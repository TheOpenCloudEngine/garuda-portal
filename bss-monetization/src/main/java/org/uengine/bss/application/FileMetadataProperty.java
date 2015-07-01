package org.uengine.bss.application;

import org.metaworks.MetaworksContext;
import org.metaworks.MetaworksFile;

import java.io.FileNotFoundException;

public class FileMetadataProperty extends MetadataProperty<MetadataFile> {

    public FileMetadataProperty(){
        setDefaultValue(new MetadataFile());
        this.setMetaworksContext(new MetaworksContext());
        if(this.getMetaworksContext().getWhen() == null){
            this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
        }
    }

    @Override
    public MetadataFile getDefaultValue() {
        return super.getDefaultValue();
    }

    @Override
    public void setDefaultValue(MetadataFile defaultValue) {
        super.setDefaultValue(defaultValue);
    }

    public void upload(String appId, String tenantId) throws Exception {
        getDefaultValue().setAppId(appId);
        getDefaultValue().setTenantId(tenantId);
        getDefaultValue().upload();
    }
}
