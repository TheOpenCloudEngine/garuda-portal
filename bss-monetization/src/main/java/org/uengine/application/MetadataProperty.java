package org.uengine.application;

import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Payload;
import org.metaworks.annotation.Range;
import org.metaworks.annotation.ServiceMethod;

public class MetadataProperty <T>{

    String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }


    T defaultValue;

        public T getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
        }

    String type;
    @Range(
            options={"Text", "File"},
            values ={"Text", "File"}
    )
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    @ServiceMethod(bindingFor = "type", target= ServiceMethodContext.TARGET_SELF)
    public MetadataProperty changeType(@Payload("type") String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (MetadataProperty) Class.forName(getClass().getPackage().getName() + "." + type + "MetadataProperty").newInstance();
    }

}
