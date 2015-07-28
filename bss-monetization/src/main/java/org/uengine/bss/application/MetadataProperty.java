package org.uengine.bss.application;

import org.metaworks.MetaworksContext;
import org.metaworks.Refresh;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.uengine.codi.mw3.selfservice.SelfServiceControlPanel;

import java.io.FileNotFoundException;

@Face(options = {"hideTitle"}, values = {"true"})
public class MetadataProperty<T> {


    String key;

    @Order(1)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    T defaultValue;

    @Order(2)
    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    String type;

    @Range(
            options = {"Text", "File", "Code(Rule)", "Process"},
            values = {"Text", "File", "Java", "Process"}
    )
    @Order(3)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String id;

    @Id
    @Hidden
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    MetaworksContext metaworksContext;

    public MetaworksContext getMetaworksContext() {
        return metaworksContext;
    }

    public void setMetaworksContext(MetaworksContext metaworksContext) {
        this.metaworksContext = metaworksContext;
    }

    @ServiceMethod(eventBinding = "change", bindingFor = "type", target = ServiceMethodContext.TARGET_SELF)
    public MetadataProperty changeType(@Payload("type") String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MetadataProperty metadataProperty = (MetadataProperty) Class.forName(getClass().getPackage().getName() + "." + type + "MetadataProperty").newInstance();
        metadataProperty.setType(type);
        return metadataProperty;
    }

    @Hidden
    @ServiceMethod(callByContent = true, target = ServiceMethodContext.TARGET_AUTO)
    public MetadataProperty showPropertyDetail() throws Exception {
        MetadataProperty detailProperty = this;
        detailProperty.setId(SelfServiceControlPanel.METADATA_DETAIL_VIEW_ID);
        detailProperty.getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
        detailProperty.getMetaworksContext().setWhere("ssp");

        return detailProperty;
    }

    @ServiceMethod(callByContent = true)
    public Object[] save(@AutowiredFromClient SelfServiceControlPanel selfServiceControlPanel) throws Exception {
        selfServiceControlPanel.save(selfServiceControlPanel.getMetadataPropertyList().indexOf(this));
        return new Object[]{this, new Refresh(selfServiceControlPanel)};
    }
}
