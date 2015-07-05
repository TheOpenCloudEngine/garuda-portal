package org.uengine.codi.mw3.metadata;

import org.metaworks.annotation.Face;
import org.uengine.bss.application.MetadataProperty;

import java.util.List;

/**
 * Created by soo on 2015. 7. 3..
 */
@Face(ejsPath="dwr/metaworks/genericfaces/CleanObjectFace.ejs"  , options={"disableHeight"}, values={"true"})
public class TextPropertyPanel {

    List<MetadataProperty> textProperties;
    public List<MetadataProperty> getTextProperties() {
        return textProperties;
    }
    public void setTextProperties(List<MetadataProperty> textProperties) {
        this.textProperties = textProperties;
    }

    public TextPropertyPanel() {
    }

    public TextPropertyPanel(List<MetadataProperty> textProperties) {
        this.setTextProperties(textProperties);
    }
}