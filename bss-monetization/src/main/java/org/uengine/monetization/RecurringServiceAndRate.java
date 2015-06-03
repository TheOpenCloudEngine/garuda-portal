package org.uengine.monetization;

import org.metaworks.annotation.Order;

/**
 * fixed price for recurrence
 */
public class RecurringServiceAndRate extends ServiceAndRate {

	public int quota;
	@Order(11)
		public int getQuota() {
			return quota;
		}

		public void setQuota(int quota) {
			this.quota = quota;
		}

	public double overagePrice;
	@Order(101)
		public double getOveragePrice() {
			return overagePrice;
		}

		public void setOveragePrice(double overagePrice) {
			this.overagePrice = overagePrice;
		}

	@Override
	public boolean isExceed(int qty) {
		return qty >= quota;
	}

    @Override
	public ServiceUsages.ServiceUsage calculate(BillingContext billingContext){
        int totalQty = 0;
	    for(UsageStatus usage : billingContext.getUsages()){
		    if(usage.getServiceId().equals(getService().getId())) {
			    int qtyPerUser = usage.getUsage();
			    totalQty += qtyPerUser;
		    }
	    }

        int normalUsage = Math.min(quota, totalQty);
        int exceedUsage = Math.max(0, totalQty - quota);

        //Recurring이므로 고정비용책정.
        double normalCharge = price;
        //Exceed는 사용량에 따라 비용책정.
        double exceedCharge = (double) exceedUsage * overagePrice;
        double charge = normalCharge + exceedCharge;

        System.out.format("** normalCharge = %s, normalUsage = %s, price = %s\n", normalCharge, normalUsage, price);
        System.out.format("** exceedCharge = %s, exceedUsage = %s, overagePrice = %s\n", exceedCharge, exceedUsage, overagePrice);

        ServiceUsages.ServiceUsage serviceUsage = new ServiceUsages.ServiceUsage(this, totalQty, charge, normalUsage, normalCharge, overagePrice, exceedUsage, exceedCharge);

		return serviceUsage;
	}

}
