package org.uengine.bss.application;

import org.metaworks.MetaworksContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;

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
            options = {"Text", "File"},
            values = {"Text", "File"}
    )
    @Order(3)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
