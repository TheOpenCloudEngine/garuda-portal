package org.uengine.codi.mw3.marketplace.model;

import org.metaworks.annotation.Face;
import org.uengine.codi.ITool;

@Face(ejsPath = "dwr/metaworks/genericfaces/FormFace.ejs")
public class ApprovalComplete implements ITool {

    String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    String serverIp;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    String realmId;

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    String imageId;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    String hardwareProfileId;

    public String getHardwareProfileId() {
        return hardwareProfileId;
    }

    public void setHardwareProfileId(String hardwareProfileId) {
        this.hardwareProfileId = hardwareProfileId;
    }

    @Override
    public void onLoad() throws Exception {

    }

    @Override
    public void beforeComplete() throws Exception {

    }

    @Override
    public void afterComplete() throws Exception {

    }


}
