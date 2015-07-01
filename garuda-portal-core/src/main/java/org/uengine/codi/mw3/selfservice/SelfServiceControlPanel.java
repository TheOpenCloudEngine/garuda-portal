package org.uengine.codi.mw3.selfservice;

import com.defaltcompany.sample.Comtable;
import com.defaltcompany.sample.IComtable;
import org.metaworks.MetaworksContext;
import org.metaworks.MetaworksException;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.AutowiredToClient;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.metadata.*;
import org.oce.garuda.multitenancy.TenantContext;
import org.uengine.codi.mw3.ide.Project;
import org.uengine.codi.mw3.ide.ResourceNode;
import org.uengine.codi.mw3.ide.Workspace;
import org.uengine.codi.mw3.marketplace.App;
import org.uengine.codi.mw3.marketplace.AppMapping;
import org.uengine.codi.mw3.marketplace.IAppMapping;
import org.uengine.codi.mw3.model.Session;
import org.uengine.codi.mw3.resource.ResourceManager;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by soo on 2015. 6. 30..
 */
@Face(displayName = "$SelfServicePortal")
public class SelfServiceControlPanel {

    public SelfServiceControlPanel() {

    }

    IAppMapping appMapping;

    public IAppMapping getAppMapping() {
        return appMapping;
    }

    public void setAppMapping(IAppMapping appMapping) {
        this.appMapping = appMapping;
    }

    ArrayList<MetadataProperty> metadataProperties;

    public ArrayList<MetadataProperty> getMetadataProperties() {
        return metadataProperties;
    }

    public void setMetadataProperties(ArrayList<MetadataProperty> metadataProperties) {
        this.metadataProperties = metadataProperties;
    }

    FilePropertyPanel filePropertyPanel;

    public FilePropertyPanel getFilePropertyPanel() {
        return filePropertyPanel;
    }

    public void setFilePropertyPanel(FilePropertyPanel filePropertyPanel) {
        this.filePropertyPanel = filePropertyPanel;
    }

    ImagePropertyPanel imagePropertyPanel;

    public ImagePropertyPanel getImagePropertyPanel() {
        return imagePropertyPanel;
    }

    public void setImagePropertyPanel(ImagePropertyPanel imagePropertyPanel) {
        this.imagePropertyPanel = imagePropertyPanel;
    }

    ProcessPropertyPanel processPropertyPanel;

    public ProcessPropertyPanel getProcessPropertyPanel() {
        return processPropertyPanel;
    }

    public void setProcessPropertyPanel(ProcessPropertyPanel processPropertyPanel) {
        this.processPropertyPanel = processPropertyPanel;
    }

    StringPropertyPanel stringPropertyPanel;

    public StringPropertyPanel getStringPropertyPanel() {
        return stringPropertyPanel;
    }

    public void setStringPropertyPanel(StringPropertyPanel stringPropertyPanel) {
        this.stringPropertyPanel = stringPropertyPanel;
    }

    FormPropertyPanel formPropertyPanel;

    public FormPropertyPanel getFormPropertyPanel() {
        return formPropertyPanel;
    }

    public void setFormPropertyPanel(FormPropertyPanel formPropertyPanel) {
        this.formPropertyPanel = formPropertyPanel;
    }


    MetadataProperty metadataProperty;

    public MetadataProperty getMetadataProperty() {
        return metadataProperty;
    }

    public void setMetadataProperty(MetadataProperty metadataProperty) {
        this.metadataProperty = metadataProperty;
    }

    int appId;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    String comCode;

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    String comName;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    MetadataXML metadataXml;

    public MetadataXML getMetadataXml() {
        return metadataXml;
    }

    public void setMetadataXml(MetadataXML metadataXml) {
        this.metadataXml = metadataXml;
    }


    Workspace workspace;

    @AutowiredToClient
    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    boolean iframe;

    public boolean getIframe() {
        return iframe;
    }

    public void setIframe(boolean iframe) {
        this.iframe = iframe;
    }

    @AutowiredFromClient
    public Session session;

    public void load(Session session) throws Exception {

        AppMapping appMp = new AppMapping();
        appMp.session = session;
        appMp.setComCode(session.getCompany().getComCode());
        appMp.setIsDeleted(false);

        IAppMapping appList = appMp.findMyApps();
        appList.getMetaworksContext().setWhen("filter");
        appList.getMetaworksContext().setWhere("ssp");

        this.setAppMapping(appList);

        Workspace workspace = new Workspace();

        while (appList.next()) {
            workspace.addProject(session.getCompany().getComCode(), appList.getProjectName(), appList.getAppName());
        }

        this.setWorkspace(workspace);
    }

    public void load(Session session, int appId) throws Exception {

        AppMapping appMp = new AppMapping();
        appMp.session = session;
        appMp.setAppId(appId);

        IAppMapping appList = appMp.findMyApp();

        appList.getMetaworksContext().setWhen("filter");
        appList.getMetaworksContext().setWhere("ssp");

        this.setAppMapping(appList);

        Workspace workspace = new Workspace();

        while (appList.next()) {
            workspace.addProject(session.getCompany().getComCode(), appList.getProjectName(), appList.getAppName());
        }

        this.setWorkspace(workspace);
    }

    @ServiceMethod(callByContent = true)
    public void popupApp() throws Exception { //TODO naming -> appSelected() is better ==> 영어공부 좀 할 필]
        setIframe(true);
        Comtable comtable = new Comtable();

        comtable.setComName(getComName());
        IComtable icomTable = comtable.findMeWithAppName();
        setComCode(icomTable.getComCode());

        AppMapping appMapping = new AppMapping();

        appMapping.setAppName(getAppName());
        appMapping.setComCode(getComCode());

        IAppMapping iappMapping = appMapping.findMeWithAppName();
        appId = iappMapping.getAppId();

        //이전에 선택한 메타데이터를 보여주지 않고 없애기 위한 처리
        MetadataProperty mp = new MetadataProperty();
        mp.setId("metaDetailView");
        mp.getMetaworksContext().setWhere("ssp");
        this.setMetadataProperty(mp);

        ResourceNode resourceNode = new ResourceNode();
        resourceNode.setProjectId(iappMapping.getProjectName());
        resourceNode.setId(Project.METADATA_FILENAME);

        MetadataXML metadataXML = null;
        try {
            metadataXML = MetadataXML.loadForTenant(appName);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        MetadataXML metadataDefinition;

        App app = new App();
        app.setAppId(getAppId());

        // 앱 타입 가져오기
        if (!(app.databaseMe().getAppType() != null && App.APP_TYPE_PROJECT.equals(app.databaseMe().getAppType())))
            throw new Exception("No such app: " + app.getAppId());

        metadataDefinition = MetadataXML.loadDefinition(getAppName());

        if (metadataXML == null) //firstly generated for tenant
            metadataXML = metadataDefinition;

        String metadataFilePath = ResourceManager.getResourcePath(getAppName(), TenantContext.getThreadLocalInstance().getTenantId(), Project.METADATA_FILENAME);
        metadataXML.setFilePath(metadataFilePath);

        if (metadataXML == null) {
            throw new MetaworksException("메타데이타 파일이 없습니다.");
        }

        this.metadataProperties = new ArrayList<MetadataProperty>();

        ArrayList<FileProperty> fileProperties = new ArrayList<FileProperty>();
        ArrayList<ImageProperty> imageProperties = new ArrayList<ImageProperty>();
        ArrayList<ProcessProperty> processProperties = new ArrayList<ProcessProperty>();
        ArrayList<StringProperty> stringProperties = new ArrayList<StringProperty>();
        ArrayList<FormProperty> formProperties = new ArrayList<FormProperty>();

        Map<String, MetadataProperty> metadataXMLMap = metadataXML.toMap();

        for (MetadataProperty metadataProperty : metadataDefinition.getProperties()) {
            String name = metadataProperty.getName();

            if (metadataXMLMap.containsKey(name)) {
                metadataProperty = metadataXMLMap.get(name);  //change to tenant's one
            } else {
                metadataXML.getProperties().add(metadataProperty); //add new one if metadata definition is updated.
            }

            metadataProperty = (MetadataProperty) metadataProperty.selectType();
            metadataProperty.setResourceNode(resourceNode);
            metadataProperty.setMetaworksContext(new MetaworksContext());
            metadataProperty.getMetaworksContext().setWhere("ssp");


            if (MetadataProperty.FILE_PROP.equals(metadataProperty.getType())) {
                fileProperties.add((FileProperty) metadataProperty);
            } else if (MetadataProperty.FORM_PROP.equals(metadataProperty.getType())) {
                formProperties.add((FormProperty) metadataProperty);
            } else if (MetadataProperty.IMAGE_PROP.equals(metadataProperty.getType())) {
                imageProperties.add((ImageProperty) metadataProperty);
            } else if (MetadataProperty.PROCESS_PROP.equals(metadataProperty.getType())) {
                processProperties.add((ProcessProperty) metadataProperty);
            } else if (MetadataProperty.STRING_PROP.equals(metadataProperty.getType())) {
                stringProperties.add((StringProperty) metadataProperty);
            }

            this.metadataProperties.add(metadataProperty.getIndex(), metadataProperty);
        }

        metadataXML.setProperties(this.metadataProperties);
//		this.getAppMapping().getMetaworksContext().setWhere("ssp");

        metadataXML.setMetaworksContext(new MetaworksContext());
        metadataXML.getMetaworksContext().setHow("selfservice");

        //add panel
        FilePropertyPanel filePanel = new FilePropertyPanel(fileProperties);
        this.setFilePropertyPanel(filePanel);

        ImagePropertyPanel imagePanel = new ImagePropertyPanel(imageProperties);
        this.setImagePropertyPanel(imagePanel);

        ProcessPropertyPanel processPanel = new ProcessPropertyPanel(processProperties);
        this.setProcessPropertyPanel(processPanel);

        StringPropertyPanel stringPanel = new StringPropertyPanel(stringProperties);
        this.setStringPropertyPanel(stringPanel);

        FormPropertyPanel formPanel = new FormPropertyPanel(formProperties);
        this.setFormPropertyPanel(formPanel);

        this.setMetadataXml(metadataXML);
    }

    @ServiceMethod(callByContent = true)
    public void selectedApp() throws Exception { //TODO naming -> appSelected() is better ==> 영어공부 좀 할 필

        //이전에 선택한 메타데이터를 보여주지 않고 없애기 위한 처리
        MetadataProperty mp = new MetadataProperty();
        mp.setId("metaDetailView");
        mp.getMetaworksContext().setWhere("ssp");
        this.setMetadataProperty(mp);

        // TODO: 작업해야함
        AppMapping appMapping = new AppMapping();
        appMapping.setAppId(this.getAppId());
        appMapping.setComCode(session.getCompany().getComCode());

        //TODO:  Comcode 로 갈것인가 URL로 갈것인가? 아니면 TenantContext 가 알아서 하는가?
        String tenantId = session.getCompany().getComCode();
        if (TenantContext.getThreadLocalInstance() != null && TenantContext.getThreadLocalInstance().getTenantId() != null) {
            tenantId = TenantContext.getThreadLocalInstance().getTenantId();
        }

//		Project project = new Project();
//		project.setId(appMapping.findMe().getProjectName());

        ResourceNode resourceNode = new ResourceNode();
        resourceNode.setProjectId(appMapping.findMe().getProjectName());
        resourceNode.setId(Project.METADATA_FILENAME);


        String appName = appMapping.databaseMe().getAppName();
//		String appBasePath = MetadataBundle.getProjectBasePath(appName, tenantId);

        MetadataXML metadataXML = null;
        try {
            metadataXML = MetadataXML.loadForTenant(appName);
//			metadataXML.setFilePath(appBasePath + File.separatorChar + Project.METADATA_FILENAME);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        MetadataXML metadataDefinition;

        App app = new App();
        app.setAppId(this.getAppId());

        // 앱 타입 가져오기
        if (!(app.databaseMe().getAppType() != null && App.APP_TYPE_PROJECT.equals(app.databaseMe().getAppType())))
            throw new Exception("No such app: " + app.getAppId());


        metadataDefinition = MetadataXML.loadDefinition(getAppName());

        if (metadataXML == null) //firstly generated for tenant
            metadataXML = metadataDefinition;

        String metadataFilePath = ResourceManager.getResourcePath(appName, TenantContext.getThreadLocalInstance().getTenantId(), Project.METADATA_FILENAME);
        metadataXML.setFilePath(metadataFilePath);

        if (metadataXML == null) {
            throw new MetaworksException("메타데이타 파일이 없습니다.");
        }

        this.metadataProperties = new ArrayList<MetadataProperty>();

        ArrayList<FileProperty> fileProperties = new ArrayList<FileProperty>();
        ArrayList<ImageProperty> imageProperties = new ArrayList<ImageProperty>();
        ArrayList<ProcessProperty> processProperties = new ArrayList<ProcessProperty>();
        ArrayList<StringProperty> stringProperties = new ArrayList<StringProperty>();
        ArrayList<FormProperty> formProperties = new ArrayList<FormProperty>();

        Map<String, MetadataProperty> metadataXMLMap = metadataXML.toMap();

        for (MetadataProperty metadataProperty : metadataDefinition.getProperties()) {
            String name = metadataProperty.getName();

            if (metadataXMLMap.containsKey(name)) {
                metadataProperty = metadataXMLMap.get(name);  //change to tenant's one
            } else {
                metadataXML.getProperties().add(metadataProperty); //add new one if metadata definition is updated.
            }

            metadataProperty = (MetadataProperty) metadataProperty.selectType();
            metadataProperty.setResourceNode(resourceNode);
            metadataProperty.setMetaworksContext(new MetaworksContext());
            metadataProperty.getMetaworksContext().setWhere("ssp");


            if (MetadataProperty.FILE_PROP.equals(metadataProperty.getType())) {
                fileProperties.add((FileProperty) metadataProperty);
            } else if (MetadataProperty.FORM_PROP.equals(metadataProperty.getType())) {
                formProperties.add((FormProperty) metadataProperty);
            } else if (MetadataProperty.IMAGE_PROP.equals(metadataProperty.getType())) {
                imageProperties.add((ImageProperty) metadataProperty);
            } else if (MetadataProperty.PROCESS_PROP.equals(metadataProperty.getType())) {
                processProperties.add((ProcessProperty) metadataProperty);
            } else if (MetadataProperty.STRING_PROP.equals(metadataProperty.getType())) {
                stringProperties.add((StringProperty) metadataProperty);
            }

            this.metadataProperties.add(metadataProperty.getIndex(), metadataProperty);
        }

        metadataXML.setProperties(this.metadataProperties);
        this.getAppMapping().getMetaworksContext().setWhere("ssp");

        metadataXML.setMetaworksContext(new MetaworksContext());
        metadataXML.getMetaworksContext().setHow("selfservice");

        //add panel
        FilePropertyPanel filePanel = new FilePropertyPanel(fileProperties);
        this.setFilePropertyPanel(filePanel);

        ImagePropertyPanel imagePanel = new ImagePropertyPanel(imageProperties);
        this.setImagePropertyPanel(imagePanel);

        ProcessPropertyPanel processPanel = new ProcessPropertyPanel(processProperties);
        this.setProcessPropertyPanel(processPanel);

        StringPropertyPanel stringPanel = new StringPropertyPanel(stringProperties);
        this.setStringPropertyPanel(stringPanel);

        FormPropertyPanel formPanel = new FormPropertyPanel(formProperties);
        this.setFormPropertyPanel(formPanel);

        this.setMetadataXml(metadataXML);
    }

    @ServiceMethod(callByContent = true)
    public void saveProperty() {
        System.out.println("save property");
    }
}

