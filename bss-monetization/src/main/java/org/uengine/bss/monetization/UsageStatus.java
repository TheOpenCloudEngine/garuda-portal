package org.uengine.bss.monetization;

/**
 * monthId, accountId, planId, serviceId, `usage`, isExceed, isBlock
 * Created by swsong on 2015. 2. 3..
 */
public class UsageStatus {
	private String monthId;
    private String appId;
    private String accountId;
    private String planId;
    private String serviceId;
    private int usage;
    private int isExceed;
    private int isBlock;
    private String msg; //메시지.

	@Override
	public String toString() {
		return "UsageStatus{" +
				"monthId='" + monthId + '\'' +
                ", appId='" + appId + '\'' +
				", accountId='" + accountId + '\'' +
				", planId='" + planId + '\'' +
				", serviceId='" + serviceId + '\'' +
				", usage=" + usage +
				", isExceed=" + isExceed +
				", isBlock=" + isBlock +
                ", msg=" + msg +
				'}';
	}

	public UsageStatus(){
    }

    public UsageStatus(String monthId, String appId, String accountId, String planId, String serviceId, int usage, int isExceed, int isBlock, String msg) {
        this.monthId = monthId;
        this.appId = appId;
	    this.accountId = accountId;
        this.planId = planId;
        this.serviceId = serviceId;
        this.usage = usage;
        this.isExceed = isExceed;
        this.isBlock = isBlock;
        this.msg = msg;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public int getIsExceed() {
        return isExceed;
    }

    public void setIsExceed(int isExceed) {
        this.isExceed = isExceed;
    }

    public int getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(int isBlock) {
        this.isBlock = isBlock;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
