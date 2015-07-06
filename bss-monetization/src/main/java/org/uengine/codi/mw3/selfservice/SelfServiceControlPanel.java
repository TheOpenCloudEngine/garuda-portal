package org.uengine.codi.mw3.selfservice;

import org.metaworks.MetaworksContext;
import org.metaworks.Refresh;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Group;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;
import org.uengine.bss.application.*;
import org.uengine.codi.mw3.metadata.FilePropertyPanel;
import org.uengine.codi.mw3.metadata.TextPropertyPanel;

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
    @Hidden
    public List<MetadataProperty> getMetadataPropertyList() {
        return metadataPropertyList;
    }

    public void setMetadataPropertyList(List<MetadataProperty> metadataPropertyList) {
        this.metadataPropertyList = metadataPropertyList;
    }

    List<MetadataProperty> fileMetadataPropertyList = new ArrayList<MetadataProperty>();

    public List<MetadataProperty> getFileMetadataPropertyList() {
        return fileMetadataPropertyList;
    }

    public void setFileMetadataPropertyList(List<MetadataProperty> fileMetadataPropertyList) {
        this.fileMetadataPropertyList = fileMetadataPropertyList;
    }

    List<MetadataProperty> textMetadataPropertyList = new ArrayList<MetadataProperty>();

    public List<MetadataProperty> getTextMetadataPropertyList() {
        return textMetadataPropertyList;
    }

    public void setTextMetadataPropertyList(List<MetadataProperty> textMetadataPropertyList) {
        this.textMetadataPropertyList = textMetadataPropertyList;
    }

    MetadataProperty metadataProperty;

    public MetadataProperty getMetadataProperty() {
        return metadataProperty;
    }

    public void setMetadataProperty(MetadataProperty metadataProperty) {
        this.metadataProperty = metadataProperty;
    }

    FilePropertyPanel filePropertyPanel;

    public FilePropertyPanel getFilePropertyPanel() {
        return filePropertyPanel;
    }

    public void setFilePropertyPanel(FilePropertyPanel filePropertyPanel) {
        this.filePropertyPanel = filePropertyPanel;
    }

    TextPropertyPanel textPropertyPanel;

    public TextPropertyPanel getTextPropertyPanel() {
        return textPropertyPanel;
    }

    public void setTextPropertyPanel(TextPropertyPanel textPropertyPanel) {
        this.textPropertyPanel = textPropertyPanel;
    }

    String appName;

    @Hidden
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public SelfServiceControlPanel() {

    }

    @ServiceMethod(callByContent = true)
    public void popupApp() throws Exception { //TODO naming -> appSelected() is better ==> 영어공부 좀 할 필]

        this.setMetadataPropertyList(TenantApp.load(appName).getMetadataPropertyList());

        MetadataProperty mp = new MetadataProperty();
        mp.setId("metaDetailView");
        mp.setMetaworksContext(new MetaworksContext());
        mp.getMetaworksContext().setHow("hide");
        mp.getMetaworksContext().setWhere("ssp");
        this.setMetadataProperty(mp);

        Iterator iterator = this.getMetadataPropertyList().iterator();
        while (iterator.hasNext()) {
            MetadataProperty metadataProperty = (MetadataProperty) iterator.next();
            metadataProperty.getMetaworksContext().setWhere("ssp");
            metadataProperty.getMetaworksContext().setWhen(MetaworksContext.WHEN_VIEW);
            if(metadataProperty instanceof FileMetadataProperty){
                ((FileMetadataProperty) metadataProperty).setFilePreview(metadataProperty.getDefaultValue());
                fileMetadataPropertyList.add(metadataProperty);
            }else if(metadataProperty instanceof TextMetadataProperty){
                textMetadataPropertyList.add(metadataProperty);
            }
        }

        FilePropertyPanel filePropertyPanel = new FilePropertyPanel(fileMetadataPropertyList);
        this.setFilePropertyPanel(filePropertyPanel);

        TextPropertyPanel textPropertyPanel = new TextPropertyPanel(textMetadataPropertyList);
        this.setTextPropertyPanel(textPropertyPanel);

    }

    public Refresh save(int index) throws Exception {
        TenantApp tenantApp = new TenantApp();
        tenantApp.setId(appName);

        this.getMetadataProperty().setId("");

        Iterator iterator = this.getMetadataPropertyList().iterator();
        while(iterator.hasNext()){
            MetadataProperty mp = (MetadataProperty)iterator.next();
                if(this.getMetadataProperty().getKey().equals(mp.getKey())){
                    this.getMetadataPropertyList().remove(mp);
                    this.getMetadataPropertyList().add(this.getMetadataProperty());
                    break;
                }
        }

        tenantApp.setMetadataPropertyList(metadataPropertyList);
        this.setMetadataPropertyList(metadataPropertyList);
        tenantApp.save();

        return new Refresh(this, true);

    }
}

