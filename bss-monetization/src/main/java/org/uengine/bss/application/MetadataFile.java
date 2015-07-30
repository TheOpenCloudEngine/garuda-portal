package org.uengine.bss.application;

import org.metaworks.MetaworksFile;
import org.uengine.util.UEngineUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by hoo.lim on 6/30/2015.
 */
public class MetadataFile extends MetaworksFile{

    String resourcePath;

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String overrideUploadPathPrefix() {
        return getResourcePath();
    }
}
