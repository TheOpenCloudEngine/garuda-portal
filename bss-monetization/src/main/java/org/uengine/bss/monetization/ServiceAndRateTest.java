package org.uengine.bss.monetization;

import org.junit.Test;
import org.uengine.bss.application.App;
import org.uengine.bss.application.TenantApp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by swsong on 2015. 2. 2..
 */
public class ServiceAndRateTest {


	String appId = "sampleapp";

    @Test
    public void calculateStandard() throws IOException {

        App app = TenantApp.load(appId);
        List<Plan> planList = app.getPlanList();

        /**
         * Standard Plan
         */
        Plan plan = planList.get(0);

        String monthId = "201502";
        String accountId = "t001";
        String planId = "Standard";
        BillingContext context = new BillingContext(monthId, accountId, planId);

        /*
        * Mock ì‚¬ìš©ëŸ‰ ìƒ?ì„±.
        * */
        String serviceId0 = "Setup";
        String serviceId1 = "Basic";
        String serviceId2 = "Advance";
        int isExeed = 1;
        int isBlock = 0;
        // ì‚¬ìš©ëŸ‰ìœ¼ë¡œ BillingContextë¥¼ ì±„ìš´ë‹¤.

	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId0, 1, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId1, 10, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId1, 20, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId2, 10, 0, 0, null)); //Standard Planì—?ì„œëŠ” Advanceì„œë¹„ìŠ¤ë¥¼ ì œê³µí•˜ì§€ ì•Šì?Œ.
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId1, 5, 0, 0, null));

        calculate(plan, context);
    }

    @Test
    public void calculateEnterprise() throws IOException {

        App app = TenantApp.load(appId);
        List<Plan> planList = app.getPlanList();

        /**
         * Enterprise Plan
         */
        Plan plan = planList.get(1);

        String monthId = "201502";
        String appId = "sampleapp";
        String accountId = "t001";
        String planId = "Enterprise";
        BillingContext context = new BillingContext(monthId, accountId, planId);

        /*
        * Mock ì‚¬ìš©ëŸ‰ ìƒ?ì„±.
        * */
        String serviceId0 = "Setup";
        String serviceId1 = "Basic";
        String serviceId2 = "Advance";
        int isExeed = 1;
        int isBlock = 0;
        // ì‚¬ìš©ëŸ‰ìœ¼ë¡œ BillingContextë¥¼ ì±„ìš´ë‹¤.
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId0, 1, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId1, 10, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId2, 5, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId1, 20, 0, 0, null));
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId2, 10, 0, 0, null)); //Standard Planì—?ì„œëŠ” Advanceì„œë¹„ìŠ¤ë¥¼ ì œê³µí•˜ì§€ ì•Šì?Œ.
	    context.addUsage(new UsageStatus(monthId, appId, accountId, planId, serviceId1, 10, 0, 0, null));


	    calculate(plan, context);
    }

//    private App loadApp() {
//        String appSettingFilePath = "/sampleapp.json";
//        App app = null;
//        InputStream is = null;
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            is = getClass().getResourceAsStream(appSettingFilePath);
//            app = mapper.readValue(is, App.class);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        System.out.println("app = " + app);
//        return app;
//    }


    private void calculate(Plan plan, BillingContext context) {
        ServiceUsages serviceUsages = plan.calculate(context);

        for(ServiceUsages.ServiceUsage serviceUsage : serviceUsages.getServiceUsageList()){
            ServiceAndRate serviceAndRate = serviceUsage.getServiceAndRate();
            System.out.format("------Service [%s]------\n", serviceAndRate.getService().getId());
            if(serviceAndRate instanceof RecurringServiceAndRate) {

            }
            System.out.println("--");
            String unit = serviceUsage.getServiceAndRate().getUnitOfMeasure();
            System.out.format("Usage: %s, Charge: %s%.1f\n", serviceUsage.getQty(), unit, serviceUsage.getCharge());
            System.out.format("Exceed Usage: %s, Charge: %s%.1f\n", serviceUsage.getExceedQty(), unit, serviceUsage.getExceedCharge());
        }
        System.out.println("======================");
        System.out.format("Total Charge: %.1f\n", serviceUsages.getTotalCharge());

    }
}
