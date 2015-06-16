package org.uengine.bss.monetization;

import java.util.ArrayList;
import java.util.List;
/**
 * month, tenant, plan 에 해당하는 모든 사용자별 usage를 가지고 있다.
 *
 * */
public class BillingContext {
    private final String monthId;
    private final String accountId;
    private final String planId;
    private double vatRate;

    // Account별 사용량. 동일 Account가 중복되면 안된다.
    private List<UsageStatus> usages;

//    int recurringTime;
//        public int getRecurringTime() {
//            return recurringTime;
//        }
//
//        public void setRecurringTime(int recurringTime) {
//            this.recurringTime = recurringTime;
//        }

//    int usageCount;
//        public int getUsageCount() {
//            return usageCount;
//        }
//
//        public void setUsageCount(int usageCount) {
//            this.usageCount = usageCount;
//        }



    public BillingContext(String monthId, String accountId, String planId) {
        this.monthId = monthId;
        this.accountId = accountId;
        this.planId = planId;
    }

    public String getMonthId() {
        return monthId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPlanId() {
        return planId;
    }

    public List<UsageStatus> getUsages() {
        return usages;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public void addUsage(UsageStatus usage) {
        if(usages == null) {
            usages = new ArrayList<UsageStatus>();
        }
        usages.add(usage);
    }

    public void addUsageAll(List<UsageStatus> usageList) {
        if(usages == null) {
            usages = new ArrayList<UsageStatus>();
        }
        usages.addAll(usageList);
    }
}
