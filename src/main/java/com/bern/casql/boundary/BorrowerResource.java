/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.boundary;

import com.bern.casql.busniess.BorrowerDao;
import com.bern.casql.entity.Borrower;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author unknown
 */
public class BorrowerResource {

    private long id;
    private BorrowerDao borrowerManager;

    public BorrowerResource(long id, BorrowerDao borrowerManager) {
        this.id = id;
        this.borrowerManager = borrowerManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBorrowerById() {
        Borrower returnBorrower = borrowerManager.assembleBorrower(id);
        return buildBorrowerResponse(returnBorrower, "Borrower With ID " + id + " does not exist");
    }

    @Path("location")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBorrowerLocation(JsonObject locationUpdate) {
        if (!locationUpdate.containsKey("address") && !locationUpdate.containsKey("city") && !locationUpdate.containsKey("state") && !locationUpdate.containsKey("zip")) {
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ResponseBody.errorMessage(new String[]{"Invalid Keys", "JSON should contain address, city, state, and zip keys"}))
                    .build();
        } else {
            String address = locationUpdate.getString("address");
            String city = locationUpdate.getString("city");
            String state = locationUpdate.getString("state");
            int zip = locationUpdate.getInt("zip");

            Borrower returnBorrower = borrowerManager.updateBorrowerLocation(id, address, city, state, zip);
            return buildBorrowerResponse(returnBorrower, "Borrower With ID " + id + " does not exist");
        }
    }

    private Response buildBorrowerResponse(Borrower returnBorrower, String errorString) {
        if (returnBorrower == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ResponseBody.errorMessage("failed to retrive application", new String[]{errorString}))
                    .build();
        } else {
            return Response.ok(returnBorrower).build();
        }
    }

}
