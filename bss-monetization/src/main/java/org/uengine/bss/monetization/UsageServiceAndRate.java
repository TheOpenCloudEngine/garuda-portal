package org.uengine.bss.monetization;

import org.metaworks.annotation.Order;

public class UsageServiceAndRate extends ServiceAndRate {

    public int minQuantity;
    @Order(11)
        public int getMinQuantity() {
            return minQuantity;
        }
        public void setMinQuantity(int minQuantity) {
            this.minQuantity = minQuantity;
        }

    public int maxQuantity;
    @Order(12)
        public int getMaxQuantity() {
            return maxQuantity;
        }
        public void setMaxQuantity(int maxQuantity) {
            this.maxQuantity = maxQuantity;
        }

	/**
	 * maxQuantity가 0보다 클때 즉, 값이 설정되어 있을때에만 초과여부를 판단한다.
	 * */
	@Override
	public boolean isExceed(int qty) {
		return maxQuantity > 0 && qty >= maxQuantity;
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

        double charge = totalQty * price;

        ServiceUsages.ServiceUsage serviceUsage = new ServiceUsages.ServiceUsage(this, totalQty, charge);

        return serviceUsage;
    }


}
