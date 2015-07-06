package org.uengine.bss.application;

import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.metaworks.annotation.*;
import org.uengine.bss.monetization.*;
import org.uengine.bss.monetization.face.PlanListFace;
import org.uengine.bss.monetization.face.ServiceListFace;

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
