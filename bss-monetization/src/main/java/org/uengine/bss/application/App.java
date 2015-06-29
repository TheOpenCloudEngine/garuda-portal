package org.uengine.bss.application;

import com.thoughtworks.xstream.XStream;
import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.metaworks.Remover;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.metaworks.dao.TransactionContext;
import org.metaworks.widget.ModalWindow;
import org.uengine.bss.monetization.*;
import org.uengine.bss.monetization.face.PlanListFace;
import org.uengine.bss.monetization.face.ServiceListFace;
import org.uengine.kernel.GlobalContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//@Face(ejsPath="dwr/metaworks/genericfaces/ElementFace.ejs")
public class App implements ContextAware{

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



	@ServiceMethod(callByContent = true)
	public Object save() throws FileNotFoundException {

		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.toXML(this, System.out);

        try {
            File f = getFile(getId());
            System.out.println("Wrote file to " + f.getAbsolutePath());
            xstream.toXML(this, new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		return new Remover(new ModalWindow());
    }

    private static File getFile(String appId) {
//		return new File(getHomeDirectory() + appId + ".xml");
		return new File(makeDirIfNotExist(getAppDirectory(appId)) + appId + ".xml");
    }

    private static String getHomeDirectory(){
        if(System.getProperty("os.name").startsWith("Windows")){
            return System.getProperty("user.home") + File.separator;
        }else{
            return System.getenv("HOME") + File.separator;
        }
    }

	private static String getAppDirectory(String appId){
		String appDirectory = GlobalContext.getPropertyString("codebase") + File.separator + appId + File.separator
				+ GlobalContext.getPropertyString("codebase.defaultFolder") + File.separator;
        return appDirectory;
    }

	private static String makeDirIfNotExist(String path){
		File directory = new File(path);
		if(!directory.exists()){
			directory.mkdirs();
		}
		return path;
	}

	@ServiceMethod(target=ServiceMethodContext.TARGET_SELF)
	public static App load(@Payload("id") String appId) throws FileNotFoundException {

		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);

		App app = (App) xstream.fromXML(new InputStreamReader(new FileInputStream(getFile(appId)), StandardCharsets.UTF_8));

        // Init Plan service information.
		List<Service> serviceList = app.getServiceList();
		List<Plan> planList = app.getPlanList();
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

		if(TransactionContext.getThreadLocalInstance()!=null)
			TransactionContext.getThreadLocalInstance().setSharedContext("app", app);

		return app;
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
}
