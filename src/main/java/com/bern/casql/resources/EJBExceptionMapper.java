/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.resources;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author unknown
 */
@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    @Override
    public Response toResponse(EJBException e) {
        Throwable cause = e.getCause();
        if(cause instanceof OptimisticLockException){
            return Response.status(Response.Status.CONFLICT).header("cause", "conflict occured: " + cause).build();
        }
        if(cause instanceof PersistenceException){
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .header("cause","violation occured: " + cause)
                    .entity("hint: borrower with that ssn may already exist")
                    .build();
        }
        else{
            return Response.serverError().entity("Unknown Error: " + cause).build();
        }
    }
    
}
