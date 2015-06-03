package org.uengine.monetization.service;

import org.uengine.application.App;
import org.uengine.monetization.BillingContext;
import org.uengine.monetization.Plan;
import org.uengine.monetization.ServiceUsages;
import org.uengine.monetization.UsageStatus;
import org.uengine.monetization.dao.AppMappingMapper;
import org.uengine.monetization.dao.UsageStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingService {

    @Autowired
    private AppMappingMapper appMappingMapper;

    @Autowired
    private UsageStatusMapper usageStatusMapper;

	public BillingService(){
    }

    public AppMappingMapper getAppMappingMapper() {
        return appMappingMapper;
    }

    public void setAppMappingMapper(AppMappingMapper appMappingMapper) {
        this.appMappingMapper = appMappingMapper;
    }

    public UsageStatusMapper getUsageStatusMapper() {
        return usageStatusMapper;
    }

    public UsageStatus getUsageStatus(String appId, String accountId, String serviceId, String monthId) throws IOException {

        List<String> planIdList = appMappingMapper.select(appId, accountId);
        String planId = planIdList.get(0);
        // appId + accountId로 plan을 가져온다.
        App app = App.load(appId);
        Plan plan = null;
        List<Plan> planList = app.getPlanList();
        if(planList != null) {
            for(Plan p : planList) {
                if(p.getId().equals(planId)) {
                    plan = p;
                    break;
                }
            }
        }

        if(plan == null) {
            //plan이 없다면 사용못함.
            return new UsageStatus(monthId, appId, accountId, planId, serviceId, 0, 0, 1, "NOPLAN");
        }

        if(!plan.hasService(serviceId)){
            //서비스 사용못함. 제공안하는 서비스.
            return new UsageStatus(monthId, appId, accountId, planId, serviceId, 0, 0, 1, "NOSERVICE");
        }
        //DB에서 사용량을 가져오기
        UsageStatus usageStatus = usageStatusMapper.select(monthId, appId, accountId, planId, serviceId);
        if(usageStatus == null) {
            //없으면 사용량 0으로..
            usageStatus = new UsageStatus(monthId, appId, accountId, planId, serviceId, 0, 0, 0, null);
        }
        return usageStatus;
    }

	public ServiceUsages getAccountUsages(String appId, String accountId, String serviceId, String monthId) throws IOException {

        //FIXME 일단 최신플랜을 사용한다. 중간에 변경을 할 경우 날짜로 보고 최신 플랜을 가져와야 한다.

        List<String> planIdList = appMappingMapper.select(appId, accountId);
        String planId = planIdList.get(0);
		// appId + accountId로 plan을 가져온다.
		App app = App.load(appId);
		Plan plan = null;
		List<Plan> planList = app.getPlanList();
		if(planList != null) {
			for(Plan p : planList) {
				if(p.getId().equals(planId)) {
					plan = p;
					break;
				}
			}
		}

        if(plan == null) {
            return new ServiceUsages("");
        }
		BillingContext context = new BillingContext(monthId, accountId, planId);
		//부가세 10%설정. 차후 셋팅으로 뺀다.
		context.setVatRate(0.1);
		//DB에서 사용량을 가져오기
		List<UsageStatus> usageStatusList = usageStatusMapper.selectByPlan(monthId, appId, accountId, planId);
		context.addUsageAll(usageStatusList);

		return plan.calculate(context);
	}
	/**
	 * 비공식 테스트용 API
	 * 사용상태정보 지우기.
	 * */
	public String truncateUsageStatusData(String appId) {
		usageStatusMapper.truncate(appId);
		return "SUCCESS";
	}

    public String truncateAllData(String appId) {
        usageStatusMapper.truncate(appId);
        appMappingMapper.truncate(appId);

        return "SUCCESS";
    }


	public Map<String, String> unblockService(String appId, String accountId, String serviceId, String monthId) throws FileNotFoundException {
		List<String> planIdList = appMappingMapper.select(appId, accountId);
		String planId = planIdList.get(0);
		// appId + accountId로 plan을 가져온다.
		App app = App.load(appId);
		Plan plan = null;
		List<Plan> planList = app.getPlanList();
		if(planList != null) {
			for(Plan p : planList) {
				if(p.getId().equals(planId)) {
					plan = p;
					break;
				}
			}
		}

		if(plan == null) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("success", "false");
			result.put("msg", "No such plan");
			return result;
		}

		UsageStatus usageStatus = usageStatusMapper.select(monthId, appId, accountId, planId, serviceId);
		if(usageStatus == null) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("success", "false");
			result.put("msg", "No service status.");
			return result;
		}
		usageStatus.setIsBlock(0);
		usageStatusMapper.update(usageStatus);
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "true");
		result.put("msg", "");

		return result;
	}
}
