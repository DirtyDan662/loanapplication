/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.busniess;

import com.bern.casql.entity.Employment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

/**
 *
 * @author unknown
 */
@ApplicationScoped
public class EmploymentDao {

    @Resource(mappedName = "java:/PostgresDS")
    DataSource dataSource;

    public void saveEmployment(Employment employment, long borrowerId) {
        String insertSmt = "INSERT INTO public.employment (employername, employerphone, enddate, startdate, borrower_id)";
        String values = "VALUES( (?), (?), (?), (?), (?) );";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement update = conn.prepareStatement(insertSmt + values);) {
            update.setString(1, employment.getEmployerName());
            update.setLong(2, employment.getEmployerPhone());
            update.setDate(3, Date.valueOf(employment.getEndDate()));
            update.setDate(4, Date.valueOf(employment.getStartDate()));
            update.setLong(5, borrowerId);

            update.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }

    }

    public List<Employment> assembleEmployments(long borrowerId){
        ArrayList<Employment> employments = new ArrayList<>();

        String query = "SELECT id, employername, employerphone, enddate, startdate FROM \"public\".\"employment\" WHERE borrower_id=(?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {

            statement.setObject(1, borrowerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employment employment = new Employment();
                    employment.setId(resultSet.getInt("id"));
                    employment.setEmployerName(resultSet.getString("employername"));
                    employment.setEmployerPhone(resultSet.getLong("employerphone"));
                    employment.setEndDate(resultSet.getDate("enddate").toLocalDate());
                    employment.setStartDate(resultSet.getDate("startdate").toLocalDate());

                    employments.add(employment);
                }
            }
            return employments;
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }
}
