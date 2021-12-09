/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

import com.bern.casql.busniess.BaseApplicationDao;
import com.bern.casql.entity.BaseApplicationModel;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author unknown
 */
@Path("applications")
public class BaseApplicationsResource {

    @Inject
    BaseApplicationDao manager;

    


    @Path("{id}")
    public BaseApplicationResource findApplication(@PathParam("id") long id) {
        return new BaseApplicationResource(manager, id);
    }

   
    
    @Path("all/{borrowerId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BaseApplicationModel> getAllApplicationsFromBorrower(@PathParam("borrowerId") long id)  {
        return manager.getApplicationsFromBorrower(id);
    }

}
