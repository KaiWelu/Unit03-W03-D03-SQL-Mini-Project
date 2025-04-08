package org.dci;

import org.dci.data.DepartmentDAO;
import org.dci.data.EmployeeDAO;
import org.dci.data.HikariConnection;

import java.sql.Date;
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



        // delete some random departments
        //departmentDAO.deleteDepartment("Engineering");
        //departmentDAO.deleteDepartment(50);

        // add some departments
        departmentDAO.insertDepartment("Engineering", "Berlin");
        departmentDAO.insertDepartment("Human Resources", "Berlin");
        departmentDAO.insertDepartment("Accounting", "Hamburg");
        departmentDAO.insertDepartment("Construction", "Hamburg");
        departmentDAO.insertDepartment("Research", "London");

        // departmentDAO.updateDepartment(55, "Engineering & Physics", "Paris");

        //System.out.println(departmentDAO.getDepartment("Accounting").toString());
        //System.out.println(departmentDAO.getDepartment(31).toString());
    }

    private static LocalDate getRandomDate() {
        LocalDate now = LocalDate.now();
        LocalDate tenYearsAgo = now.minusYears(10);
        long days = ChronoUnit.DAYS.between(tenYearsAgo, now);
        long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
        return tenYearsAgo.plusDays(randomDays);
    }
}