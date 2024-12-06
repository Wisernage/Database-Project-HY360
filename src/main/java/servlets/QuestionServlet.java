package servlets;

import java.io.IOException;
import java.io.Serial;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
//import java.time.LocalDate;
//import java.time.Period;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONException;
import org.json.JSONObject;


//  static java.lang.System.out;


@WebServlet(name = "QuestionServlet", urlPatterns = {"/question"})
public class QuestionServlet extends HttpServlet {
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
        if (request.getParameter("questionType").equals("payroll_list")) {
            try {
                PreparedStatement stmt;
                String query = "SELECT * FROM employee";
                stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                JSONArray payrollArray = new JSONArray();
                while (rs.next()) {
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
    /*              out.print(salary + "\n");
                    out.print(familyAllowance + "\n");
                    out.print(familyAllowanceMultiplier + "\n");
                    out.print(((double)(familyAllowance * familyAllowanceMultiplier) / 100 * salary) + "\n");
                    out.print((yearsOfService * 0.15 * salary) + "\n");
                    out.print((researchAllowance) + "\n");
                    out.print((libraryAllowance) + "\n");*/
                    JSONObject payroll = new JSONObject();
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
                }
                response.setContentType("application/json");
                response.getWriter().write(payrollArray.toString());
                stmt.close();
            } catch (SQLException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (request.getParameter("questionType").equals("payroll_stats")) {
            try {
                PreparedStatement stmt;
                String query = "SELECT * FROM employee";
                stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                JSONArray categoryArray = new JSONArray();
                int[] count = new int[4];
                double[] sum = new double[4];
                double[] min = new double[4];
                double[] max = new double[4];
                double[] avg = new double[4];
                while (rs.next()) {
                    // Get employee data
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
                    int index = staffCategoryId - 1;
                    if (count[index] == 0) {
                        min[index] = totalAmount;
                        max[index] = totalAmount;
                    } else {
                        if (totalAmount > max[index]) {
                            max[index] = totalAmount;
                        }
                        if (totalAmount < min[index]) {
                            min[index] = totalAmount;
                        }
                    }
                    sum[index] = sum[index] + totalAmount;
                    count[index]++;
                }
//                for (int i=0; i<4; i++) {
//                    if (count[i] > 0){
//                        avg[i] = sum[i] / count[i];
//                    }
//                    out.print(count[i] + "\n");
//                    out.print(sum[i] + "\n");
//                    out.print(min[i] + "\n");
//                    out.print(max[i] + "\n");
//                    out.print(avg[i] + "\n");
//                }
                for (int i = 0; i < 4; i++) {
                    JSONObject category = new JSONObject();
                    if (count[i] > 0) {
                        avg[i] = sum[i] / count[i];
                    }
                    category.put("min", min[i]);
                    category.put("max", max[i]);
                    category.put("avg", avg[i]);
                    categoryArray.put(category);
                }
                response.setContentType("application/json");
                response.getWriter().write(categoryArray.toString());
                stmt.close();
            } catch (SQLException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (request.getParameter("questionType").equals("total_pay")) {
            try {
                PreparedStatement stmt;
                String query = "SELECT * FROM employee";
                stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                JSONArray categoryArray = new JSONArray();
                double[] sum = new double[4];
                while (rs.next()) {
                    // Get employee data
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
                    int index = staffCategoryId - 1;
                    sum[index] = sum[index] + totalAmount;
                }
//                for (int i=0; i<4; i++) {
//                    if (count[i] > 0){
//                        avg[i] = sum[i] / count[i];
//                    }
//                    out.print(count[i] + "\n");
//                    out.print(sum[i] + "\n");
//                    out.print(min[i] + "\n");
//                    out.print(max[i] + "\n");
//                    out.print(avg[i] + "\n");
//                }
                for (int i = 0; i < 4; i++) {
                    JSONObject category = new JSONObject();
                    category.put("sum", sum[i]);
                    categoryArray.put(category);
                }
                response.setContentType("application/json");
                response.getWriter().write(categoryArray.toString());
                stmt.close();
            } catch (SQLException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (request.getParameter("questionType").equals("employee_data")) {
            try {
                String username = request.getParameter("username");
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
                    int children = rs.getInt("children");
                    String ages = rs.getString("ages");
                    int staffCategoryId = rs.getInt("staff_category_id");
                    int departmentId = rs.getInt("department_id");
                    Date startDate = rs.getDate("start_date");
                    String address = rs.getString("address");
                    String telephone = rs.getString("telephone");
                    String bankAccount = rs.getString("bank_account");
                    String bankName = rs.getString("bank_name");
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
                    payroll.put("username", username);
                    payroll.put("fname", fname);
                    payroll.put("lastname", lastname);
                    payroll.put("marital_status", maritalStatus);
                    payroll.put("children", children);
                    payroll.put("ages", ages);
                    payroll.put("staffCategoryId", staffCategoryId);
                    payroll.put("departmentId", departmentId);
                    payroll.put("start_date", startDate);
                    payroll.put("address", address);
                    payroll.put("telephone", telephone);
                    payroll.put("bank_account", bankAccount);
                    payroll.put("bank_name", bankName);
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
                    payroll.put("basicSalary", salary);
                    payroll.put("totalAmount", totalAmount);
                    payrollArray.put(payroll);
                    response.setContentType("application/json");
                    response.getWriter().write(payrollArray.toString());
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
        } else if (request.getParameter("questionType").equals("all_employees")) {
            try {
                String query = "SELECT * FROM employee";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                JSONArray payrollArray = new JSONArray();
                while (rs.next()) {
                    // Get employee data
                    String username = rs.getString("username");
                    String fname = rs.getString("fname");
                    String lastname = rs.getString("lastname");
                    String maritalStatus = rs.getString("marital_status");
                    int children = rs.getInt("children");
                    String ages = rs.getString("ages");
                    int staffCategoryId = rs.getInt("staff_category_id");
                    int departmentId = rs.getInt("department_id");
                    Date startDate = rs.getDate("start_date");
                    String address = rs.getString("address");
                    String telephone = rs.getString("telephone");
                    String bankAccount = rs.getString("bank_account");
                    String bankName = rs.getString("bank_name");
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
                    payroll.put("username", username);
                    payroll.put("fname", fname);
                    payroll.put("lastname", lastname);
                    payroll.put("marital_status", maritalStatus);
                    payroll.put("children", children);
                    payroll.put("ages", ages);
                    payroll.put("staffCategoryId", staffCategoryId);
                    payroll.put("departmentId", departmentId);
                    payroll.put("start_date", startDate);
                    payroll.put("address", address);
                    payroll.put("telephone", telephone);
                    payroll.put("bank_account", bankAccount);
                    payroll.put("bank_name", bankName);
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
                    payroll.put("basicSalary", salary);
                    payroll.put("totalAmount", totalAmount);
                    payrollArray.put(payroll);
                }
                response.setContentType("application/json");
                response.getWriter().write(payrollArray.toString());
                stmt.close();
            } catch (SQLException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if (request.getParameter("questionType").equals("payment_history")){
            JSONArray payrollPayments = new JSONArray();
            try {
                String query = "SELECT * FROM payroll_payment";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                // For each payroll_payment, create a JSON object and add it to the JSONArray
                while (rs.next()) {
                    JSONObject payrollPayment = new JSONObject();
                    payrollPayment.put("payment_id", rs.getInt("payment_id"));
                    payrollPayment.put("fname", rs.getString("fname"));
                    payrollPayment.put("lastname", rs.getString("lastname"));
                    payrollPayment.put("payment_date", rs.getDate("payment_date"));
                    payrollPayment.put("total_amount", rs.getBigDecimal("total_amount"));
                    payrollPayments.put(payrollPayment);
                }

                // Set the response content type to JSON and write the JSONArray to the response
                response.setContentType("application/json");
                response.getWriter().write(payrollPayments.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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