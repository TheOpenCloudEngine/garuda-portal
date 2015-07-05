package org.uengine.codi.mw3.metadata;

import org.metaworks.annotation.Face;
import org.uengine.bss.application.MetadataProperty;

import java.util.List;

/**
 * Created by soo on 2015. 7. 3..
 */
@Face(ejsPath="dwr/metaworks/genericfaces/CleanObjectFace.ejs"  , options={"disableHeight"}, values={"true"})
public class FilePropertyPanel {

    List<MetadataProperty> fileProperties;
    public List<MetadataProperty> getFileProperties() {
        return fileProperties;
    }
    public void setFileProperties(List<MetadataProperty> fileProperties) {
        this.fileProperties = fileProperties;
    }

    public FilePropertyPanel() {
    }

    public FilePropertyPanel(List<MetadataProperty> fileProperties) {
        this.setFileProperties(fileProperties);
    }
}