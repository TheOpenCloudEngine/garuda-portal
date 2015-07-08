package org.uengine.bss.monetization.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hoo.lim on 7/7/2015.
 */
@Path("/tenant")
public interface TenantService {

    @POST
    @Path("/signUp")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response tenantSignUp(@FormParam("tenantId") String tenantId, @FormParam("tenantName") String tenantName,
                                 @FormParam("userId") String userId, @FormParam("userName") String userName,
                                 @FormParam("password") String password);
}
