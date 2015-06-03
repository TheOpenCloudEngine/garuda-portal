package org.uengine.application;

import org.metaworks.MetaworksFile;

public class FileMetadataProperty extends MetadataProperty<MetaworksFile> {

    public FileMetadataProperty(){
        setDefaultValue(new MetaworksFile());
    }

}
