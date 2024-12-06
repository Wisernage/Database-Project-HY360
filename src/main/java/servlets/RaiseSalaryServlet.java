package servlets;

import java.io.IOException;
import java.io.Serial;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "RaiseSalaryServlet", urlPatterns = {"/raise"})
public class RaiseSalaryServlet extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Retrieve the selected option and raise percentage from the form
        int option = Integer.parseInt(request.getParameter("option"));
        double raisePercentage = Double.parseDouble(request.getParameter("raisePercentage")) / 100;
        try {
            // Prepare the SQL statement based on the selected option
            String query = switch (option) {
                case 1 -> "UPDATE staff_category SET salary = salary + (salary * ?) WHERE staff_category_id = 1";
                case 2 -> "UPDATE staff_category SET salary = salary + (salary * ?) WHERE staff_category_id = 2";
                case 3 -> "UPDATE staff_category SET salary = salary + (salary * ?) WHERE staff_category_id = 3";
                case 4 -> "UPDATE staff_category SET salary = salary + (salary * ?) WHERE staff_category_id = 4";
                case 5 -> "UPDATE staff_category SET salary = salary + (salary * ?)";
                case 6 ->
                        "UPDATE staff_category SET research_allowance = research_allowance + (research_allowance * ?) WHERE staff_category_id = 3";
                case 7 ->
                        "UPDATE staff_category SET library_allowance = library_allowance + (library_allowance * ?) WHERE staff_category_id = 4";
                case 8 -> "UPDATE staff_category SET family_allowance = family_allowance + (? * 100)";
                default -> throw new ServletException("Invalid option selected");
            };
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, raisePercentage);
            stmt.executeUpdate();
            stmt.close();
            response.sendRedirect("raisesuccess.jsp");
        } catch (SQLException e) {
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
