package org.uengine.bss.monetization.service;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;
import org.uengine.bss.application.App;
import org.uengine.bss.application.TenantApp;
import org.uengine.bss.monetization.Plan;
import org.uengine.bss.monetization.ServiceAndRate;
import org.uengine.bss.monetization.UsageStatus;
import org.uengine.bss.monetization.dao.AppMappingMapper;
import org.uengine.bss.monetization.dao.UsageStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.uengine.bss.monetization.util.MutableInteger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 로그파일을 읽어들인다.
 * WAS를 재시작해서 마지막읽은 위치를 읽어버렸다면, 파일의 마지막부터 읽게된다.
 * 주기적으로 호출되며,
 * Created by swsong on 2015. 2. 3..
 */

@Component
public class MeteringService {
    @Autowired
    private BillingService billingService;

    @Autowired
    private UsageStatusMapper usageStatusMapper;

    @Autowired
    private AppMappingMapper appMappingMapper;

    private long lastPos;

	public String logFilePath = "/var/log/metering/metering";


    public MeteringService() {
    }

	private Map<String, Map<String, String>> getAppMapping(){
		List<Map<String, String>> appMapping = appMappingMapper.selectAll();
        Map<String, Map<String, String>> subscriptionMap = new HashMap<String, Map<String, String>>();
		for(Map<String, String> map : appMapping){
            String appId = map.get("appId");
            Map<String, String> val = subscriptionMap.get(appId);
            if(val == null) {
                val = new HashMap<String, String>();
                subscriptionMap.put(appId, val);
            }
            val.put(map.get("accountId"), map.get("planId"));
		}
        return subscriptionMap;
	}

    private Map<String, Map<String, ServiceAndRate>> getPlanMap(App app){
        Map<String, Plan> planMap = new HashMap<String, Plan>();

        for(Plan plan : app.getPlanList()){
            planMap.put(plan.getId(), plan);
        }

        Map<String, Map<String, ServiceAndRate>> planServiceAndRateMap = new HashMap<String, Map<String, ServiceAndRate>>();
        for(Map.Entry<String, Plan> e : planMap.entrySet()){
            Map<String, ServiceAndRate> serviceAndRateMap = new HashMap<String, ServiceAndRate>();
            for(ServiceAndRate sr : e.getValue().getOneTimeServiceAndRateList()){
                serviceAndRateMap.put(sr.getService().getId(), sr);
            }
            for(ServiceAndRate sr : e.getValue().getRecurringServiceAndRateList()){
                serviceAndRateMap.put(sr.getService().getId(), sr);
            }
            for(ServiceAndRate sr : e.getValue().getUsageServiceAndRateList()){
                serviceAndRateMap.put(sr.getService().getId(), sr);
            }
            String planId = e.getKey();
            planServiceAndRateMap.put(planId, serviceAndRateMap);
        }
        return planServiceAndRateMap;
    }

    @Scheduled(fixedDelay=2000)
    public void calculateLogs(){
        try {
	        String dateString = new SimpleDateFormat("yyyyMMdd").format(new Date());
	        String filePath = logFilePath + "." + dateString + ".log";
//	        System.out.println("check log file .. " + filePath);
	        File logFile = new File(filePath);

            if(!logFile.exists()) {
                return;
            }

            RandomAccessFile reader = new RandomAccessFile(logFile, "r");
            long length = reader.length();

            //처음시작이라면, 마지막으로 이동.
            if(lastPos == 0) {
                lastPos = length;
            }
            if(length == lastPos) {
                //추가된게 없다면.
                return;
            }else if(length < lastPos) {
                //파일이 작아졌다면
                lastPos = length;
            }
            //파일읽기 위치를 마지막 읽은 위치로 이동.
            if(lastPos > 0) {
                reader.seek(lastPos);
            }

            String line = reader.readLine();
	        if(line == null){
		        return;
	        }

	        System.out.println("MeteringService] Read new log...");
	        //테넌트별.
	        Map<String, org.uengine.bss.monetization.util.MutableInteger> usageMap = new HashMap<String, org.uengine.bss.monetization.util.MutableInteger>();

            while (line != null) {
                System.out.println("MeteringService:101] > "+line);
                int p = line.lastIndexOf('\t');
                if(p > 0) {
                    String jsonData = line.substring(p + 1);
                    try {
                        JSONObject obj = new JSONObject(jsonData);
                        String aid = obj.getString("aid");
	                    String cid = obj.getString("cid"); //Account ID
                        String uid = obj.getString("uid");
                        String sid = obj.getString("sid");
                        String id1 = String.format("%s:%s:%s", aid, cid, sid);
                        org.uengine.bss.monetization.util.MutableInteger qty1 = usageMap.get(id1);
	                    if(qty1 == null) {
		                    qty1 = new org.uengine.bss.monetization.util.MutableInteger();
		                    usageMap.put(id1, qty1);
	                    }
	                    qty1.increment();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
	            line = reader.readLine();
            }
	        lastPos = reader.getFilePointer();

	        if(usageMap.size() == 0){
		        return;
	        }
            //TODO 로그의 날짜를 확인해서 현재와 다르다면, 이전날짜에 대해서만 합산하여 나누어 여러번 업데이트한다.

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String monthId = sdf.format(new Date());




            Map<String, Map<String, String>> appMapping = getAppMapping();
            Map<String, Map<String, Map<String, ServiceAndRate>>> appPlanMap = new HashMap<String, Map<String, Map<String, ServiceAndRate>>>();


            for(Map.Entry<String, MutableInteger> e : usageMap.entrySet()){
                String[] keys = e.getKey().split(":");
                String appId = keys[0];

                App app = TenantApp.load(appId);
                Map<String, Map<String, ServiceAndRate>> planServiceAndRateMap = appPlanMap.get(appId);
                if(planServiceAndRateMap == null) {
                    planServiceAndRateMap = getPlanMap(app);
                    appPlanMap.put(appId, planServiceAndRateMap);
                }

                Map<String, String> subscriptionMap = appMapping.get(appId);

                String accountId = keys[1];
                //해당 Account가 어떤 plan으로 가입했는지 먼저 확인필요.
                String pid = subscriptionMap.get(accountId);
                String sid = keys[2];
                int incrementQty = e.getValue().intValue();

                UsageStatus usageStatus = usageStatusMapper.select(monthId, appId, accountId, pid, sid);
                // 초과여부 계산하여 status table 업데이트.
//                incrementQty + 체크..

	            Map<String, ServiceAndRate> serviceAndRateMap = planServiceAndRateMap.get(pid);
	            if(serviceAndRateMap == null) {
		            //해당 플랜없음.
		            System.out.println("No such plan : " + pid);
		            continue;
	            }
	            ServiceAndRate serviceAndRate = serviceAndRateMap.get(sid);
	            if(serviceAndRate == null) {
		            //해당 서비스없음.
		            System.out.println("No such service : " + sid);
		            continue;
	            }

	            if(usageStatus != null) {
		            //기존 상태가 존재하면 합산.
		            int totalQty = usageStatus.getUsage() + incrementQty;
		            usageStatus.setUsage(totalQty);
		            boolean isExceed = serviceAndRate.isExceed(totalQty);
		            if(usageStatus.getIsExceed() == 1){
		                //이미 exceed 되어있다면, 자동으로 block을 걸지않는다. 관리자가 풀었을수 있기때문..

	                }else{
		                if(isExceed) {
			                usageStatus.setIsExceed(1);
			                //일단 블락 시킨다.
			                usageStatus.setIsBlock(1);
		                }
	                }
                    usageStatusMapper.update(usageStatus);
		            System.out.println("Updated : " + usageStatus);

                } else {
		            boolean isExceed = serviceAndRate.isExceed(incrementQty);
		            //초과했으면, 동시에 블럭까지 시킨다. 초과가 아니면, 블럭도 하지 않음.
                    usageStatus = new UsageStatus(monthId, appId, accountId, pid, sid, incrementQty, isExceed ? 1 : 0,  isExceed ? 1 : 0, null);
                    usageStatusMapper.insert(usageStatus);
		            System.out.println("Inserted : " + usageStatus);
                }

            }

            long pos =  reader.getFilePointer();

            if (pos > lastPos) {
                System.out.println("read end at = " + pos);
            }
            lastPos = pos;
        } catch(IOException e) {
            e.printStackTrace();
        }

    }


//    private Map<String, String> loadSubscriptionMap() {
//        List<Subscription> list = null;//subscriptionMapper.selectAll();
//        if(list != null) {
//            Map<String, String> subscriptionMap = new HashMap<String, String>();
//            for(Subscription s : list) {
//                subscriptionMap.put(s.getAccountId(), s.getPlan().getId());
//            }
//            return subscriptionMap;
//        }
//        return null;
//    }
}
