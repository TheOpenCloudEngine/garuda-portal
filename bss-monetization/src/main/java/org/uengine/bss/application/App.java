package org.uengine.bss.application;

import com.thoughtworks.xstream.XStream;
import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.metaworks.Remover;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.metaworks.dao.TransactionContext;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.ModalWindow;
import org.oce.garuda.multitenancy.Operation;
import org.oce.garuda.multitenancy.TenantContext;
import org.uengine.bss.monetization.*;
import org.uengine.bss.monetization.face.PlanListFace;
import org.uengine.bss.monetization.face.ServiceListFace;
import org.uengine.kernel.NeedArrangementToSerialize;
import org.uengine.modeling.resource.DefaultResource;
import org.uengine.modeling.resource.ResourceManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//@Face(ejsPath="dwr/metaworks/genericfaces/ElementFace.ejs")
public class App implements ContextAware, NeedArrangementToSerialize{
	protected final static String UENGINE_METADATA_FILE = "uengine.metadata";

	transient MetaworksContext metaworksContext;

	public MetaworksContext getMetaworksContext() {
		if(metaworksContext==null){
			metaworksContext = new MetaworksContext();
		}
		return metaworksContext;
	}
	public void setMetaworksContext(MetaworksContext context) {
		this.metaworksContext = context;
	}

	public String id;
	@Order(1)
	@Id
	@NonEditable
	@Group(name="Basic")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String name;
	@Order(2)
	@Group(name="Basic")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String description;
	@Order(3)
	@Group(name="Basic")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Plan> planList = new ArrayList<Plan>();
	@Face(faceClass = PlanListFace.class)
	@Order(5)
	@Group(name="Plan")
	public List<Plan> getPlanList() {
		return planList;
	}
	public void setPlanList(List<Plan> planList) {
		this.planList = planList;
	}


	public List<Service> serviceList = new ArrayList<Service>();
	@Face(faceClass = ServiceListFace.class)
	@Order(4)
	@Group(name="Service")
	public List<Service> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}


	List<MetadataProperty> metadataPropertyList = new ArrayList<MetadataProperty>();
	@Face(faceClass = MetadataPropertyListFace.class)
	@Order(5)
	@Group(name="MetadataProperty")
	public List<MetadataProperty> getMetadataPropertyList() {
		return metadataPropertyList;
	}
	public void setMetadataPropertyList(List<MetadataProperty> metadataPropertyList) {
		this.metadataPropertyList = metadataPropertyList;
	}

//	@ServiceMethod(callByContent = true, target= ServiceMethodContext.TARGET_POPUP)
//	public static Invoice createInvoice(){
//		Invoice invoice = new Invoice();
//
//		wrapReturn(new ModalWindow(invoice));
//
//		return invoice;
//	}

	public Service getService(String id) {
		for(Service service: getServiceList()){
			if(service.getId().equals(id)){
				return service;
			}
		}

		return null;
	}

	public Plan getPlan(String id) {
		for(Plan plan: getPlanList()){
			if(plan.getId().equals(id)){
				return plan;
			}
		}

		return null;
	}

	@ServiceMethod(callByContent = true)
	public Object save() throws FileNotFoundException {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.toXML(this, System.out);

		beforeSerialization();


		try {
			xstream.toXML(this, new OutputStreamWriter(getResourceAsOutputStream(getId(), UENGINE_METADATA_FILE),
					"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally {

		}

		return new Remover(new ModalWindow());
	}

	@ServiceMethod(target= ServiceMethodContext.TARGET_SELF)
	public static App load(@Payload("id") String appId) throws FileNotFoundException {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);

		App app = null;
		try {
			app = (App) xstream.fromXML(new InputStreamReader(getResourceAsStream(appId, UENGINE_METADATA_FILE),
                    StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new FileNotFoundException("Uengine metadata file is not existed!");
		}

		app.afterDeserialization();



		if(TransactionContext.getThreadLocalInstance()!=null)
			TransactionContext.getThreadLocalInstance().setSharedContext("app", app);

		return app;
	}

	protected OutputStream getResourceAsOutputStream(final String appKey, final String alias) throws FileNotFoundException {

		return

				(OutputStream)

						TenantContext.nonTenantSpecificOperation(new Operation() {
							@Override
							public Object run() {
								try {
									return _getResourceAsOutputStream(appKey, alias);
								} catch (FileNotFoundException e) {
									throw new RuntimeException(e);
								}
							}
						});

	}

	protected static InputStream getResourceAsStream(final String appKey, final String alias) throws Exception {
		return

				(InputStream)

						TenantContext.nonTenantSpecificOperation(new Operation() {
							@Override
							public Object run() {
								try {
									return _getResourceAsStream(appKey, alias);
								} catch (Exception e) {
									throw new RuntimeException(e);
								}
							}
						});
	}



	protected OutputStream _getResourceAsOutputStream(String appKey, String alias) throws FileNotFoundException {
		ResourceManager resourceManager = MetaworksRemoteService.getComponent(ResourceManager.class);

		DefaultResource resource = new DefaultResource();
		resource.setPath(appKey + "/" + alias);

		try {

			return resourceManager.getStorage().getOutputStream(resource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static InputStream _getResourceAsStream(String appKey, String alias) throws Exception {
		ResourceManager resourceManager = MetaworksRemoteService.getComponent(ResourceManager.class);

		DefaultResource resource = new DefaultResource();
		resource.setPath(appKey + "/" + alias);

		try {
			return resourceManager.getStorage().getInputStream(resource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void beforeSerialization() {

		for(MetadataProperty metadataProperty : getMetadataPropertyList()){

			if(metadataProperty instanceof NeedArrangementToSerialize){
				((NeedArrangementToSerialize) metadataProperty).beforeSerialization();
			}
		}
	}

	@Override
	public void afterDeserialization() {


		// Init Plan service information.
		List<Service> serviceList = getServiceList();
		List<Plan> planList = getPlanList();
		for (Service service : serviceList) {
			for (Plan plan : planList) {
				if(plan.getOneTimeServiceAndRateList() != null) {
					for (OneTimeServiceAndRate serviceAndRate : plan.getOneTimeServiceAndRateList()) {
						if (serviceAndRate != null && serviceAndRate.getService().getId().equals(service.getId())) {
							serviceAndRate.setService(service);
						}
					}
				}
				if(plan.getRecurringServiceAndRateList() != null) {
					for (RecurringServiceAndRate serviceAndRate : plan.getRecurringServiceAndRateList()) {
						if (serviceAndRate != null && serviceAndRate.getService().getId().equals(service.getId())) {
							serviceAndRate.setService(service);
						}
					}
				}
				if(plan.getUsageServiceAndRateList() != null) {
					for (UsageServiceAndRate serviceAndRate : plan.getUsageServiceAndRateList()) {
						if (serviceAndRate != null && serviceAndRate.getService().getId().equals(service.getId())) {
							serviceAndRate.setService(service);
						}
					}
				}
			}
		}

		for(MetadataProperty metadataProperty : getMetadataPropertyList()){

			if(metadataProperty instanceof NeedArrangementToSerialize){
				((NeedArrangementToSerialize) metadataProperty).afterDeserialization();
			}
		}

	}
}