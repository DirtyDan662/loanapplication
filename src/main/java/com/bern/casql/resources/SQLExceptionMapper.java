/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.resources;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author unknown
 */
@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {

    @Override
    public Response toResponse(SQLException e) {
        return Response.serverError().entity("SQL Exception Error: " + e).build();
        //return Response.status(Response.Status.SERVICE_UNAVAILABLE).header("Error", "SQL Exception").header("cause", e).build();
    }

    
}