/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

import com.bern.casql.busniess.BorrowerDao;
import com.bern.casql.entity.Borrower;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author unknown
 */
@Path("borrowers")
public class BorrowersResource  {

    @Inject
    BorrowerDao borrowerManager;

    @Path("{borrowerId}")

    public BorrowerResource getBorrowerInfo(@PathParam("borrowerId") long id)  {
        return new BorrowerResource(id, borrowerManager);
    }
    
    
    @Path("ssn/{ssn}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBorrowerBySSN(@PathParam("ssn") int ssn)  {
        Borrower returnBorrower =  borrowerManager.assembleBorrower(ssn);
        return buildBorrowerResponse(returnBorrower, "Borrwer With That SSN Does Not Exist");
    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Borrower> fetchAllBorrowers()  {
        return borrowerManager.fetchAllBorrowers();
    }
    
    private Response buildBorrowerResponse(Borrower returnBorrower, String errorString){
        if(returnBorrower == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ResponseBody.errorMessage(new String[]{errorString}))
                    .build();
        }
        else{
            return Response.ok(returnBorrower).build();
        }
    }

}
