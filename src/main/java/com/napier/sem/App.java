package com.napier.sem;

import java.util.ArrayList;
import java.sql.*;

public class App {

    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
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

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an employee by their ID.
     * (Exercise 2: Adds the department manager as an Employee object)
     *
     * @param ID The employee ID.
     * @return The Employee object containing their details, or null if not found.
     */
    public Employee getEmployee(int ID) {
        Employee employee = null;

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, " +
                            "t.title, s.salary, d.dept_name, " +
                            "m.emp_no AS m_emp_no, m.first_name AS m_first_name, m.last_name AS m_last_name " +
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

                // Assign department
                employee.dept = new Department();
                employee.dept.dept_name = rset.getString("dept_name");

                // Exercise 2: Add department manager as an Employee
                if (rset.getString("m_first_name") != null) {
                    Employee mgr = new Employee();
                    mgr.emp_no = rset.getInt("m_emp_no");
                    mgr.first_name = rset.getString("m_first_name");
                    mgr.last_name = rset.getString("m_last_name");
                    employee.manager = mgr;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }

    /**
     * Exercise 3: Retrieves an employee based on their first and last name.
     *
     * @param first_name The first name of the employee.
     * @param last_name The last name of the employee.
     * @return The Employee object or null if not found.
     */
    public Employee getEmployee(String first_name, String last_name) {
        Employee employee = null;

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, " +
                            "t.title, s.salary, d.dept_name, " +
                            "m.emp_no AS m_emp_no, m.first_name AS m_first_name, m.last_name AS m_last_name " +
                            "FROM employees e " +
                            "JOIN titles t ON e.emp_no = t.emp_no " +
                            "JOIN salaries s ON e.emp_no = s.emp_no " +
                            "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                            "JOIN departments d ON de.dept_no = d.dept_no " +
                            "LEFT JOIN dept_manager dm ON de.dept_no = dm.dept_no " +
                            "LEFT JOIN employees m ON dm.emp_no = m.emp_no " +
                            "WHERE e.first_name = '" + first_name + "' " +
                            "AND e.last_name = '" + last_name + "' " +
                            "AND t.to_date = '9999-01-01' " +
                            "AND s.to_date = '9999-01-01' " +
                            "AND de.to_date = '9999-01-01' " +
                            "AND dm.to_date = '9999-01-01' " +
                            "LIMIT 1";

            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                employee = new Employee();

                employee.emp_no = rset.getInt("emp_no");
                employee.first_name = rset.getString("first_name");
                employee.last_name = rset.getString("last_name");
                employee.title = rset.getString("title");
                employee.salary = rset.getInt("salary");

                // Assign department
                employee.dept = new Department();
                employee.dept.dept_name = rset.getString("dept_name");

                // Assign department manager
                if (rset.getString("m_first_name") != null) {
                    Employee mgr = new Employee();
                    mgr.emp_no = rset.getInt("m_emp_no");
                    mgr.first_name = rset.getString("m_first_name");
                    mgr.last_name = rset.getString("m_last_name");
                    employee.manager = mgr;
                }
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

    public ArrayList<Employee> getAllSalaries() {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    public void printSalaries(ArrayList<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println(
                String.format(
                        "%-10s %-15s %-20s %-8s",
                        "Emp No",
                        "First Name",
                        "Last Name",
                        "Salary"
                )
        );

        for (Employee emp : employees) {
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
        if (emp != null) {
            System.out.println("Emp No: " + emp.emp_no);
            System.out.println("Name: " + emp.first_name + " " + emp.last_name);
            System.out.println("Title: " + emp.title);
            System.out.println("Salary: " + emp.salary);
            System.out.println("Department: " + (emp.dept != null ? emp.dept.dept_name : "N/A"));
            System.out.println("Manager: " + (emp.manager != null ? emp.manager.first_name + " " + emp.manager.last_name : "N/A"));
        } else {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Exercise 1: Gets a department by its name, including its department manager.
     *
     * @param dept_name The name of the department.
     * @return The Department object or null if not found.
     */
    public Department getDepartment(String dept_name) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT d.dept_no, d.dept_name, " +
                            "e.emp_no, e.first_name, e.last_name " +
                            "FROM departments d " +
                            "LEFT JOIN dept_manager dm ON d.dept_no = dm.dept_no AND dm.to_date = '9999-01-01' " +
                            "LEFT JOIN employees e ON dm.emp_no = e.emp_no " +
                            "WHERE d.dept_name = '" + dept_name + "'";

            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                Department dept = new Department();
                dept.dept_no = rset.getString("dept_no");
                dept.dept_name = rset.getString("dept_name");

                // Exercise 1: Attach Manager to Department
                if (rset.getString("first_name") != null) {
                    Employee manager = new Employee();
                    manager.emp_no = rset.getInt("emp_no");
                    manager.first_name = rset.getString("first_name");
                    manager.last_name = rset.getString("last_name");
                    dept.manager = manager;
                }

                return dept;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department details");
            return null;
        }
    }

    public ArrayList<Employee> getSalariesByDepartment(Department dept) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                            "FROM employees, salaries, dept_emp, departments " +
                            "WHERE employees.emp_no = salaries.emp_no " +
                            "AND employees.emp_no = dept_emp.emp_no " +
                            "AND dept_emp.dept_no = departments.dept_no " +
                            "AND salaries.to_date = '9999-01-01' " +
                            "AND departments.dept_no = '" + dept.dept_no + "' " +
                            "ORDER BY employees.emp_no ASC";

            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salaries by department");
            return null;
        }
    }

    public static void main(String[] args) {
        App a = new App();
        a.connect();

        // 1. Test getDepartment with Manager (Exercise 1)
        Department dept = a.getDepartment("Sales");
        if (dept != null) {
            System.out.println("Department: " + dept.dept_name + " (" + dept.dept_no + ")");
            if (dept.manager != null) {
                System.out.println("Department Manager: " + dept.manager.first_name + " " + dept.manager.last_name);
            }
        }

        // 2. Test getEmployee with ID and Manager (Exercise 2)
        System.out.println("\nTesting getEmployee by ID:");
        Employee empById = a.getEmployee(255530);
        a.displayEmployee(empById);

        // 3. Test getEmployee by Name (Exercise 3)
        System.out.println("\nTesting getEmployee by Name:");
        Employee empByName = a.getEmployee("Ronghao", "Apsitis");
        a.displayEmployee(empByName);

        a.disconnect();
        System.out.println("\nApplication finished successfully.");
    }
}