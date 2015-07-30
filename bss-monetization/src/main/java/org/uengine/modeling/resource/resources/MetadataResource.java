package org.uengine.modeling.resource.resources;

import org.metaworks.annotation.AutowiredFromClient;
import org.uengine.codi.mw3.model.Session;
import org.uengine.modeling.resource.DefaultResource;

/**
 * Created by hoo.lim on 7/27/2015.
 */
public class MetadataResource extends DefaultResource{
    @AutowiredFromClient
    public Session session;


}
