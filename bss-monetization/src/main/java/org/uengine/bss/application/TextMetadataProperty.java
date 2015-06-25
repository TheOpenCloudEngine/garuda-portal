package org.uengine.bss.application;


import org.metaworks.MetaworksContext;

public class TextMetadataProperty extends MetadataProperty<String> {

    public TextMetadataProperty(){
        this.setMetaworksContext(new MetaworksContext());
        this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
    }

}
