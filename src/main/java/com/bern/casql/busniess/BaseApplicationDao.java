/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.busniess;

import com.bern.casql.entity.BaseApplicationModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author unknown
 */
@ApplicationScoped
public class BaseApplicationDao extends ModelApplicationDao {

    @Inject
    MortgageApplicationDao mortgageManager;

    @Inject
    CreditCardApplicationDao creditManager;
    HashMap<String, String> tableMap;

    public BaseApplicationDao() {
        tableMap = new HashMap<>();
        tableMap.put(CreditCardApplicationDao.APPLICATION_TYPE, CreditCardApplicationDao.TABLE_NAME);
        tableMap.put(MortgageApplicationDao.APPLICATION_TYPE, CreditCardApplicationDao.TABLE_NAME);
    }

    public BaseApplicationModel findById(long id, String applicationType) {
        //if application does not exist, return null
        if (applicationType == null) {
            return null;
        }

        if (applicationType.equals(CreditCardApplicationDao.APPLICATION_TYPE)) {
            return creditManager.findById(id);
        } else {
            return mortgageManager.findById(id);
        }
    }

    public BaseApplicationModel findById(long applicationId) {
        return findById(applicationId, findApplicationType(applicationId));
    }

    public List<BaseApplicationModel> getApplicationsFromBorrower(long borrowerId) {

        List<BaseApplicationModel> applications = new ArrayList<>();
        String query = "SELECT application_id, application_type FROM \"public\".\"application_borrower\" WHERE borrower_id=(?); ";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {

            statement.setObject(1, borrowerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long applicationId = resultSet.getLong("application_id");
                    String applicationType = resultSet.getString("application_type");

                    applications.add(findById(applicationId, applicationType));
                }
            }
            return applications;
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }

    public void deleteApplication(long applicationId) {
        String applicationType = findApplicationType(applicationId);
        String tableName = null;

        //if app does not exist, do nothing
        if (applicationType == null) {
            return;
        } else {
            tableName = tableMap.get(applicationType);
        }
        String abDeleteQuery = "DELETE FROM public.application_borrower WHERE application_id=(?)";
        String nativeDeleteQuery = "DELETE FROM " + tableName + " WHERE id = (?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement abDeleteStatement = conn.prepareStatement(abDeleteQuery);
                PreparedStatement nativeDeleteStatement = conn.prepareStatement(nativeDeleteQuery);) {

            abDeleteStatement.setObject(1, applicationId);
            abDeleteStatement.executeUpdate();

            nativeDeleteStatement.setObject(1, applicationId);
            nativeDeleteStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }

    }

    private String findApplicationType(long applicationId){
        String query = "SELECT DISTINCT application_type FROM \"public\".\"application_borrower\" WHERE application_id=(?);";
        String applicationType = null;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {
            statement.setObject(1, applicationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    applicationType = resultSet.getString("application_type");
                }
            }
            conn.close();
            return applicationType;
        }catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }
}
