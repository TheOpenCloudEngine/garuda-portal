package org.uengine.bss.monetization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swsong on 2015. 2. 2..
 */
public class ServiceUsages {

    /*
      * 여러 서비드들에 대한 사용량과금정보를 담는다.
      * */
    private List<ServiceUsage> serviceUsageList;
    private Subscription subscription;
    private double subTotalCharge;
    private double vatCharge;
    private double totalCharge;
    private String locale;

    public ServiceUsages(String locale) {
        this.locale = locale;
    }

    public List<ServiceUsage> getServiceUsageList() {
        return serviceUsageList;
    }

    public void addAll(List<ServiceUsage> list) {
        if(serviceUsageList == null) {
            serviceUsageList = new ArrayList<ServiceUsage>();
        }
        serviceUsageList.addAll(list);
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setTotalCharge(double totalCharge) {
        this.subTotalCharge = totalCharge;
        this.totalCharge = totalCharge;
    }

    public void setTotalChargeWithVAT(double totalCharge, double vatRate) {
        this.subTotalCharge = totalCharge;
        this.vatCharge = totalCharge * vatRate;
        this.totalCharge = subTotalCharge + vatCharge;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public double getSubTotalCharge() {
        return subTotalCharge;
    }

    public double getVatCharge() {
        return vatCharge;
    }



    public String getLocale() {
        return locale;
    }

    /**
     * 서비스와 과금정보를 표현하는 클래스.
     * */
    public static class ServiceUsage {

        private final ServiceAndRate serviceAndRate;
        private final int qty;
        private final double charge;
        private final int normalQty;
        private final double normalCharge;
	    private final double exceedPrice;
	    private final int exceedQty;
        private final double exceedCharge;

//        private List<ServiceUsagePerUser> qtyPerUserList;

        public ServiceUsage(ServiceAndRate serviceAndRate, int usage, double charge, int normalUsage, double normalCharge, double exceedPrice, int exceedUsage, double exceedCharge) {
            this.serviceAndRate = serviceAndRate;
            this.qty = usage;
            this.charge = charge;
            this.normalQty = normalUsage;
            this.normalCharge = normalCharge;
	        this.exceedPrice = exceedPrice;
	        this.exceedQty = exceedUsage;
            this.exceedCharge = exceedCharge;
        }

        public ServiceUsage(ServiceAndRate serviceAndRate, int totalUsage, double charge) {
            this(serviceAndRate, totalUsage, charge, totalUsage, charge, 0, 0, 0);
        }

        public ServiceAndRate getServiceAndRate() {
            return serviceAndRate;
        }

        public int getQty() {
            return qty;
        }

        public double getCharge() {
            return charge;
        }

        public int getNormalQty() {
            return normalQty;
        }

        public double getNormalCharge() {
            return normalCharge;
        }

        public int getExceedQty() {
            return exceedQty;
        }

        public double getExceedCharge() {
            return exceedCharge;
        }

	    public double getExceedPrice() {
		    return exceedPrice;
	    }
    }

    /**
     * 사용자별 사용량을 표현하는 클래스.
     * */
//    public static class ServiceUsagePerUser {
//
//        private final String userId;
//        private final int qty;
//
//        public ServiceUsagePerUser(String userId, int usage) {
//            this.userId = userId;
//            this.qty = usage;
//        }
//
//        public String getUserId() {
//            return userId;
//        }
//
//        public int getUsage() {
//            return qty;
//        }
//
//        @Override
//        public String toString() {
//            return userId + ":" + qty;
//        }
//    }
}
