package org.uengine.monetization;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Range;
import org.uengine.monetization.face.PlanSelectorFace;

import java.util.List;

/**
 * Created by swsong on 2015. 2. 2..
 */
public class Subscription {
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

	public java.util.Date effectiveDate;
	public java.util.Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(java.util.Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public java.util.Date expirationDate;
	public java.util.Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(java.util.Date expirationDate) {
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

	java.util.Date billingDate;
	public java.util.Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(java.util.Date billingDate) {
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
		return 0;
	}
}
