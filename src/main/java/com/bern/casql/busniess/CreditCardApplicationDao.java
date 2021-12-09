/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.busniess;

import com.bern.casql.entity.BaseApplicationModel;
import com.bern.casql.entity.CreditCardApplication;
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
public class CreditCardApplicationDao extends ModelApplicationDao {

    public static final String APPLICATION_TYPE = "credit_card";
    public static final String TABLE_NAME = "\"public\".\"creditcardapplication\"";

    public BaseApplicationModel findById(long id){
        return assembleCreditCardApplication(id);
    }

    public BaseApplicationModel saveApplication(@Valid CreditCardApplication newApplication) {
        String query = "INSERT INTO" + TABLE_NAME + " (cardtype, requestedcreditlimit)";
        String values = "VALUES( (?), (?) )";
        //save application
        try (Connection conn = dataSource.getConnection();
                PreparedStatement update = conn.prepareStatement(query + values);) {

            update.setString(1, newApplication.getCardType());
            update.setInt(2, newApplication.getRequestedCreditLimit().intValue());

            update.executeUpdate();

            long applicationId = getLatestRowId();

            //save borrower
            saveBorrowers(newApplication.getBorrowers(), applicationId, APPLICATION_TYPE);
            return assembleCreditCardApplication(applicationId);
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
            return returnId;
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }

    private BaseApplicationModel assembleCreditCardApplication(long applicationId) {

        CreditCardApplication creditCardApplication = null;
        String query = "SELECT id, cardtype, requestedcreditlimit FROM " + TABLE_NAME + " WHERE id=(?);";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {
            statement.setObject(1, applicationId);
            
            try (ResultSet resultSet = statement.executeQuery();) {

                while (resultSet.next()) {
                    creditCardApplication = new CreditCardApplication();
                    creditCardApplication.setId(resultSet.getLong("id"));
                    creditCardApplication.setCardType(resultSet.getString("cardtype"));
                    creditCardApplication.setRequestedCreditLimit(BigInteger.valueOf(resultSet.getInt("requestedcreditlimit")));
                    creditCardApplication.setBorrowers(borrowerManager.assembleBorrowers(applicationId));
                }
            }
        }catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        } 
        return creditCardApplication;
    }

}
