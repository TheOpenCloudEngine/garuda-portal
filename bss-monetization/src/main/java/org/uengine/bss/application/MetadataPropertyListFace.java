package org.uengine.bss.application;


import org.metaworks.model.MetaworksElement;
import org.metaworks.widget.ListFace;

public class MetadataPropertyListFace extends ListFace<MetadataProperty> {

    @Override
    public MetaworksElement createNewElement() {
        MetaworksElement metaworksElement = super.createNewElement();
        metaworksElement.setValue(new TextMetadataProperty());
        return metaworksElement;
    }
}
