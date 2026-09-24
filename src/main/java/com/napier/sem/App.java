package com.napier.sem;

import java.util.ArrayList;
import java.sql.*;

public class App {

private Connection con = null;

public void connect() {
    String url =
            "jdbc:mysql://db:3306/employees"
                    + "?allowPublicKeyRetrieval=true"
                    + "&useSSL=false"
                    + "&connectTimeout=5000";

    for (int attempt = 1; attempt <= 12; attempt++) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    url,
                    "root",
                    "example"
            );

            System.out.println("Successfully connected to database");
            return;

        } catch (Exception e) {
            System.out.println(
                    "Database connection attempt "
                            + attempt
                            + " failed: "
                            + e.getMessage()
            );

            if (attempt == 12) {
                throw new IllegalStateException(
                        "Could not connect to database", e);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(
                        "Interrupted while waiting for database",
                        interrupted);
            }
        }
    }
}

public void disconnect() {
    try {
        if (con != null) {
            con.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public Employee getEmployee(int ID) {
    Employee employee = null;

    try {
        Statement stmt = con.createStatement();

        String strSelect =
                "SELECT e.emp_no, e.first_name, e.last_name, " +
                        "t.title, s.salary, d.dept_name, " +
                        "CONCAT(m.first_name, ' ', m.last_name) AS manager " +
                        "FROM employees e " +
                        "JOIN titles t ON e.emp_no = t.emp_no " +
                        "JOIN salaries s ON e.emp_no = s.emp_no " +
                        "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                        "JOIN departments d ON de.dept_no = d.dept_no " +
                        "LEFT JOIN dept_manager dm ON de.dept_no = dm.dept_no " +
                        "LEFT JOIN employees m ON dm.emp_no = m.emp_no " +
                        "WHERE e.emp_no = " + ID + " " +
                        "AND t.to_date = '9999-01-01' " +
                        "AND s.to_date = '9999-01-01' " +
                        "AND de.to_date = '9999-01-01' " +
                        "AND dm.to_date = '9999-01-01'";

        ResultSet rset = stmt.executeQuery(strSelect);

        if (rset.next()) {
            employee = new Employee();

            employee.emp_no = rset.getInt("emp_no");
            employee.first_name = rset.getString("first_name");
            employee.last_name = rset.getString("last_name");
            employee.title = rset.getString("title");
            employee.salary = rset.getInt("salary");
            employee.dept_name = rset.getString("dept_name");
            employee.manager = rset.getString("manager");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return employee;
}

public void getSalariesByRole(String role) {
    try {
        Statement stmt = con.createStatement();

        String strSelect =
                "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                        "FROM employees, salaries, titles " +
                        "WHERE employees.emp_no = salaries.emp_no " +
                        "AND employees.emp_no = titles.emp_no " +
                        "AND salaries.to_date = '9999-01-01' " +
                        "AND titles.to_date = '9999-01-01' " +
                        "AND titles.title = '" + role + "' " +
                        "ORDER BY employees.emp_no ASC";

        ResultSet rset = stmt.executeQuery(strSelect);

        while (rset.next()) {
            System.out.println(
                    rset.getInt("emp_no") + " " +
                            rset.getString("first_name") + " " +
                            rset.getString("last_name") + " " +
                            rset.getInt("salary")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public ArrayList<Employee> getAllSalaries()
{
    try
    {
        Statement stmt = con.createStatement();
        String strSelect =
                "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                        + "FROM employees, salaries "
                        + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                        + "ORDER BY employees.emp_no ASC";
        ResultSet rset = stmt.executeQuery(strSelect);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        while (rset.next())
        {
            Employee emp = new Employee();
            emp.emp_no = rset.getInt("employees.emp_no");
            emp.first_name = rset.getString("employees.first_name");
            emp.last_name = rset.getString("employees.last_name");
            emp.salary = rset.getInt("salaries.salary");
            employees.add(emp);
        }
        return employees;
    }
    catch (Exception e)
    {
        System.out.println(e.getMessage());
        System.out.println("Failed to get salary details");
        return null;
    }
}

/**
 * Prints a list of employees.
 * @param employees The list of employees to print.
 */
public void printSalaries(ArrayList<Employee> employees)
{
    // Print header
    System.out.println(
            String.format(
                    "%-10s %-15s %-20s %-8s",
                    "Emp No",
                    "First Name",
                    "Last Name",
                    "Salary"
            )
    );

    // Loop over all employees in the list
    for (Employee emp : employees)
    {
        String emp_string =
                String.format(
                        "%-10s %-15s %-20s %-8s",
                        emp.emp_no,
                        emp.first_name,
                        emp.last_name,
                        emp.salary
                );

        System.out.println(emp_string);
    }
}

public void displayEmployee(Employee emp) {
    System.out.println(emp.emp_no + " "
            + emp.first_name + " "
            + emp.last_name);

    System.out.println("Title: " + emp.title);
    System.out.println("Salary:" + emp.salary);
    System.out.println("Department: " + emp.dept_name);
    System.out.println("Manager: " + emp.manager);
}

    public static void main(String[] args) {
        App a = new App();
        a.connect();

        ArrayList<Employee> employees = a.getAllSalaries();

        System.out.println(employees.size());

        a.printSalaries(employees);

        a.disconnect();

        System.out.println("Application finished successfully.");
    }
}