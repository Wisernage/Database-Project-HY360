package servlets;

import java.io.IOException;
import java.io.Serial;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//  static java.lang.System.out;


@WebServlet(name = "FireServlet", urlPatterns = {"/fire"})
public class FireServlet extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {


        // Get all employees
        try {
            String username = request.getParameter("username");
//          out.println(username);
            String query = "SELECT * FROM employee WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            JSONArray payrollArray = new JSONArray();
            if (rs.next()) {
                // Get employee data
                String fname = rs.getString("fname");
                String lastname = rs.getString("lastname");
                String maritalStatus = rs.getString("marital_status");
                String ages = rs.getString("ages");
                int staffCategoryId = rs.getInt("staff_category_id");
                Date startDate = rs.getDate("start_date");

                // Get staff category data
                query = "SELECT * FROM staff_category WHERE staff_category_id = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, staffCategoryId);
                ResultSet staffCategoryResult = stmt.executeQuery();
                staffCategoryResult.next();
                double salary = staffCategoryResult.getDouble("salary");
                double researchAllowance = staffCategoryResult.getDouble("research_allowance");
                double libraryAllowance = staffCategoryResult.getDouble("library_allowance");
                int familyAllowance = staffCategoryResult.getInt("family_allowance");

                // Calculate family allowance multiplier
                int familyAllowanceMultiplier = 0;
                if (!ages.equals("") || maritalStatus.equals("married")) {
                    String[] agesArray = ages.split(",");
                    if (maritalStatus.equals("married")) {
                        familyAllowanceMultiplier = familyAllowanceMultiplier + 1;
                    }
                    for (String s : agesArray) {
                        if (Integer.parseInt(s) < 18) {
                            familyAllowanceMultiplier = familyAllowanceMultiplier + 1;
                        }
                    }
                }

                // Calculate years of service
                LocalDate start = startDate.toLocalDate();
                LocalDate now = LocalDate.now();
                int yearsOfService;
                if (staffCategoryId < 3) {
                    yearsOfService = Period.between(start, now).getYears();
                } else {
                    yearsOfService = 0;
                }
                // Calculate total amount
                double totalAmount = salary + ((double) (familyAllowance * familyAllowanceMultiplier) / 100 * salary) + (yearsOfService * 0.15 * salary) + researchAllowance + libraryAllowance;
                /*out.print(salary + "\n");
                out.print(familyAllowance + "\n");
                out.print(familyAllowanceMultiplier + "\n");
                out.print(((double)(familyAllowance * familyAllowanceMultiplier) / 100 * salary) + "\n");
                out.print((yearsOfService * 0.15 * salary) + "\n");
                out.print((researchAllowance) + "\n");
                out.print((libraryAllowance) + "\n");*/
                // Create new payroll payment
                query = "INSERT INTO payroll_payment (fname, lastname, payment_date, total_amount) VALUES (?, ?, NOW(), ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, fname);
                stmt.setString(2, lastname);
                stmt.setDouble(3, totalAmount);
                stmt.executeUpdate();
                JSONObject payroll = new JSONObject();
                payroll.put("error", 0);
                payroll.put("fname", fname);
                payroll.put("lastname", lastname);
                payroll.put("paymentDate", LocalDate.now().toString());
                payroll.put("basicSalary", salary);
                String appliedAllowances = "";
                if (familyAllowance * familyAllowanceMultiplier != 0) {
                    appliedAllowances += (familyAllowance * familyAllowanceMultiplier) + "% family allowance";
                }
                if (yearsOfService != 0) {
                    appliedAllowances += ", " + yearsOfService + " years of service allowance";
                }
                if (researchAllowance != 0) {
                    appliedAllowances += ", " + researchAllowance + " research allowance";
                }
                if (libraryAllowance != 0) {
                    appliedAllowances += ", " + libraryAllowance + " library allowance";
                }
                payroll.put("appliedAllowances", appliedAllowances);
                payroll.put("totalAmount", totalAmount);
                payroll.put("staffCategoryId", staffCategoryId);
                payrollArray.put(payroll);
                response.setContentType("application/json");
                response.getWriter().write(payrollArray.toString());
                query = "DELETE FROM employee WHERE username = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.executeUpdate();
            } else {
                JSONObject payroll = new JSONObject();
                payroll.put("error", 1);
                payrollArray.put(payroll);
                response.setContentType("application/json");
                response.getWriter().write(payrollArray.toString());
            }
            stmt.close();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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