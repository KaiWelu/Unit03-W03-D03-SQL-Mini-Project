package org.dci.data;

import org.dci.models.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {
    private final Connection connection;

    public DepartmentDAO(Connection connection) {
        this.connection = connection;
    }


    public void insertDepartment(String name, String location) throws SQLException {
        String insertDepartmentQuery =  "INSERT INTO departments (name, location) " +
                                        "VALUES (?, ?) " +
                                        "ON CONFLICT (name) DO NOTHING;";

        PreparedStatement insertDepartmentStatement = connection.prepareStatement(insertDepartmentQuery);

        insertDepartmentStatement.setString(1, name);
        insertDepartmentStatement.setString(2, location);

        int rows = insertDepartmentStatement.executeUpdate();
        System.out.println("Department added! Rows affected: " + rows);
    }

    public Department getDepartment(String name) throws SQLException {
        Department output;

        String query = "SELECT * FROM departments WHERE name = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            output = new Department(resultSet.getInt("department_id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("location"));
            return output;
        } else {
            System.out.println(name + " not found!");
            return null;
        }

    }

    // overloading with id
    public Department getDepartment(Integer id) throws SQLException {
        Department output;

        String query = "SELECT * FROM departments WHERE department_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            output = new Department(resultSet.getInt("department_id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("location"));
            return output;
        } else {
            System.out.println("ID: " + id + " not found!");
            return null;
        }

    }

    public void deleteDepartment(String name) throws SQLException {
        String deleteDepartmentQuery = "DELETE FROM departments WHERE name = ?;";

        PreparedStatement deleteDepartmentStatement = connection.prepareStatement(deleteDepartmentQuery);
        deleteDepartmentStatement.setString(1, name);

        int rows = deleteDepartmentStatement.executeUpdate();
        System.out.println("Department " + name + " deleted!");

    }

    // overloading with id
    public void deleteDepartment(int id) throws SQLException {
        Department department = getDepartment(id);

        String deleteDepartmentQuery = "DELETE FROM departments WHERE department_id = ?;";

        PreparedStatement deleteDepartmentStatement = connection.prepareStatement(deleteDepartmentQuery);
        deleteDepartmentStatement.setInt(1, id);

        int rows = deleteDepartmentStatement.executeUpdate();
        System.out.println("Department " + department.getName() + " deleted!");

    }

    public void updateDepartment(int id, String name, String location) throws SQLException {
        String query = "UPDATE departments SET name = ?, location = ? WHERE department_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, location);
        statement.setInt(3, id);

        int rows = statement.executeUpdate();
        System.out.println("Department changed!");
    }


}
