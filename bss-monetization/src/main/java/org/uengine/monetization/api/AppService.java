package org.uengine.monetization.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;


@Path("/app")
public interface AppService {

	@GET
	@Path("/{appId}/plan")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	Response getPlans(@PathParam("appId") String appId) throws FileNotFoundException;

	@GET
	@Path("/{appId}/plan/{planId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	Response getPlan(@PathParam("appId") String appId, @PathParam("planId") String planId) throws FileNotFoundException;

	@GET
	@Path("/{appId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	Response getApp(@PathParam("appId") String appId) throws FileNotFoundException;

    @GET
    @Path("/{appId}/account/{accountId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    Response getSubscription(@PathParam("appId") String appId, @PathParam("accountId") String accountId);


    @POST
	@Path("/{appId}/subscribe")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	Response subscribePlan(@PathParam("appId") String appId,
							 @FormParam("accountId") String accountId, @FormParam("planId") String planId);

	@GET
	@Path("/{appId}/account/{accountId}/invoice")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getInvoice(@PathParam("appId") String appId,
							   @PathParam("accountId") String accountId, @FormParam("monthId") String monthId);



	@GET
	@Path("/{appId}/account/{accountId}/service/{serviceId}/status")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getServiceStatus(@PathParam("appId") String appId, @PathParam("accountId") String accountId,
									 @PathParam("serviceId") String serviceId, @FormParam("monthId") String monthId);

	@GET
	@Path("/{appId}/account/{accountId}/service/{serviceId}/unblock")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response unblockService(@PathParam("appId") String appId, @PathParam("accountId") String accountId,
	                                 @PathParam("serviceId") String serviceId, @FormParam("monthId") String monthId);



	@GET
	@Path("/{appId}/settings/truncateUsageData")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String truncateUsageData(@PathParam("appId") String appId);


	@GET
	@Path("/{appId}/settings/truncateAllData")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String truncateAllData(@PathParam("appId") String appId);



}


