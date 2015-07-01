package org.uengine.bss.monetization.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;

/**
 * Created by hoo.lim on 6/29/2015.
 */
@Path("/metadata")
public interface MetadataService {

    @GET
    @Path("/app/{appId}/tenant/{tenantId}/text/{key}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getTextMetadata(@PathParam("appId") String appId,@PathParam("tenantId") String tenantId,
                                  @PathParam("key") String key) throws FileNotFoundException;

    @GET
    @Path("/app/{appId}/tenant/{tenantId}/image/{key}")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response getImageMetadata(@PathParam("appId") String appId,@PathParam("tenantId") String tenantId,
                                  @PathParam("key") String key, @HeaderParam("If-Modified-Since") String modified)
            throws FileNotFoundException;

}
