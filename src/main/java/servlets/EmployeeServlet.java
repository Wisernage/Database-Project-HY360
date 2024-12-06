package servlets;

import java.io.IOException;
import java.io.Serial;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.time.LocalDate;



@WebServlet(name = "EmployeeServlet", urlPatterns = {"/hire"})
public class EmployeeServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 102831973239L;
    private Connection conn;
    private static final String DB_URL = "jdbc:mysql://localhost:6000/hy360";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("uncaught", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String fname = request.getParameter("fname");
        String lastname = request.getParameter("lastname");
        String maritalStatus = request.getParameter("marital_status");
        int children = Integer.parseInt(request.getParameter("children"));
        String ages = request.getParameter("ages");
        int staffCategoryId = Integer.parseInt(request.getParameter("staff_category"));
        int departmentId = Integer.parseInt(request.getParameter("department_id"));
        LocalDate localDate = LocalDate.now();
        java.sql.Date startDate = java.sql.Date.valueOf(localDate);
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String bankAccount = request.getParameter("bank_account");
        String bankName = request.getParameter("bank_name");
        String form = request.getParameter("form");
        try {
            String query = "SELECT * FROM employee WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = 1;
            }
            switch (form) {
                case "hire" -> {
                    if (count == 0) {
                        query = "INSERT INTO employee (username, fname, lastname, marital_status, children, ages, staff_category_id, department_id, start_date, end_date, address, telephone, bank_account, bank_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, username);
                        stmt.setString(2, fname);
                        stmt.setString(3, lastname);
                        stmt.setString(4, maritalStatus);
                        stmt.setInt(5, children);
                        stmt.setString(6, ages);
                        stmt.setInt(7, staffCategoryId);
                        stmt.setInt(8, departmentId);
                        stmt.setDate(9, startDate);
                        stmt.setNull(10, Types.DATE);
                        stmt.setString(11, address);
                        stmt.setString(12, telephone);
                        stmt.setString(13, bankAccount);
                        stmt.setString(14, bankName);
                        stmt.executeUpdate();
                        response.sendRedirect("createnewusersuccess.jsp");
                    } else {
                        response.sendRedirect("alreadyexists.jsp");
                    }
                }
                case "contract" -> {
                    if (count == 0) {
                        query = "INSERT INTO employee (username, fname, lastname, marital_status, children, ages, staff_category_id, department_id, start_date, end_date, address, telephone, bank_account, bank_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, username);
                        stmt.setString(2, fname);
                        stmt.setString(3, lastname);
                        stmt.setString(4, maritalStatus);
                        stmt.setInt(5, children);
                        stmt.setString(6, ages);
                        stmt.setInt(7, staffCategoryId);
                        stmt.setInt(8, departmentId);
                        stmt.setDate(9, startDate);
                        stmt.setNull(10, Types.DATE);
                        stmt.setString(11, address);
                        stmt.setString(12, telephone);
                        stmt.setString(13, bankAccount);
                        stmt.setString(14, bankName);
                        stmt.executeUpdate();
                        response.sendRedirect("createnewusersuccess.jsp");
                    } else {
                        query = "SELECT staff_category_id FROM employee WHERE username = ? AND (staff_category_id = 3 OR staff_category_id = 4)";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, username);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            query = "UPDATE employee SET fname = ?, lastname = ?, marital_status = ?, children = ?, ages = ?, staff_category_id = ?, department_id = ?, address = ?, telephone = ?, bank_account = ?, bank_name = ? WHERE username = ?";
                            stmt = conn.prepareStatement(query);
                            stmt.setString(1, fname);
                            stmt.setString(2, lastname);
                            stmt.setString(3, maritalStatus);
                            stmt.setInt(4, children);
                            stmt.setString(5, ages);
                            stmt.setInt(6, staffCategoryId);
                            stmt.setInt(7, departmentId);
                            stmt.setString(8, address);
                            stmt.setString(9, telephone);
                            stmt.setString(10, bankAccount);
                            stmt.setString(11, bankName);
                            stmt.setString(12, username);
                            stmt.executeUpdate();
                            response.sendRedirect("renewcontractsuccess.jsp");
                        } else {
                            response.sendRedirect("contperm.jsp");
                        }
                    }
                }
                case "update" -> {
                    if (count == 1) {
                        query = "UPDATE employee SET fname = ?, lastname = ?, marital_status = ?, children = ?, ages = ?, staff_category_id = ?, department_id = ?, address = ?, telephone = ?, bank_account = ?, bank_name = ? WHERE username = ?";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, fname);
                        stmt.setString(2, lastname);
                        stmt.setString(3, maritalStatus);
                        stmt.setInt(4, children);
                        stmt.setString(5, ages);
                        stmt.setInt(6, staffCategoryId);
                        stmt.setInt(7, departmentId);
                        stmt.setString(8, address);
                        stmt.setString(9, telephone);
                        stmt.setString(10, bankAccount);
                        stmt.setString(11, bankName);
                        stmt.setString(12, username);
                        stmt.executeUpdate();
                        response.sendRedirect("updatesuccess.jsp");
                    } else {
                        response.sendRedirect("updatefailed.jsp");
                    }
                }
            }
            stmt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendRedirect("failure.jsp");
        }
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
