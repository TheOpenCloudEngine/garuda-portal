package org.uengine.bss.monetization;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.Order;
import org.metaworks.annotation.Range;
import org.uengine.bss.monetization.face.OneTimeServiceAndRateListFace;
import org.uengine.bss.monetization.face.RecurringServiceAndRateListFace;
import org.uengine.bss.monetization.face.UsageServiceAndRateListFace;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@XStreamAlias("Plan")
//@Face(ejsPath="dwr/metaworks/genericfaces/ElementFace.ejs")
public class Plan {
	
	@XStreamAsAttribute
	public String id;
	@Order(1)
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

	public String name;
	@Order(2)
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}


	public String description;
	@Order(3)
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}


	public String type;
	@Hidden
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

	public String status;
	@Hidden
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

	public String billingInterval;
	@Order(4)
	@Range(options = {"Month", "Quarter", "Semi-Annual", "Annual", "Eighteen Months", "Two-Yeares", "Three-Years"}, values = {"1", "3", "6", "12", "18", "24", "36"})
		public String getBillingInterval() {
			return billingInterval;
		}
		public void setBillingInterval(String billingInterval) {
			this.billingInterval = billingInterval;
		}

	public String extend;
	@Hidden
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}

    public String locale;
    @Hidden
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }


	public List<OneTimeServiceAndRate> oneTimeServiceAndRateList = new ArrayList<OneTimeServiceAndRate>();
	@Face(faceClass= OneTimeServiceAndRateListFace.class)
	@Order(5)
	public List<OneTimeServiceAndRate> getOneTimeServiceAndRateList() {
			return oneTimeServiceAndRateList;
		}
		public void setOneTimeServiceAndRateList(List<OneTimeServiceAndRate> oneTimeServiceAndRateList) {
			this.oneTimeServiceAndRateList = oneTimeServiceAndRateList;
		}

	public List<RecurringServiceAndRate> recurringServiceAndRateList = new ArrayList<RecurringServiceAndRate>();
	@Face(faceClass = RecurringServiceAndRateListFace.class)
	@Order(6)
		public List<RecurringServiceAndRate> getRecurringServiceAndRateList() {
			return recurringServiceAndRateList;
		}

		public void setRecurringServiceAndRateList(List<RecurringServiceAndRate> recurringServiceAndRateList) {
			this.recurringServiceAndRateList = recurringServiceAndRateList;
		}

	public List<UsageServiceAndRate> usageServiceAndRateList = new ArrayList<UsageServiceAndRate>();
	@Face(faceClass = UsageServiceAndRateListFace.class)
	@Order(7)
		public List<UsageServiceAndRate> getUsageServiceAndRateList() {
			return usageServiceAndRateList;
		}
		public void setUsageServiceAndRateList(List<UsageServiceAndRate> usageServiceAndRateList) {
			this.usageServiceAndRateList = usageServiceAndRateList;
		}

	public Properties supplimentalFields;
	@Hidden
		public Properties getSupplimentalFields() {
			return supplimentalFields;
		}
		public void setSupplimentalFields(Properties supplimentalFields) {
			this.supplimentalFields = supplimentalFields;
		}



	public UsageServiceAndRate[] getUsageServiceAndRate(String serviceId) {
		List<ServiceAndRate> serviceAndRates = new ArrayList<ServiceAndRate>();

		for(UsageServiceAndRate serviceAndRate: getUsageServiceAndRateList()){
			if(serviceAndRate.getService().getId().equals(serviceId))
				serviceAndRates.add(serviceAndRate);
		}

		UsageServiceAndRate[] serviceAndRates1 = new UsageServiceAndRate[serviceAndRates.size()];
		serviceAndRates.toArray(serviceAndRates1);

		return serviceAndRates1;
	}

    public ServiceUsages calculate(BillingContext context) {
        double totalCharge = 0.0;
        List<ServiceUsages.ServiceUsage> list = new ArrayList<ServiceUsages.ServiceUsage>();
        for (OneTimeServiceAndRate item : oneTimeServiceAndRateList) {
            ServiceUsages.ServiceUsage serviceUsage = item.calculate(context);
            totalCharge += serviceUsage.getCharge();
            list.add(serviceUsage);
        }
        for (RecurringServiceAndRate item : recurringServiceAndRateList) {
            ServiceUsages.ServiceUsage serviceUsage = item.calculate(context);
            totalCharge += serviceUsage.getCharge();
            list.add(serviceUsage);
        }
        for (UsageServiceAndRate item : usageServiceAndRateList) {
            ServiceUsages.ServiceUsage serviceUsage = item.calculate(context);
            totalCharge += serviceUsage.getCharge();
            list.add(serviceUsage);
        }
        ServiceUsages serviceUsages = new ServiceUsages(locale);
        serviceUsages.addAll(list);
        serviceUsages.setTotalChargeWithVAT(totalCharge, context.getVatRate());

        //FOR DEBUG
//        System.out.println("-Plan.java:167-");
//        for(ServiceUsages.ServiceUsage serviceUsage : serviceUsages.getServiceUsageList()){
//            ServiceAndRate serviceAndRate = serviceUsage.getServiceAndRate();
//            System.out.format("------Service [%s]------\n", serviceAndRate.getServiceId());
//            List<ServiceUsages.ServiceUsagePerUser> usagePerUserList = serviceUsage.getQtyPerUserList();
//            for(ServiceUsages.ServiceUsagePerUser usagePerUser : usagePerUserList) {
//                System.out.format("PerUser > %s\n", usagePerUser);
//            }
//            System.out.println("--");
//            String unit = serviceUsage.getServiceAndRate().getUnitOfMeasure();
//            System.out.format("Usage: %s, Charge: %s%.1f\n", serviceUsage.getUsage(), unit, serviceUsage.getCharge());
//            System.out.format("Exceed Usage: %s, Charge: %s%.1f\n", serviceUsage.getExceedQty(), unit, serviceUsage.getExceedCharge());
//        }
//        System.out.println("=============");
//        System.out.format("Total Charge: %.1f\n", serviceUsages.getTotalCharge());

        return serviceUsages;
    }

    //해당 서비스를 가지고 있는지 여부.
    public boolean hasService(String serviceId) {

        for (OneTimeServiceAndRate item : oneTimeServiceAndRateList) {
            if(serviceId.equals(item.getService().getId())) {
                return true;
            }
        }
        for (RecurringServiceAndRate item : recurringServiceAndRateList) {
            if(serviceId.equals(item.getService().getId())) {
                return true;
            }
        }
        for (UsageServiceAndRate item : usageServiceAndRateList) {
            if(serviceId.equals(item.getService().getId())) {
                return true;
            }
        }

        return false;
    }
}
