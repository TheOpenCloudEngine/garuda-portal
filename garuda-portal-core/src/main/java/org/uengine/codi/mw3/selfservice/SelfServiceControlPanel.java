package org.uengine.codi.mw3.selfservice;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Group;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;
import org.uengine.bss.application.MetadataProperty;
import org.uengine.bss.application.MetadataPropertyListFace;
import org.uengine.bss.application.TenantApp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by soo on 2015. 6. 30..
 */
public class SelfServiceControlPanel {

    List<MetadataProperty> metadataPropertyList = new ArrayList<MetadataProperty>();

    @Face(faceClass = MetadataPropertyListFace.class)
    @Group(name = "MetadataProperty")
    public List<MetadataProperty> getMetadataPropertyList() {
        return metadataPropertyList;
    }

    public void setMetadataPropertyList(List<MetadataProperty> metadataPropertyList) {
        this.metadataPropertyList = metadataPropertyList;
    }


    String appName;
    @Hidden
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    String comName;
    @Hidden
    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    String comcode;
    @Hidden
    public String getComCode() {
        return comcode;
    }

    public void setComCode(String comcode) {
        this.comcode = comcode;
    }

    public SelfServiceControlPanel() {

    }

    @ServiceMethod(callByContent = true)
    public void popupApp() throws Exception { //TODO naming -> appSelected() is better ==> 영어공부 좀 할 필]

        this.setMetadataPropertyList(TenantApp.load(appName).getMetadataPropertyList());

        Iterator iterator = this.getMetadataPropertyList().iterator();
        while(iterator.hasNext()){
            MetadataProperty metadataProperty = (MetadataProperty) iterator.next();
            metadataProperty.getMetaworksContext().setWhere("selfservice");
        }
    }

    @Face(displayName = "저장")
    @ServiceMethod(callByContent = true)
    public void save() throws FileNotFoundException {
        TenantApp tenantApp = new TenantApp();
        tenantApp.setId(appName);
        tenantApp.setMetadataPropertyList(this.getMetadataPropertyList());
        tenantApp.save();
    }
}

