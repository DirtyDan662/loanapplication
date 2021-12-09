/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.casql.busniess;

import com.bern.casql.entity.Borrower;
import com.bern.casql.entity.Employment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class BorrowerDao {

    @Resource(mappedName = "java:/PostgresDS")
    private DataSource dataSource;

    @Inject
    private EmploymentDao employmentManager;

    public long saveBorrower(Borrower borrower) {
        String insertSmt = "INSERT INTO public.borrower (firstname, lastname, relationship, ssn, address, city, state, zip, age)";
        String values = "VALUES( (?), (?), (?), (?), (?), (?), (?), (?), (?) );";

        long borrowerId;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(insertSmt + values);) {

            statement.setString(1, borrower.getFirstName());
            statement.setString(2, borrower.getLastName());
            statement.setString(3, borrower.getRelationship());
            statement.setInt(4, borrower.getSsn());
            statement.setString(5, borrower.getAddress());
            statement.setString(6, borrower.getCity());
            statement.setString(7, borrower.getState());
            statement.setInt(8, borrower.getZip());
            statement.setInt(9, borrower.getAge());

            statement.executeUpdate();
            
            //if borrower already exists, employments won't save
            borrowerId = fetchBorrowerIdBySSN(borrower.getSsn());
            
            
            for (Employment employment : borrower.getEmployment()) {
                employmentManager.saveEmployment(employment, borrowerId);
            }

        } catch (SQLException ex) {
            //if borrower already exists in db, return id;
            borrowerId = fetchBorrowerIdBySSN(borrower.getSsn());
        }

        return borrowerId;
    }

    public List<Borrower> assembleBorrowers(long applicationId){

        String appBorrowerQuery = "SELECT borrower_id FROM \"public\".\"application_borrower\" WHERE application_id=(?);";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement appBorrStatement = conn.prepareStatement(appBorrowerQuery);) {

            appBorrStatement.setObject(1, applicationId);

            try (ResultSet appBorrResultSet = appBorrStatement.executeQuery()) {
                return createBorrowerList(appBorrResultSet, "borrower_id");
            }

        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }

    public Borrower assembleBorrower(long borrowerId) {

        Borrower borrower = new Borrower();
        String borrowerQuery = "SELECT id,address,age,city,firstname,lastname,relationship,ssn,state,zip FROM \"public\".\"borrower\" WHERE id=(?);";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement borrowerStatement = conn.prepareStatement(borrowerQuery);) {

            borrowerStatement.setObject(1, borrowerId);
            try (ResultSet resultSet = borrowerStatement.executeQuery()) {
                while (resultSet.next()) {
                    borrower.setId(resultSet.getLong("id"));
                    borrower.setAddress(resultSet.getString("address"));
                    borrower.setAge(resultSet.getInt("age"));
                    borrower.setCity(resultSet.getString("city"));
                    borrower.setLastName(resultSet.getString("lastname"));
                    borrower.setFirstName(resultSet.getString("firstname"));
                    borrower.setRelationship(resultSet.getString("relationship"));
                    borrower.setSsn(resultSet.getInt("ssn"));
                    borrower.setState(resultSet.getString("state"));
                    borrower.setZip(resultSet.getInt("zip"));
                    borrower.setEmployments(employmentManager.assembleEmployments(resultSet.getLong("id")));
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }

        if (borrower.getId() == null) {
            return null;
        }
        return borrower;
    }

    public Borrower assembleBorrower(int ssn) {
        return assembleBorrower(fetchBorrowerIdBySSN(ssn));
    }

    private long fetchBorrowerIdBySSN(int ssn) {
        long returnId = -1;
        String query = "SELECT id FROM \"public\".\"borrower\" where ssn=(?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {

            statement.setObject(1, ssn);
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

    public List<Borrower> fetchAllBorrowers() {

        String query = "SELECT id FROM \"public\".\"borrower\"";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query);) {
            try (ResultSet resultSet = statement.executeQuery();) {
                return createBorrowerList(resultSet, "id");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }

    }

    public Borrower updateBorrowerLocation(long id, String address, String city, String state, int zip) {

        String updateQuery = "UPDATE public.borrower SET address = (?), city = (?), state = (?), zip = (?) "
                + "WHERE id = (?)";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement updateStatement = conn.prepareStatement(updateQuery);) {

            updateStatement.setObject(1, address);
            updateStatement.setObject(2, city);
            updateStatement.setObject(3, state);
            updateStatement.setObject(4, zip);
            updateStatement.setObject(5, id);

            updateStatement.executeUpdate();
            return assembleBorrower(id);
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
    }

    private List<Borrower> createBorrowerList(ResultSet borrowerRs, String idColumnName) {

        ArrayList<Borrower> borrowers = new ArrayList<>();
        try (borrowerRs) {
            while (borrowerRs.next()) {
                long borrowerId = borrowerRs.getLong(idColumnName);
                borrowers.add(assembleBorrower(borrowerId));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("SqlException", ex);
        }
        return borrowers;
    }
}
