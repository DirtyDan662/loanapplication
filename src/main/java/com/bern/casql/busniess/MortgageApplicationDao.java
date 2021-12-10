/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.busniess;

import com.bern.casql.entity.BaseApplicationModel;
import com.bern.casql.entity.MortgageApplication;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

/**
 *
 * @author unknown
 */
@ApplicationScoped
public class MortgageApplicationDao extends ModelApplicationDao {

    public static final String APPLICATION_TYPE = "mortgage";
    public static final String TABLE_NAME = "\"public\".\"mortgageapplication\"";

    public BaseApplicationModel findById(long id){
        return assembleMortgageApplication(id);
    }

    public BaseApplicationModel saveApplication(@Valid MortgageApplication newApplication) {
        String query = "INSERT INTO" + TABLE_NAME + " (lengthofmortgageyears, loanamount, mortgagetype)";
        String values = "VALUES( (?), (?), (?) )";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement update = conn.prepareStatement(query + values)) {

            update.setInt(1, newApplication.getLengthOfMortgageYears());
            update.setInt(2, newApplication.getLoanAmount().intValue());
            update.setString(3, newApplication.getMortgageType());

            //save application
            update.executeUpdate();
            
            
            //save borrower
            long applicationId = getLatestRowId();
            saveBorrowers(newApplication.getBorrowers(), applicationId, APPLICATION_TYPE);
            conn.close();
            return assembleMortgageApplication(applicationId);
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }

    }

    private long getLatestRowId() {
        long returnId = -1;
        String query = "SELECT id FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1;";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {

            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    returnId = resultSet.getLong("id");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
        return returnId;
    }

    private BaseApplicationModel assembleMortgageApplication(long applicationId) {

        MortgageApplication mortgageApplication = null;
        String query = "SELECT id, lengthofmortgageyears, loanamount, mortgagetype FROM " + TABLE_NAME + " WHERE id=(?);";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {

            statement.setObject(1, applicationId);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    mortgageApplication = new MortgageApplication();

                    mortgageApplication.setId(resultSet.getLong("id"));
                    mortgageApplication.setLengthOfMortgageYears(resultSet.getInt("lengthofmortgageyears"));
                    mortgageApplication.setLoanAmount(BigInteger.valueOf(resultSet.getInt("loanamount")));
                    mortgageApplication.setMortgageType(resultSet.getString("mortgagetype"));
                    mortgageApplication.setBorrowers(borrowerManager.assembleBorrowers(applicationId));
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
        return mortgageApplication;
    }

}
