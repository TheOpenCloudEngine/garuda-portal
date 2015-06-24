package org.uengine.bss.monetization.service;

import org.metaworks.dao.IDAO;
import org.metaworks.dao.MetaworksDAO;
import org.metaworks.spring.SpringConnectionFactory;
import org.springframework.stereotype.Component;
import org.uengine.bss.application.App;
import org.uengine.bss.monetization.*;
import org.uengine.bss.monetization.api.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AppServiceImpl implements AppService {

	@Autowired
	public SpringConnectionFactory springConnectionFactoryForMetaworks;
	@Autowired
	public BillingService billingService;

	public Response getPlans(String appId) throws FileNotFoundException {
		Response response = getApp(appId);
		App app = (App) response.getEntity();

		return Response.ok()
				.entity(new GenericEntity<List<Plan>>(app.getPlanList()) {
				})
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.build();
	}


	public Response getPlan(String appId, String planId) throws FileNotFoundException {
		Plan plan = null;
		Response response = getPlans(appId);
		List<Plan> plans = (List<Plan>) response.getEntity();
		for (Plan p : plans) {
			if (p.getId().equals(planId))
				plan = p;
		}
		return Response
				.ok()
				.entity(plan)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods",
						"GET").build();
	}

	public Response getApp(String appId) throws FileNotFoundException {
		return Response
				.ok()
				.entity(App.load(appId))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods",
						"GET").build();
	}

	@Transactional
	@Override
	public Response subscribePlan(String appId, String accountId, String planId) {
		try {
			// Check whether plan exists.
			Response response = getPlan(appId, planId);
			Plan plan = (Plan) response.getEntity();
			if (plan == null)
				throw new Exception("No Such Plan [" + planId + "]");

			// Check App Information for App exist and name.
			String appName = null;

			StringBuffer sqlStmt = new StringBuffer();

//////// uncheck in standalone mode
//
//			IDAO appDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStmt.toString(), IDAO.class);
//			appDao.set("AppName", appId);
//			appDao.getImplementationObject().setTableName("APP");
//			appDao.getImplementationObject().createSelectSql();
//			appDao.select();
//
//			if (appDao.size() == 0)
//				throw new Exception("No Such App [" + appId + "]");
//
//			appDao.next();
//

			// Check for company code for App
			sqlStmt = new StringBuffer();
			sqlStmt.append("SELECT * FROM COMTABLE ")
					.append("WHERE COMNAME = ?comname ");

			IDAO comDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStmt.toString(), IDAO.class);

			comDao.set("comname", accountId);

			comDao.select();

			if (comDao.size() == 0) {
				sqlStmt = new StringBuffer();
				sqlStmt.append("INSERT INTO COMTABLE(COMCODE, COMNAME, ALIAS) ")
						.append("VALUES(?comcode, ?comname, ?alias)");

				comDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStmt.toString(), IDAO.class);
				comDao.set("comcode", accountId);
				comDao.set("comname", accountId);
				comDao.set("alias", accountId);
				comDao.insert();
			} else {
				comDao.next();
			}

			// Insert Tenant Join Data
			sqlStmt = new StringBuffer();
			sqlStmt.append("INSERT INTO APPMAPPING(APPID, COMCODE, PLANID, APPNAME, EFFECTIVEDATE, EXPIRATIONDATE, ISTRIAL) ")
					.append("VALUES(?appId, ?comcode, ?planId, ?appName, ?effectiveDate, ?expirationDate, ?isTrial)");

			IDAO appMDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStmt.toString(), IDAO.class);
			appMDao.set("appId", appId);
			appMDao.set("appName", appId);
			appMDao.set("comcode", comDao.get("comcode"));
			appMDao.set("planId", planId);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 30);
            Date expDate = cal.getTime();
            Date effData = new Date();
            appMDao.set("effectiveDate", effData);
            appMDao.set("expirationDate", expDate);
            appMDao.set("isTrial", 0);
			appMDao.insert();

			return Response.ok()
					.entity("SUCCESS")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();


		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	}

	public Response getSubscription(String appId, String accountId) {
		System.out.println(String.format("appId = %s, accountId = %s", appId, accountId));

		Subscription subscription = null;
		try {
			StringBuffer sqlStmt = new StringBuffer();
//			sqlStmt.append("SELECT * FROM COMTABLE ")
//					.append("WHERE COMNAME = ?comname ");
//
//			IDAO comDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStmt.toString(), IDAO.class);
//
//			comDao.set("comname", accountId);
//
//			comDao.select();

			sqlStmt = new StringBuffer();
			sqlStmt.append("SELECT * FROM APPMAPPING ")
					.append("WHERE APPNAME = ?appName ")
					.append("AND COMCODE = ?comcode ");

			IDAO appMDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStmt.toString(), IDAO.class);

			appMDao.set("appName", appId);
			appMDao.set("comcode", accountId);

			appMDao.select();
			if (appMDao.size() > 0) {
				appMDao.next();
				subscription = new Subscription();
				subscription.setAppId(appId);
				subscription.setAccountId(accountId);
				String planId = (appMDao.getString("planId"));
                subscription.setEffectiveDate(appMDao.getDate("effectiveDate"));
                subscription.setExpirationDate(appMDao.getDate("expirationDate"));
				Response response = getPlans(appId);
				List<Plan> plans = (List<Plan>) response.getEntity();
				for (Plan plan : plans) {
					if (plan.getId().equals(planId)) {
						subscription.setPlan(plan);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
		return Response.ok()
				.entity(subscription)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}

	@Override
	public Response getInvoice(String appId, String accountId, String monthId) {
		try {
			ServiceUsages serviceUsages = billingService.getAccountUsages(appId, accountId, "", monthId);
			return Response.ok()
					.entity(serviceUsages)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	}

	@Override
	 public Response getServiceStatus(String appId, String accountId, String serviceId, String monthId) {
		try {
			UsageStatus usageStatus = billingService.getUsageStatus(appId, accountId, serviceId, monthId);
			return Response.ok()
					.entity(usageStatus)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	}

	@Override
	public Response unblockService(String appId, String accountId, String serviceId, String monthId) {
		try {
			Map<String, String> result = billingService.unblockService(appId, accountId, serviceId, monthId);
			return Response.ok()
					.entity(result)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	}

	@Override
	public String truncateUsageData(String appId) {
		return billingService.truncateUsageStatusData(appId);
	}

	@Override
	public String truncateAllData(String appId) {
		return billingService.truncateAllData(appId);
	}
}
