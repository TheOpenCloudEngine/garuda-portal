package org.uengine.bss.application;

import org.metaworks.MetaworksContext;
import org.metaworks.MetaworksFile;

import java.io.FileNotFoundException;

public class FileMetadataProperty extends MetadataProperty<MetadataFile> {

    public FileMetadataProperty(){
        setDefaultValue(new MetadataFile());
        this.setMetaworksContext(new MetaworksContext());
        this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
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
        if(getDefaultValue().getUploadedPath() == null ||
                (getDefaultValue().getUploadedPath() != null && getDefaultValue().getDeletedPath() != null)){
            getDefaultValue().upload();
        }
    }
}
