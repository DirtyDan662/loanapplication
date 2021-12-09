/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.busniess;

import com.bern.casql.entity.Borrower;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author unknown
 */
@ApplicationScoped
public abstract class ModelApplicationDao {

    @Resource(mappedName = "java:/PostgresDS")
    protected DataSource dataSource;

    @Inject
    protected BorrowerDao borrowerManager;

    public ModelApplicationDao() {
    }

    public void relateApplicationBorrower(long applicationId, long borrowerId, String applicationType){
        String query = "INSERT INTO public.application_borrower (application_id, borrower_id, application_type)";
        //String values = "VALUES ( " + applicationId + ", " + borrowerId + ", '" + applicationType + "' )";
        String values = "VALUES( (?), (?), (?) )";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement update = conn.prepareStatement(query + values);) {
            update.setLong(1, applicationId);
            update.setLong(2,borrowerId);
            update.setString(3, applicationType);
            
            update.executeUpdate();
        }catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }

    public void saveBorrowers(List<Borrower> borrowers, long applicationId, String applicationType){
        for (Borrower borrower : borrowers) {
            long borrowerId = borrowerManager.saveBorrower(borrower);
            //save relation between application and borrower
            relateApplicationBorrower(applicationId, borrowerId, applicationType);
        }
    }

}
