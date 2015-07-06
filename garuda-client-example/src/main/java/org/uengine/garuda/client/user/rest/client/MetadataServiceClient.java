package org.uengine.garuda.client.user.rest.client;

import org.restlet.Component;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * Created by hoo.lim on 7/2/2015.
 */
public class MetadataServiceClient {
    public static String getTextMetadata(String domain, String appId, String tenantId, String keyValue) throws IOException {
        ClientResource resource = new ClientResource(domain + "/services/metadata/app/" + appId + "/text/" + keyValue);

        Representation response = resource.get();

        return response.getText();
    }
}
