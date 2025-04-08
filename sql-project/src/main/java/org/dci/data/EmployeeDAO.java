package org.dci.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDAO {

    private final Connection connection;
    DepartmentDAO departmentDAO;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
        this.departmentDAO = new DepartmentDAO(connection);
    }

    public void insertEmployee(String firstName, String lastName, String email, String phone, Date hireDate,
                               int salary, String department) throws SQLException {
        String query = "INSERT INTO employees (first_name, last_name, email, phone, hire_date, salary, department_id)" +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)" +
                       "ON CONFLICT (email) DO NOTHING;";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setString(4, phone);
        statement.setDate(5, hireDate);
        statement.setInt(6, salary);
        statement.setInt(7, departmentDAO.getDepartment(department).getId());

        int rows = statement.executeUpdate();
        System.out.println("Rows changed in employees: " + rows);

    }
}
