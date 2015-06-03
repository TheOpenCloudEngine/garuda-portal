package org.uengine.monetization;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Range;
import org.uengine.monetization.face.PlanSelectorFace;

import java.util.Date;
import java.util.List;

public class SubscriptionOld {
	
	public String accountId;
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

	public String appId;
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}

	public Plan plan = new Plan();
	@Face(faceClass=PlanSelectorFace.class)
		public Plan getPlan() {
			return plan;
		}
		public void setPlan(Plan plan) {
			this.plan = plan;
		}

	public Date effectiveDate;
		public Date getEffectiveDate() {
			return effectiveDate;
		}
		public void setEffectiveDate(Date effectiveDate) {
			this.effectiveDate = effectiveDate;
		}

	public Date expirationDate;
		public Date getExpirationDate() {
			return expirationDate;
		}
		public void setExpirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
		}

	public boolean isTrial;
		public boolean isTrial() {
			return isTrial;
		}
		public void setTrial(boolean isTrial) {
			this.isTrial = isTrial;
		}

	public String states;
		public String getStates() {
			return states;
		}
		public void setStates(String states) {
			this.states = states;
		}

	public String contractDuration; //for the case that the fees differ from others by usageContract
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

	Date billingDate;
		public Date getBillingDate() {
			return billingDate;
		}
		public void setBillingDate(Date billingDate) {
			this.billingDate = billingDate;
		}

	public String billingTo;
		public String getBillingTo() {
			return billingTo;
		}
		public void setBillingTo(String billingTo) {
			this.billingTo = billingTo;
		}



	public double bill(List<UsageStatus> usages, int recurringTime){

		if(isTrial) return 0;

		double amount = 0;

//		BillingContext bc = new BillingContext();
//		{
//			bc.setRecurringTime(recurringTime);
//			bc.setUsages(usages);
//			bc.setSubscription(this);
//		}
//
//		//one time billing
//		for(ServiceAndRate serviceAndRate : plan.getOneTimeServiceAndRateList()){
//			amount = amount + serviceAndRate.calculate(bc);
//		}
//
//		//recurring billing
//		for(ServiceAndRate serviceAndRate : plan.getRecurringServiceAndRateList()){
//			amount = amount + serviceAndRate.calculate(bc);
//		}
//
//		//usage based billing
//		Map<String, Integer> usageCntByServiceId = new HashMap<String, Integer>();
//
//		for(Usage usage : bc.getUsages()) {
//			int usageCnt = 0;
//
//			if (usageCntByServiceId.containsKey(usage.serviceId))
//				usageCnt = usageCntByServiceId.get(usage.serviceId).intValue();
//
//			usageCnt = usageCnt + 1;
//
//			usageCntByServiceId.put(usage.serviceId, usageCnt);
//
//
//			UsageServiceAndRate[] serviceRates = getPlan().getUsageServiceAndRate(usage.serviceId);
//			for(UsageServiceAndRate serviceRate : serviceRates){
//				if(
//						serviceRate.minQuantity < usageCnt &&
//						(serviceRate.maxQuantity > usageCnt ||
//								serviceRate.maxQuantity == -1 /*means unlimited*/
//						)
//						){
//					amount += serviceRate.price;
//				}
//			}
//
//		}

		return amount;
	}

}
