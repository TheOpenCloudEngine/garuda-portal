package org.uengine.bss.application;


import org.metaworks.MetaworksContext;
import org.metaworks.annotation.Face;

public class JavaMetadataProperty extends MetadataProperty<String> {

    public JavaMetadataProperty(){
        setDefaultValue("");
        this.setMetaworksContext(new MetaworksContext());
        this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
    }

    @Override
    @Face(faceClassName="org.uengine.codi.mw3.widget.SourceCodeFace")
    public String getDefaultValue() {
        return super.getDefaultValue();
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        super.setDefaultValue(defaultValue);
    }
}
