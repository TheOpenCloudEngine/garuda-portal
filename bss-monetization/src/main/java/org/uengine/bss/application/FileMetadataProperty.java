package org.uengine.bss.application;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.metaworks.MetaworksContext;
import org.metaworks.Refresh;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.ServiceMethod;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileMetadataProperty extends MetadataProperty<MetadataFile> {

    @XStreamOmitField
    Object filePreview;
    public Object getFilePreview() {
        return filePreview;
    }
    public void setFilePreview(Object filePreview) {
        this.filePreview = filePreview;
    }

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
        if(getDefaultValue().getUploadedPath() == null ||
                (getDefaultValue().getUploadedPath() != null && getDefaultValue().getDeletedPath() != null)){
            getDefaultValue().setAppId(appId);
            getDefaultValue().setTenantId(tenantId);
            getDefaultValue().upload();
        }
    }

    @ServiceMethod(callByContent=true, bindingHidden=true, bindingFor="defaultValue", eventBinding={"change"} ,target= ServiceMethodContext.TARGET_APPEND)
    public Object[] changeFile() throws Exception{
        // TODO 여기를 안타서.. 셀프 서비스 포탈에서 이미지가 변경이 안됨
        this.getDefaultValue().getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
        setFilePreview(this.getDefaultValue());

        return new Object[]{new Refresh(this.getDefaultValue() , true) ,  new Refresh(this.getFilePreview() , true)};

    }
}
