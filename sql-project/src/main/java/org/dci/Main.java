package org.dci;

import org.dci.data.DepartmentDAO;
import org.dci.data.EmployeeDAO;
import org.dci.data.HikariConnection;

import java.sql.SQLException;

public class Main {



    public static void main(String[] args) throws SQLException {

        // set up hikari connection
        HikariConnection connection = new HikariConnection();

        // create employee dao and pass hikari connection
        EmployeeDAO employeeDAO = new EmployeeDAO(connection.getConnection());
        DepartmentDAO departmentDAO = new DepartmentDAO(connection.getConnection());

        // delete some random departments
        //departmentDAO.deleteDepartment("Engineering");
        //departmentDAO.deleteDepartment(50);

        // add some departments
        departmentDAO.insertDepartment("Engineering", "Berlin");
        departmentDAO.insertDepartment("Human Resources", "Berlin");
        departmentDAO.insertDepartment("Accounting", "Hamburg");
        departmentDAO.insertDepartment("Construction", "Hamburg");
        departmentDAO.insertDepartment("Research", "London");

        departmentDAO.updateDepartment(55, "Engineering & Physics", "Paris");

        //System.out.println(departmentDAO.getDepartment("Accounting").toString());
        //System.out.println(departmentDAO.getDepartment(31).toString());
    }
}