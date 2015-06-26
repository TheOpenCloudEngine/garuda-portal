package org.uengine.bss.application;


import org.metaworks.MetaworksContext;

public class TextMetadataProperty extends MetadataProperty<String> {

    public TextMetadataProperty(){
        setDefaultValue("");
        this.setMetaworksContext(new MetaworksContext());
        this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
    }

    @Override
    public String getDefaultValue() {
        return super.getDefaultValue();
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        super.setDefaultValue(defaultValue);
    }
}
