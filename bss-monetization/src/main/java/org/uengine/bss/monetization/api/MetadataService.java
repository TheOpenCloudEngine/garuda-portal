package org.uengine.bss.monetization.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;

/**
 * Created by hoo.lim on 6/29/2015.
 */
@Path("/")
public interface MetadataService {

    @GET
    @Path("app/{appId}/text/{key}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getTextMetadata(@PathParam("appId") String appId, @PathParam("key") String key)
            throws FileNotFoundException;

    @GET
    @Path("{appId}/{key}")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response getImageMetadata(@PathParam("appId") String appId,@PathParam("key") String key)
            throws FileNotFoundException;

}
