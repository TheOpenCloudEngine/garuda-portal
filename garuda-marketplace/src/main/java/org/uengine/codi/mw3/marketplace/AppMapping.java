package org.uengine.codi.mw3.marketplace;

import org.metaworks.*;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.dao.Database;
import org.metaworks.website.MetaworksFile;
import org.uengine.codi.mw3.CodiClassLoader;
import org.uengine.codi.mw3.common.MainPanel;
import org.uengine.codi.mw3.model.Application;
import org.uengine.codi.mw3.model.*;
import org.uengine.codi.mw3.widget.IFrame;

import java.io.File;
import java.util.Calendar;

public class AppMapping extends Database<IAppMapping> implements IAppMapping {
	
	private final static String APPMAPPING_TYPE = "app";
	
	public AppMapping() {
		this.setType(APPMAPPING_TYPE);
	}
	
	int appId;
		public int getAppId() {
			return appId;
		}
		public void setAppId(int appId) {
			this.appId = appId;
		}
	
	String comCode;
		public String getComCode() {
			return comCode;
		}
		public void setComCode(String comCode) {
			this.comCode = comCode;
		}
	
	String appName;
		public String getAppName() {
			return appName;
		}
		public void setAppName(String appName) {
			this.appName = appName;
		}
	
	boolean isDeleted;
		public boolean getIsDeleted() {
			return isDeleted;
		}
		public void setIsDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
		}

	boolean selected;
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	
	String projectName;
		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

	MetaworksFile logoFile;
		public MetaworksFile getLogoFile() {
			return logoFile;
		}
		public void setLogoFile(MetaworksFile logoFile) {
			this.logoFile = logoFile;
		}
	
	String type;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	String empCode;
		public String getEmpCode() {
			return empCode;
		}
		public void setEmpCode(String empCode) {
			this.empCode = empCode;
		}
	String url;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	String appType;
		public String getAppType() {
			return appType;
		}
		public void setAppType(String appType) {
			this.appType = appType;
		}	
	public IAppMapping findMe() throws Exception {

		IAppMapping findApp = (IAppMapping) Database.sql(IAppMapping.class, "select appmapping.appId, appmapping.comcode, appmapping.appname, appmapping.isdeleted, appmapping.url, appmapping.appType, bpm_knol.name projectName from appmapping appmapping, app, bpm_knol where appmapping.appId = app.appId and app.projectId = bpm_knol.id and appmapping.appid=?appId and appmapping.comcode=?comCode");
		
		findApp.setAppId(this.getAppId());
		findApp.setComCode(this.getComCode());
		findApp.select();
		
		if(findApp.next())
			return findApp;
		else
			return null;
	}
	
	public IAppMapping findMyApps() throws Exception {
		return findMyApps(0);
	}
	
	public IAppMapping findMyApps(int limitCount) throws Exception {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select appmapping.appId, appmapping.comcode, appmapping.appname, appmapping.isdeleted, appmapping.url, appmapping.appType, bpm_knol.name projectName, item.* ")
		   .append(" from appmapping, bpm_knol,  ")
		   .append(" 	(select app.appId, app.logoFileName, app.logoContent, app.projectId, recentItem.empcode, recentItem.updateDate, recentItem.clickedCount from app left join recentItem on app.appid=recentItem.itemId) item")
		   .append(" where appmapping.appId = item.appId ")
		   .append("	and item.projectId = bpm_knol.id ")
		   .append("	and appmapping.comcode=?comCode ")
		   .append("	and appmapping.isdeleted=?isdeleted order by item.clickedCount desc " + ((limitCount==0)? " " : "limit " + limitCount));
		
		IAppMapping findApp = (IAppMapping) Database.sql(IAppMapping.class, sql.toString());
		findApp.setComCode(this.getComCode());
		findApp.setIsDeleted(this.getIsDeleted());
		findApp.select();
		
		return findApp;
	}
	
	public IAppMapping findMyApp() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select appmapping.appId, appmapping.comcode, appmapping.appname, appmapping.isdeleted, appmapping.url, appmapping.appType, bpm_knol.name projectName, item.* ")
		   .append(" from appmapping, bpm_knol,  ")
		   .append(" 	(select app.appId, app.logoFileName, app.logoContent, app.projectId, recentItem.empcode, recentItem.updateDate, recentItem.clickedCount from app left join recentItem on app.appid=recentItem.itemId) item")
		   .append(" where appmapping.appId = ?appId ")
		   .append("	and appmapping.appId = item.appId ")
		   .append("	and item.projectId = bpm_knol.id ");
		
		IAppMapping findApp = (IAppMapping) Database.sql(IAppMapping.class, sql.toString());
		findApp.setAppId(this.getAppId());
		findApp.select();
		
		return findApp;
	}
	
	public void findProject(String appId) throws Exception {
		String projectName = null;
		
		this.setProjectName(projectName);
	}

	public IAppMapping findMeWithAppName() throws Exception {

		IAppMapping findApp = (IAppMapping) Database.sql(IAppMapping.class, "select appmapping.appId, appmapping.comcode, appmapping.appname, appmapping.isdeleted, appmapping.url, appmapping.appType, appmapping.planId, appmapping.effectiveDate, appmapping.expirationDate, appmapping.isTrial, bpm_knol.name projectName from appmapping appmapping, app, bpm_knol where appmapping.appId = app.appId and app.projectId = bpm_knol.id and appmapping.appname=?appname and appmapping.comcode=?comCode");

		findApp.setAppName(this.getAppName());
		findApp.setComCode(this.getComCode());
		findApp.select();

		if(findApp.next())
			return findApp;
		else
			return null;
	}
	
	public Object[] clickAppList() throws Exception {
		
//		updateFavoriteApp();
//
//		String title = "앱 : " + getAppName();
//		Object[] returnObject = Perspective.loadInstanceListPanel(session, "app", String.valueOf(getAppId()), title);
//		session.setLastPerspecteType("app");
//		session.setLastSelectedItem(String.valueOf(getAppId()));
//
//
//		DashboardWindow window = new DashboardWindow();
//		window.setPanel(returnObject[1]);
//		window.setTitle(title);
//
//		return new Object[]{session , window};
//
		return null;
	}
	
	public void updateFavoriteApp() throws Exception {
		RecentItem recentItem = new RecentItem();
		recentItem.setEmpCode(this.getEmpCode());
		recentItem.setItemId(String.valueOf(this.getAppId()));
		recentItem.setItemType(APPMAPPING_TYPE);
		recentItem.setUpdateDate(Calendar.getInstance().getTime());
		
		recentItem.add();
	}
	
	
	
	@Override
	public Object[] openAppBrowser() throws Exception {
/*		OceMain oceMain = new OceMain();
		oceMain.loadAppSns(session);
		return new MainPanel(oceMain);
		session.setUx("oce_app");
		session.setLastPerspecteType("oce_app");
		session.setLastSelectedItem(String.valueOf(this.getAppId()));
		
		DashboardWindow appDashboard = new DashboardWindow();
		appDashboard.session = session;
		appDashboard.setTitle(this.getAppName());
		
		App app = new App();
		app.setAppId(this.getAppId());
		app.copyFrom(app.databaseMe());
		
		app.databaseMe().getUrl();
		
		InstanceListPanel instanceListPanel = new InstanceListPanel();
		
		Object[] returnObject = Perspective.loadInstanceListPanel(session, "oce_app", session.getLastSelectedItem(), appDashboard.getTitle());
		
		instanceListPanel = (InstanceListPanel) returnObject[1];
		instanceListPanel.session = session;
		
		Layout appPanel = new Layout();
		appPanel.setOptions("togglerLength_open:0, spacing_open:0, spacing_closed:0, east__spacing_open:1, east__size:'400'");
		appPanel.setCenter(new IFrame(app.url.replace("$tenantId", session.getEmployee().getGlobalCom())));
		appPanel.setCenter(new IFrame("http://" + GlobalContext.getPropertyString("vm.manager.ip","localhost") + ":8080/" + app.getSubDomain()));
		appPanel.setEast(returnObject[1]);
		
		appDashboard.setPanel(appPanel);
		
		return new Object[]{new Refresh(appDashboard)};*/
		
//		Application application = null;
//		if( App.APP_TYPE_PROJECT.equals(appType)){
//			IFrame iframe = new IFrame(this.getUrl());
//
//			application = new IFrameApplication();
//			((IFrameApplication)application).setContent(iframe);
//		}else if( App.APP_TYPE_PROCESS.equals(appType)){
//			// jar 파일을 읽어들인다. // App 의 projectAnalysis() 메서드에서 정의된 파일
//			String appFilePath = MetadataBundle.getProjectBasePath(this.getAppName()) +  File.separatorChar  +this.getAppName() + ".jar" ;
//			File appJarFile = new File(appFilePath);
//			if( appJarFile.exists() ){
//				// 해당 jar 파일을 classPath 에 등록함
//				CodiClassLoader prerCl = (CodiClassLoader)Thread.currentThread().getContextClassLoader();
//				prerCl.addSourcePath(appJarFile);
//				Thread.currentThread().setContextClassLoader(prerCl);
//			}
//			if( this.getUrl() == null || "".equals(this.getUrl())){
//				throw new Exception("프로세스를 찾을수 없습니다.");
//			}
//			application = new ProcessApp(session, this.getAppName(), this.getUrl() );
//		}
//
//		return new Object[]{new Refresh(application), new Refresh(session), new Refresh(application.loadTopCenterPanel(session)), new ToEvent(new Popup(), EventContext.EVENT_CLOSE)};
		return null;
	}

	public MainPanel goSelfService() throws Exception{
		
//		SelfService selfService = new SelfService();
//		selfService.session = session;
//		selfService.setPageNavigator(new OcePageNavigator());
//		selfService.load(this.getAppId());
//
//		return new MainPanel(selfService);
		return null;
	}
	
	public Object[] deleteApp() throws Exception{
//		this.setAppId(this.getAppId());
//
//		this.deleteDatabaseMe();
//
//		return new Object[]{new Remover(this), new Refresh(new OcePerspectivePanel(session))};
		return null;
	}
	
	@Override
	public Object[] pickUp() throws Exception {
		this.getMetaworksContext().setHow(IUser.HOW_PICKER);
		this.getMetaworksContext().setWhere(WHERE_EVER);
		
		return new Object[]{new ToOpener(this), new ToEvent(ServiceMethodContext.TARGET_OPENER, EventContext.EVENT_CHANGE), new ToEvent(ServiceMethodContext.TARGET_SELF, EventContext.EVENT_CLOSE)};
	}
	
	@AutowiredFromClient
	public Session session;
	
}

