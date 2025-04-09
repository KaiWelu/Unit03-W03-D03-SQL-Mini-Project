package org.dci.data;

import org.dci.models.Employee;

import java.sql.*;

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

    public void deleteEmployee(int id) throws SQLException {
        String query = "DELETE FROM employees WHERE employee_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        int rows = statement.executeUpdate();
        System.out.println(rows + " employee deleted!");
    }

    //overload to delete an employee with first + lastname
    public void deleteEmployee(String firstName, String lastName) throws SQLException {
        String query = "DELETE FROM employees WHERE first_name = ? AND last_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);

        int rows = statement.executeUpdate();
        System.out.println(rows + " employee deleted!");
    }

    public Employee getEmployee(int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            return new Employee(resultSet.getInt("employee_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("phone"),
                                resultSet.getDate("hire_date"),
                                resultSet.getInt("salary"),
                                departmentDAO.getDepartment(resultSet.getInt("department_id")).getName());
        }
        // if the employee id doesn't exist return null
        return null;
    }

    //overload to make it possible to get employee by name
    public Employee getEmployee(String firstName, String lastName) throws SQLException {
        String query = "SELECT * FROM employees WHERE first_name = ? AND last_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);

        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            return new Employee(resultSet.getInt("employee_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("phone"),
                                resultSet.getDate("hire_date"),
                                resultSet.getInt("salary"),
                                departmentDAO.getDepartment(resultSet.getInt("department_id")).getName());
        }
        // if the employee id doesn't exist return null
        return null;
    }

    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employees SET first_name ?," +
                       "last_name = ?, email = ?, phone = ?, hire_date = ?, salary = ?, department_id = ? " +
                       "WHERE employee_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getPhone());
        statement.setDate(5, employee.getHireDate());
        statement.setInt(6, employee.getSalary());
        statement.setInt(7, departmentDAO.getDepartment(employee.getDepartment()).getId());
        statement.setInt(8, employee.getId());
    }
}
