package org.uengine.bss.application;

import com.thoughtworks.xstream.XStream;
import org.metaworks.Remover;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Payload;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dao.TransactionContext;
import org.metaworks.widget.ModalWindow;
import org.uengine.bss.monetization.*;
import org.uengine.kernel.GlobalContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by hoo.lim on 6/30/2015.
 */
public class TenantApp extends App{
    private final static String DEFAULT_TENANT_FOLDER = "default";

    private final static String UENGINE_METADATA_FILE = "uengine.metadata";

    public static String getCodebaseRoot(){
        return GlobalContext.getPropertyString("codebase") + File.separator;
    }

    public static String getGarudaAppPath(String appId, String tenantId){
        return getCodebaseRoot()+ appId + File.separator + getTenantFolder(tenantId) + File.separator;
    }

    public static String getGarudaAppDefaultPath(String appId){
        return getCodebaseRoot()+ appId + File.separator + DEFAULT_TENANT_FOLDER + File.separator;
    }

    public static String getGarudaAppDefaultPathAndCreateIfNotExist(String appId){
        String defaultAppPath = TenantApp.getGarudaAppDefaultPath(appId);

        createDirectoryIfNotExist(defaultAppPath);

        return defaultAppPath;
    }

    public static String getTenantFolder(String tenantId){
        return tenantId == null ? DEFAULT_TENANT_FOLDER : tenantId;
    }

    public static String getGarudaAppPathAndCreateIfNotExist(String appId, String tenantId){
        String appPath = getGarudaAppPath(appId, tenantId);

        createDirectoryIfNotExist(appPath);

        return appPath;
    }

    private static void createDirectoryIfNotExist(String path){
        File appDirectory = new File(path);
        boolean ifCreate = appDirectory.mkdirs();

        if(ifCreate){
            System.out.printf("Directory %s is created!", path);
        }else if(appDirectory.exists()){
            System.out.printf("Directory %s is already existed!", path);
        }else{
            System.out.printf("Directory %s cannot be created!", path);
        }
    }

    public static File getFileOfGarudaApp(String appId, String tenantId, String fileName) throws FileNotFoundException {
        File garudaAppFile = new File(getGarudaAppPathAndCreateIfNotExist(appId, tenantId) + fileName);
        return garudaAppFile;
    }

    public static File getDefaultFileOfGarudaApp(String appId, String fileName) throws FileNotFoundException {
        File garudaAppFile = new File(getGarudaAppDefaultPathAndCreateIfNotExist(appId) + fileName);
        return garudaAppFile;
    }

    @ServiceMethod(callByContent = true)
    public Object save() throws FileNotFoundException {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.toXML(this, System.out);

        for(MetadataProperty metadataProperty : getMetadataPropertyList()){
            if(metadataProperty instanceof FileMetadataProperty){
                try {
                    ((FileMetadataProperty) metadataProperty).upload(getId(),getTenantid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            File f = TenantApp.getFileOfGarudaApp(getId(), getTenantid(), UENGINE_METADATA_FILE);
            System.out.println("Wrote file to " + f.getAbsolutePath());
            xstream.toXML(this, new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new Remover(new ModalWindow());
    }

    @ServiceMethod(target= ServiceMethodContext.TARGET_SELF)
    public static TenantApp load(@Payload("id") String appId) throws FileNotFoundException {
        return TenantApp.load(appId, null);
    }

    public static TenantApp load(String appId, String tenantId) throws FileNotFoundException {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);

        File uengine_metadata_file = TenantApp.getFileOfGarudaApp(appId, tenantId, UENGINE_METADATA_FILE);
        if(!uengine_metadata_file.exists()){
            uengine_metadata_file = TenantApp.getDefaultFileOfGarudaApp(appId, UENGINE_METADATA_FILE);
        }

        TenantApp app = (TenantApp) xstream.fromXML(new InputStreamReader(new FileInputStream(uengine_metadata_file), StandardCharsets.UTF_8));

        for(MetadataProperty metadataProperty : app.getMetadataPropertyList()){
            if(metadataProperty instanceof FileMetadataProperty){
                MetadataFile metadataFile = (MetadataFile) metadataProperty.getDefaultValue();
                if(metadataFile != null){
                    metadataFile.setAppId(appId);
                    metadataFile.setTenantId(TenantApp.getTenantFolder(tenantId));
                }
            }
        }

        // Init Plan service information.
        List<Service> serviceList = app.getServiceList();
        List<Plan> planList = app.getPlanList();
        for (Service service : serviceList) {
            for (Plan plan : planList) {
                if(plan.getOneTimeServiceAndRateList() != null) {
                    for (OneTimeServiceAndRate serviceAndRate : plan.getOneTimeServiceAndRateList()) {
                        if (serviceAndRate != null && serviceAndRate.getService().getId().equals(service.getId())) {
                            serviceAndRate.setService(service);
                        }
                    }
                }
                if(plan.getRecurringServiceAndRateList() != null) {
                    for (RecurringServiceAndRate serviceAndRate : plan.getRecurringServiceAndRateList()) {
                        if (serviceAndRate != null && serviceAndRate.getService().getId().equals(service.getId())) {
                            serviceAndRate.setService(service);
                        }
                    }
                }
                if(plan.getUsageServiceAndRateList() != null) {
                    for (UsageServiceAndRate serviceAndRate : plan.getUsageServiceAndRateList()) {
                        if (serviceAndRate != null && serviceAndRate.getService().getId().equals(service.getId())) {
                            serviceAndRate.setService(service);
                        }
                    }
                }
            }
        }

        if(TransactionContext.getThreadLocalInstance()!=null)
            TransactionContext.getThreadLocalInstance().setSharedContext("app", app);

        return app;
    }

}

