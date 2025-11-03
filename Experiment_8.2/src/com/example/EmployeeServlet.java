package com.example;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EmployeeServlet extends HttpServlet {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password"; // change if needed

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empIdParam = request.getParameter("empid");

        out.println("<html><body style='font-family: Arial; text-align: center;'>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            if (empIdParam != null && !empIdParam.isEmpty()) {
                // Fetch specific employee
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee WHERE EmpID = ?");
                ps.setInt(1, Integer.parseInt(empIdParam));
                ResultSet rs = ps.executeQuery();

                out.println("<h2>Search Result</h2>");
                if (rs.next()) {
                    out.println("<table border='1' align='center' cellpadding='8'>");
                    out.println("<tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");
                    out.println("<tr><td>" + rs.getInt("EmpID") + "</td><td>" + rs.getString("Name") + "</td><td>" + rs.getDouble("Salary") + "</td></tr>");
                    out.println("</table>");
                } else {
                    out.println("<p style='color:red;'>No employee found with ID: " + empIdParam + "</p>");
                }
                rs.close();
                ps.close();
            } else {
                // Fetch all employees
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

                out.println("<h2>All Employee Records</h2>");
                out.println("<table border='1' align='center' cellpadding='8'>");
                out.println("<tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");

                while (rs.next()) {
                    out.println("<tr><td>" + rs.getInt("EmpID") + "</td><td>" + rs.getString("Name") + "</td><td>" + rs.getDouble("Salary") + "</td></tr>");
                }
                out.println("</table>");
                rs.close();
                stmt.close();
            }

            con.close();

        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='index.html'>Back to Home</a>");
        out.println("</body></html>");
    }
}
