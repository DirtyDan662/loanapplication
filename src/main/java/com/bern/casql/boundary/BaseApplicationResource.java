/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

import com.bern.casql.busniess.BaseApplicationDao;
import com.bern.casql.entity.BaseApplicationModel;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author unknown
 */
public class BaseApplicationResource {

    private BaseApplicationDao manager;
    private long id;
    private String applicationType;

    public BaseApplicationResource(BaseApplicationDao manager, long id, String applicationType) {
        this.manager = manager;
        this.id = id;
        this.applicationType = applicationType;
    }

    public BaseApplicationResource(BaseApplicationDao manager, long id) {
        this.manager = manager;
        this.id = id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById()  {
        BaseApplicationModel application = manager.findById(id);
        if (application != null) {
            return Response.ok(application).build();
        }
        ;
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseBody.errorMessage("failed to retrive application",new String[]{"Application with id " + id + " does not exist"}))
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteApplication()  {
        manager.deleteApplication(id);
        return Response.ok().entity(ResponseBody.successMessage()).build();
    }

}
