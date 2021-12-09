/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

import com.bern.casql.busniess.MortgageApplicationDao;
import com.bern.casql.entity.BaseApplicationModel;
import com.bern.casql.entity.MortgageApplication;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author unknown
 */
@Path("mortgage/applications")
public class MortgageApplicationsResource {

    @Inject
    MortgageApplicationDao manager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewApplication(MortgageApplication mortgageApplication) {

        BaseApplicationModel newApplication = manager.saveApplication(mortgageApplication);

        if (newApplication == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ResponseBody.errorMessage(new String[]{"this is not a valid customer"}))
                    .build();
        }
        return Response.ok().entity(ResponseBody.successMessage()).build();
    }
}
