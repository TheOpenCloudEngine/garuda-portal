package org.uengine.monetization;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Order;
import org.metaworks.annotation.Range;
import org.uengine.monetization.face.ServiceSelectorFace;

@XStreamAlias("ServiceAndRate")
public class ServiceAndRate{

    public Service service = new Service();
	@Face(faceClass=ServiceSelectorFace.class)
	@Order(1)
		public Service getService() {
			return service;
		}
		public void setService(Service service) {
			this.service = service;
		}

	public double price;
	@Order(100)
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}

	public String unitOfMeasure;
	@Order(110)
		public String getUnitOfMeasure() {
			return unitOfMeasure;
		}

		public void setUnitOfMeasure(String unitOfMeasure) {
			this.unitOfMeasure = unitOfMeasure;
		}

	public String contractDuration; //for the case that the fees differ from others by usageContract
	@Order(2)
	@Range(
			options={"Month", "Quarter", "Semi-Year", "Year", "Two-Year"},
			values={"1", "3", "6", "12", "24"}
	)

	public String getContractDuration() {
		return contractDuration;
	}

	public void setContractDuration(String contractDuration) {
			this.contractDuration = contractDuration;
		}

	/**
	 * 사용량이 초과하였는지 여부.
	 * 디폴트로는 false이며, 판단하는 로직을 하위클래스에서 구현해야한다.
	 * @param qty 현재사용량
	 * */
	public boolean isExceed(int qty) {
		return false;
	}

	public ServiceUsages.ServiceUsage calculate(BillingContext billingContext){
		return null;
	}

//	String type;
//	@Range(
//			options={"One Time", "Recurring", "Usage-based"},
//			values ={"OneTime", "Recurring", "Usage"}
//	)
//		public String getType() {
//			return type;
//		}
//		public void setType(String type) {
//			this.type = type;
//		}
//
//	@ServiceMethod(bindingFor = "type", target= ServiceMethodContext.TARGET_SELF)
//	public ServiceAndRate changeType(@AutowiredFromClient App app, @Payload("type") String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//		return (ServiceAndRate) Class.forName(getClass().getPackage().getName() + "." + type + "ServiceAndRate").newInstance();
//	}





}
