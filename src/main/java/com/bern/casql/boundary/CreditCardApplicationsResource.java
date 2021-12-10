/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

import com.bern.casql.busniess.CreditCardApplicationDao;
import com.bern.casql.entity.BaseApplicationModel;
import com.bern.casql.entity.CreditCardApplication;
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
@Path("cc/applications")
public class CreditCardApplicationsResource {

    @Inject
    CreditCardApplicationDao manager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewApplication(CreditCardApplication creditCardApplication) {

        BaseApplicationModel newApplication = manager.saveApplication(creditCardApplication);
        return Response.ok().entity(ResponseBody.successMessage()).build();

    }

}
