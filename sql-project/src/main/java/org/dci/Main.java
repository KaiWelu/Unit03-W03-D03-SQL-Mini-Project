package org.dci;

import org.dci.data.DepartmentDAO;
import org.dci.data.EmployeeDAO;
import org.dci.data.HikariConnection;
import org.dci.models.Employee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class Main {



    public static void main(String[] args) throws SQLException {

        // set up hikari connection
        HikariConnection connection = new HikariConnection();


        // create employee dao and pass hikari connection
        EmployeeDAO employeeDAO = new EmployeeDAO(connection.getConnection());
        DepartmentDAO departmentDAO = new DepartmentDAO(connection.getConnection());
        LocalDate currentDate = LocalDate.now();


        //employeeDAO.insertEmployee("Kai", "Weluda", "kaiw@gmail.com", "045412222",
        //                          Date.valueOf(currentDate), 40000, "Research");


        // add some departments
        departmentDAO.insertDepartment("Engineering", "Berlin");
        departmentDAO.insertDepartment("Human Resources", "Berlin");
        departmentDAO.insertDepartment("Accounting", "Hamburg");
        departmentDAO.insertDepartment("Construction", "Hamburg");
        departmentDAO.insertDepartment("Research", "London");

        System.out.println(employeeDAO.getEmployee(6).toString());
        Employee testEmployee = employeeDAO.getEmployee("Kai", "Weluda");
        System.out.println(testEmployee.toString());

        testEmployee.setEmail("yolo@yolo.com");
        testEmployee.setSalary(70000);
        employeeDAO.updateEmployee(testEmployee);
        System.out.println(testEmployee);


    }

    private static LocalDate getRandomDate() {
        LocalDate now = LocalDate.now();
        LocalDate tenYearsAgo = now.minusYears(10);
        long days = ChronoUnit.DAYS.between(tenYearsAgo, now);
        long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
        return tenYearsAgo.plusDays(randomDays);
    }
}