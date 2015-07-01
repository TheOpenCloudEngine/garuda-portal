package org.uengine.bss.application;

import org.metaworks.MetaworksFile;
import org.uengine.util.UEngineUtil;

/**
 * Created by hoo.lim on 6/30/2015.
 */
public class MetadataFile extends MetaworksFile{

    String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String overrideUploadPathPrefix() {
        return TenantApp.getGarudaAppPath(getAppId(),getTenantId());
    }

    @Override
    public String renameUploadFile(String filename) {
        return UEngineUtil.getCalendarDir() + "/" + super.renameUploadFile(filename);
    }

    @Override
    public String renameUploadFileWithMimeType(String filename, String mimeType) {
        // TODO Auto-generated method stub
        return UEngineUtil.getCalendarDir() + "/" + super.renameUploadFileWithMimeType(filename, mimeType);
    }
}
