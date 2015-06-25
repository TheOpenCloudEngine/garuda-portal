package org.uengine.bss.application;

import org.metaworks.MetaworksContext;
import org.metaworks.MetaworksFile;

public class FileMetadataProperty extends MetadataProperty<MetaworksFile> {

    public FileMetadataProperty(){
        setDefaultValue(new MetaworksFile());
        this.setMetaworksContext(new MetaworksContext());
        this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
    }

}
