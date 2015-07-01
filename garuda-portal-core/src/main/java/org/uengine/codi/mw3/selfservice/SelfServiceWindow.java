package org.uengine.codi.mw3.selfservice;

import org.metaworks.annotation.Face;
import org.metaworks.metadata.MetadataProperty;
import org.uengine.codi.mw3.model.Session;

/**
 * Created by soo on 2015. 6. 30..
 */
@Face(displayName="$SelfServicePortal", ejsPath="genericfaces/Window.ejs", options={"hideAddBtn", "hideLabels"}, values={"true", "true"})
public class SelfServiceWindow {

    SelfServiceControlPanel selfServiceControlPanel;
    public SelfServiceControlPanel getSelfServiceControlPanel() {
        return selfServiceControlPanel;
    }
    public void setSelfServiceControlPanel(
            SelfServiceControlPanel selfServiceControlPanel) {
        this.selfServiceControlPanel = selfServiceControlPanel;
    }

    public SelfServiceWindow(Session session) throws Exception{

        SelfServiceControlPanel selfServiceControlPanel = new SelfServiceControlPanel();
        selfServiceControlPanel.load(session);

        MetadataProperty metadataProperty = new MetadataProperty();
        metadataProperty.setId("metaDetailView");
        metadataProperty.getMetaworksContext().setWhere("ssp");

        selfServiceControlPanel.setMetadataProperty(metadataProperty);

        this.setSelfServiceControlPanel(selfServiceControlPanel);
    }

    public SelfServiceWindow(Session session, int appId) throws Exception{

        SelfServiceControlPanel selfServiceControlPanel = new SelfServiceControlPanel();
        selfServiceControlPanel.load(session, appId);

        MetadataProperty metadataProperty = new MetadataProperty();
        metadataProperty.setId("metaDetailView");
        metadataProperty.getMetaworksContext().setWhere("ssp");

        selfServiceControlPanel.setMetadataProperty(metadataProperty);

        this.setSelfServiceControlPanel(selfServiceControlPanel);
    }

}