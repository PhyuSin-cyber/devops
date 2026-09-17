package com.napier.sem;

import java.sql.*;

public class App {

    private Connection con = null;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false",
                    "root",
                    "example"
            );

            System.out.println("Connecting to database...");
            System.out.println("Successfully connected");

        } catch (Exception e) {
            e.printStackTrace();
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

        Employee emp = a.getEmployee(255530);
        a.displayEmployee(emp);

        a.disconnect();
    }
}