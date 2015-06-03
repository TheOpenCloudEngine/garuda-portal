package org.uengine.monetization;

public class OneTimeServiceAndRate extends ServiceAndRate {

    public ServiceUsages.ServiceUsage calculate(BillingContext billingContext){
        int totalQty = 0;
        for(UsageStatus usage : billingContext.getUsages()){
            if(usage.getServiceId().equals(getService().getId())) {
                int qtyPerUser = usage.getUsage();
                totalQty += qtyPerUser;
            }
        }

        ServiceUsages.ServiceUsage serviceUsage = new ServiceUsages.ServiceUsage(this, totalQty, price);

        return serviceUsage;
    }

}
