<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Administration Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.6.3.js" integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM=" crossorigin="anonymous"></script>
    <style>
      :root {}

      body {
        background-color: var(--bs-cyan);
      }
    </style>
  </head>
  <body>
    <div class="container" id="main_container">
      <h1>Employee Management</h1>
      <button class="btn btn-primary" onclick="showForm('hireNewEmployeeForm')">Hire New Employee</button>
      <button class="btn btn-primary" onclick="showForm('newContractForm')">New Contract</button>
      <button class="btn btn-primary" onclick="showForm('updateEmployeeForm')">Update Employee</button>
      <button class="btn btn-primary" onclick="showForm('updateSalaryForm')">Update Salary</button>
      <button class="btn btn-danger" onclick="showForm('fireEmployeeForm')">Fire/Retire Employee</button>
      <button class="btn btn-primary" id="payButton" onclick="submitPayment()">Submit Payment</button>
      <button class="btn btn-primary" id="questionsButton" onclick="questions()"> Questions</button>
      <div id="questionsContainer"></div>
      <div id="tableContainer"></div>
      <form class="form-horizontal" id="hireNewEmployeeForm" style="display:none;" action="http://localhost:8080/HY360/hire" method="post">
        <fieldset>
          <legend>Hire New Permanent Employee</legend>
          <input type="hidden" name="form" value="hire">
          <div class="form-group">
            <label for="username1">Username:</label>
            <input type="text" class="form-control" id="username1" name="username" aria-describedby="usernameHelp1" required>
            <small id="usernameHelp1" class="form-text text-muted">Enter the username of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="fname1">First Name:</label>
            <input type="text" class="form-control" id="fname1" name="fname" aria-describedby="fnameHelp1" required>
            <small id="fnameHelp1" class="form-text text-muted">Enter the first name of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="lastname1">Last Name:</label>
            <input type="text" class="form-control" id="lastname1" name="lastname" aria-describedby="lastnameHelp1" required>
            <small id="lastnameHelp1" class="form-text text-muted">Enter the last name of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="marital_status1">Marital Status:</label>
            <select class="form-control" id="marital_status1" name="marital_status" aria-describedby="marital_status_Help1">
              <option value="single">Single</option>
              <option value="married">Married</option>
            </select>
            <small id="marital_status_Help1" class="form-text text-muted">Select the marital status of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="children1">Number of Children:</label>
            <input type="number" class="form-control" id="children1" name="children" aria-describedby="children_Help1">
            <small id="children_Help1" class="form-text text-muted">Enter the number of children of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="ages1">Ages of Children:</label>
            <input type="text" class="form-control" id="ages1" name="ages" aria-describedby="ages_Help1">
            <small id="ages_Help1" class="form-text text-muted">Enter the ages of children of the new employee to (comma separated, without spaces).</small>
          </div>
          <div class="form-group">
            <label for="staff_category1">Staff Category:</label>
            <select class="form-control" id="staff_category1" name="staff_category" required aria-describedby="staff_category_Help1">
              <option value="">Choose category</option>
              <option value="1">Administrative</option>
              <option value="2">Teaching</option>
            </select>
            <small id="staff_category_Help1" class="form-text text-muted">Select the staff category of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="department_id1">Department ID:</label>
            <input type="number" class="form-control" id="department_id1" name="department_id" required aria-describedby="department_id_Help1">
            <small id="department_id_Help1" class="form-text text-muted">Enter the department ID of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="address1">Address:</label>
            <input type="text" class="form-control" id="address1" name="address" required aria-describedby="address_Help1">
            <small id="address_Help1" class="form-text text-muted">Enter the address of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="telephone1">Telephone:</label>
            <input type="text" class="form-control" id="telephone1" name="telephone" required aria-describedby="telephone_Help1">
            <small id="telephone_Help1" class="form-text text-muted">Enter the telephone number of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="bank_account1">Bank Account:</label>
            <input type="text" class="form-control" id="bank_account1" name="bank_account" required aria-describedby="bank_account1">
            <small id="bank_account_Help1" class="form-text text-muted">Enter the bank account number of the new employee to hire.</small>
          </div>
          <div class="form-group">
            <label for="bank_name1">Bank Name:</label>
            <input type="text" class="form-control" id="bank_name1" name="bank_name" required aria-describedBy="bank_name_Help1">
            <small id="bank_name_Help1" class="form-text text-muted">Enter the bank name of the new employee to hire.</small>
          </div>
          <button class="btn btn-secondary" type="button" onclick="hideForm('hireNewEmployeeForm')">Cancel</button>
          <button class="btn btn-primary">Submit</button>
        </fieldset>
      </form>
      <form class="form-horizontal" id="newContractForm" style="display:none;" action="http://localhost:8080/HY360/hire" method="post">
        <fieldset>
          <legend>New Contract</legend>
          <input type="hidden" name="form" value="contract">
          <div class="form-group">
            <label for="username2">Username:</label>
            <input type="text" class="form-control" id="username2" name="username" aria-describedby="usernameHelp2" required>
            <small id="usernameHelp2" class="form-text text-muted">Enter an existing username to renew a contract, or a new username to forge a new contract with a new employee.</small>
          </div>
          <div class="form-group">
            <label for="fname2">First Name:</label>
            <input type="text" class="form-control" id="fname2" name="fname" aria-describedby="fnameHelp2" required>
            <small id="fnameHelp2" class="form-text text-muted">Enter the first name of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="lastname2">Last Name:</label>
            <input type="text" class="form-control" id="lastname2" name="lastname" aria-describedby="lastnameHelp2" required>
            <small id="lastnameHelp2" class="form-text text-muted">Enter the last name of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="marital_status2">Marital Status:</label>
            <select class="form-control" id="marital_status2" name="marital_status" aria-describedby="marital_status_Help2">
              <option value="single">Single</option>
              <option value="married">Married</option>
            </select>
            <small id="marital_status_Help2" class="form-text text-muted">Select the marital status of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="children2">Number of Children:</label>
            <input type="number" class="form-control" id="children2" name="children" aria-describedby="children_Help2">
            <small id="children_Help2" class="form-text text-muted">Enter the number of children of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="ages2">Ages of Children:</label>
            <input type="text" class="form-control" id="ages2" name="ages" aria-describedby="ages_Help2">
            <small id="ages_Help2" class="form-text text-muted">Enter the ages of children of the employee you want to renew or make a new contract with(comma separated, without spaces).</small>
          </div>
          <div class="form-group">
            <label for="staff_category2">Staff Category:</label>
            <select class="form-control" id="staff_category2" name="staff_category" required aria-describedby="staff_category_Help2">
              <option value="">Choose category</option>
              <option value="3">Administrative</option>
              <option value="4">Teaching</option>
            </select>
            <small id="staff_category_Help2" class="form-text text-muted">Select the staff category of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="department_id2">Department ID:</label>
            <input type="number" class="form-control" id="department_id2" name="department_id" required aria-describedby="department_id_Help2">
            <small id="department_id_Help2" class="form-text text-muted">Enter the department ID of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="address2">Address:</label>
            <input type="text" class="form-control" id="address2" name="address" required aria-describedby="address_Help2">
            <small id="address_Help2" class="form-text text-muted">Enter the address of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="telephone2">Telephone:</label>
            <input type="text" class="form-control" id="telephone2" name="telephone" required aria-describedby="telephone_Help2">
            <small id="telephone_Help2" class="form-text text-muted">Enter the telephone number of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="bank_account2">Bank Account:</label>
            <input type="text" class="form-control" id="bank_account2" name="bank_account" required aria-describedby="bank_account_Help2">
            <small id="bank_account_Help2" class="form-text text-muted">Enter the bank account number of the employee you want to renew or make a new contract with.</small>
          </div>
          <div class="form-group">
            <label for="bank_name2">Bank Name:</label>
            <input type="text" class="form-control" id="bank_name2" name="bank_name" required aria-describedby="bank_name_Help2">
            <small id="bank_name_Help2" class="form-text text-muted">Enter the bank name of the employee you want to renew or make a new contract with.</small>
          </div>
          <button class="btn btn-secondary" type="button" onclick="hideForm('newContractForm')">Cancel</button>
          <button class="btn btn-primary">Submit</button>
        </fieldset>
      </form>
      <form class="form-horizontal" id="updateEmployeeForm" style="display:none;" action="http://localhost:8080/HY360/hire" method="post">
        <fieldset>
          <legend>Update Employee</legend>
          <input type="hidden" name="form" value="update">
          <div class="form-group">
            <label for="username3">Username:</label>
            <input type="text" class="form-control" id="username3" name="username" aria-describedby="usernameHelp3" required>
            <small id="usernameHelp3" class="form-text text-muted">Enter the username of the employee you want to update. Username cannot be changed!</small>
          </div>
          <div class="form-group">
            <label for="fname3">First Name:</label>
            <input type="text" class="form-control" id="fname3" name="fname" aria-describedby="fnameHelp3" required>
            <small id="fnameHelp3" class="form-text text-muted">Enter the first name that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="lastname3">Last Name:</label>
            <input type="text" class="form-control" id="lastname3" name="lastname" aria-describedby="lastnameHelp3" required>
            <small id="lastnameHelp3" class="form-text text-muted">Enter the last name that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="marital_status3">Marital Status:</label>
            <select class="form-control" id="marital_status3" name="marital_status" aria-describedby="marital_status_Help3">
              <option value="single">Single</option>
              <option value="married">Married</option>
            </select>
            <small id="marital_status_Help3" class="form-text text-muted">Select the marital status that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="children3">Number of Children:</label>
            <input type="number" class="form-control" id="children3" name="children" aria-describedby="children_Help3">
            <small id="children_Help3" class="form-text text-muted">Enter the number of children that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="ages3">Ages of Children:</label>
            <input type="text" class="form-control" id="ages3" name="ages" aria-describedby="ages_Help3">
            <small id="ages_Help3" class="form-text text-muted">Enter the ages of children that you wish to update (comma separated, without spaces).</small>
          </div>
          <div class="form-group">
            <label for="staff_category3">Staff Category:</label>
            <select class="form-control" id="staff_category3" name="staff_category" required aria-describedby="staff_category_Help3">
              <option value="">Choose category</option>
              <option value="1">Administrative Permanent</option>
              <option value="2">Teaching Permanent</option>
              <option value="3">Administrative Contractual</option>
              <option value="4">Teaching Contractual</option>
            </select>
            <small id="staff_category_Help3" class="form-text text-muted">Select the staff category that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="department_id3">Department ID:</label>
            <input type="number" class="form-control" id="department_id3" name="department_id" required aria-describedby="department_id_Help3">
            <small id="department_id_Help3" class="form-text text-muted">Enter the department ID that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="address3">Address:</label>
            <input type="text" class="form-control" id="address3" name="address" required aria-describedby="address_Help3">
            <small id="address_Help3" class="form-text text-muted">Enter the address that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="telephone3">Telephone:</label>
            <input type="text" class="form-control" id="telephone3" name="telephone" required aria-describedby="telephone_Help3">
            <small id="telephone_Help3" class="form-text text-muted">Enter the telephone number that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="bank_account3">Bank Account:</label>
            <input type="text" class="form-control" id="bank_account3" name="bank_account" required aria-describedby="bank_account_Help3">
            <small id="bank_account_Help3" class="form-text text-muted">Enter the bank account number that you wish to update.</small>
          </div>
          <div class="form-group">
            <label for="bank_name3">Bank Name:</label>
            <input type="text" class="form-control" id="bank_name3" name="bank_name" required aria-describedby="bank_name_Help3">
            <small id="bank_name_Help3" class="form-text text-muted">Enter the bank name that you wish to update.</small>
          </div>
          <button class="btn btn-secondary" type="button" onclick="hideForm('updateEmployeeForm')">Cancel</button>
          <button class="btn btn-primary"> Submit</button>
        </fieldset>
      </form>
      <form class="form-horizontal" id="updateSalaryForm" method="post" action="http://localhost:8080/HY360/raise" style="display:none;">
        <fieldset>
          <legend>Update Salary</legend>
          <div class="form-group">
            <label for="option" class="control-label col-sm-2">Select Option:</label>
            <div class="col-sm-10">
              <select class="form-control" id="option" name="option">
                <option value="1">Raise Administrative Permanent staff salary</option>
                <option value="2">Raise Teaching Permanent staff salary</option>
                <option value="3">Raise Administrative Contractual staff salary</option>
                <option value="4">Raise Teaching Contractual staff salary</option>
                <option value="5">Raise all staff categories salary</option>
                <option value="6">Raise research allowances</option>
                <option value="7">Raise library allowances</option>
                <option value="8">Raise family allowance by x% (e.g. default is 5%, raising by 2% will bring it to 7%)</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="raisePercentage" class="control-label col-sm-2">Raise Percentage:</label>
            <div class="col-sm-10">
              <input type="number" class="form-control" id="raisePercentage" name="raisePercentage" step="1" min="0" required>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button class="btn btn-secondary" type="button" onclick="hideForm('updateSalaryForm')">Cancel</button>
              <button type="submit" class="btn btn-primary">Submit</button>
            </div>
          </div>
        </fieldset>
      </form>
      <form class="form-horizontal" id="fireEmployeeForm" onsubmit="event.preventDefault();" style="display:none;">
        <h2>Fire/Retire Employee</h2>
        <div class="form-group">
          <label for="fireusername">Employee Username</label>
          <input type="text" class="form-control" name="fireusername" id="fireusername" aria-describedby="usernameHelp4" required>
          <small id="usernameHelp4" class="form-text text-muted">Enter the username of the employee to fire/retire.</small>
        </div>
        <div class="form-group">
          <button class="btn btn-secondary" type="button" onclick="hideForm('fireEmployeeForm')">Cancel</button>
          <button type="submit" class="btn btn-danger" onclick="fire()">Submit</button>
        </div>
      </form>
      <div class="container">
        <div id="staff_category_1" class="table-container" style="display: none"> Administrative Permanent: <table class="table">
            <thead>
              <tr>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Payment Date</th>
                <th>Basic Salary</th>
                <th>Applied Allowances</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody id="staff_category_1_data"></tbody>
          </table>
          <button onclick="closeTable(1)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_2" class="table-container" style="display: none"> Administrative Contractual: <table class="table">
            <thead>
              <tr>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Payment Date</th>
                <th>Basic Salary</th>
                <th>Applied Allowances</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody id="staff_category_2_data"></tbody>
          </table>
          <button onclick="closeTable(2)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_3" class="table-container" style="display: none"> Teaching Permanent: <table class="table">
            <thead>
              <tr>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Payment Date</th>
                <th>Basic Salary</th>
                <th>Applied Allowances</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody id="staff_category_3_data"></tbody>
          </table>
          <button onclick="closeTable(3)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_4" class="table-container" style="display: none"> Teaching Contractual: <table class="table">
            <thead>
              <tr>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Payment Date</th>
                <th>Basic Salary</th>
                <th>Applied Allowances</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody id="staff_category_4_data"></tbody>
          </table>
          <button onclick="closeTable(4)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_5" class="table-container" style="display: none"> Pay statistics: <table class="table">
            <thead>
              <tr>
                <th>Staff Category</th>
                <th>Minimum Total Pay</th>
                <th>Maximum Total Pay</th>
                <th>Average Total Pay</th>
              </tr>
            </thead>
            <tbody id="staff_category_5_data"></tbody>
          </table>
          <button onclick="closeTable(5)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_6" class="table-container" style="display: none"> Pay statistics: <table class="table">
            <thead>
              <tr>
                <th>Staff Category</th>
                <th>Total Pay</th>
              </tr>
            </thead>
            <tbody id="staff_category_6_data"></tbody>
          </table>
          <button onclick="closeTable(6)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_7" class="table-container" style="display: none"> Employee details: <table class="table">
            <thead>
              <tr>
                <th>Employee Username</th>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Marital Status</th>
                <th>Children</th>
                <th>Ages</th>
                <th>Staff Category ID</th>
                <th>Department ID</th>
                <th>Start Date</th>
                <th>Address</th>
                <th>Telephone</th>
                <th>Bank Account</th>
                <th>Bank Name</th>
                <th>Basic Salary</th>
                <th>Applied Allowances</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody id="staff_category_7_data"></tbody>
          </table>
          <button onclick="closeTable(7)" class="btn btn-danger">Close</button>
        </div>
        <div id="staff_category_8" class="table-container" style="display: none"> Employee details: <table class="table">
            <thead>
              <tr>
                <th>Payment ID</th>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Payment Date</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody id="staff_category_8_data"></tbody>
          </table>
          <button onclick="closeTable(8)" class="btn btn-danger">Close</button>
        </div>
      </div>
    </div>
    <script>
      function showForm(formId) {
        document.getElementById(formId).style.display = "block";
      }

      function hideForm(formId) {
        document.getElementById(formId).style.display = "none";
      }

      function submitPayment() {
        if (confirm("Are you sure you want to submit payments now?")) {
          var xhr = new XMLHttpRequest();
          xhr.open("POST", "http://localhost:8080/HY360/payment", true);
          xhr.send();
          xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
              var payrollData = JSON.parse(xhr.responseText);
              var tableData1 = document.getElementById("staff_category_1_data");
              var tableData2 = document.getElementById("staff_category_2_data");
              var tableData3 = document.getElementById("staff_category_3_data");
              var tableData4 = document.getElementById("staff_category_4_data");
              var row;
              console.log(payrollData);
              for (var i = 0; i < payrollData.length; i++) {
                switch (payrollData[i].staffCategoryId) {
                  case 1:
                    row = tableData1.insertRow();
                    break;
                  case 2:
                    row = tableData2.insertRow();
                    break;
                  case 3:
                    row = tableData3.insertRow();
                    break;
                  case 4:
                    row = tableData4.insertRow();
                    break;
                  default:
                    break;
                }
                var employeefnameCell = row.insertCell(0);
                var employeelastnameCell = row.insertCell(1);
                var paymentDateCell = row.insertCell(2);
                var basicSalaryCell = row.insertCell(3);
                var appliedAllowancesCell = row.insertCell(4);
                var totalAmountCell = row.insertCell(5);
                employeefnameCell.innerHTML = payrollData[i].fname;
                employeelastnameCell.innerHTML = payrollData[i].lastname;
                paymentDateCell.innerHTML = payrollData[i].paymentDate;
                basicSalaryCell.innerHTML = payrollData[i].basicSalary;
                appliedAllowancesCell.innerHTML = payrollData[i].appliedAllowances;
                totalAmountCell.innerHTML = payrollData[i].totalAmount;
              }
              document.getElementById("staff_category_1").style.display = "block";
              document.getElementById("staff_category_2").style.display = "block";
              document.getElementById("staff_category_3").style.display = "block";
              document.getElementById("staff_category_4").style.display = "block";
              alert("Payments submitted successfully!");
            }
          };
        }
      }

      function fire() {
        if (confirm("Are you sure you want to fire/retire this employee?")) {
          var name = document.getElementById("fireusername").value;
          var data = "username=" + name;
          var xhr = new XMLHttpRequest();
          xhr.open("POST", "http://localhost:8080/HY360/fire", true);
          xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
          xhr.send(data);
          xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
              var payrollData = JSON.parse(xhr.responseText);
              console.log(payrollData[0].error);
              if (payrollData[0].error == 0) {
                var tableData1 = document.getElementById("staff_category_1_data");
                var tableData2 = document.getElementById("staff_category_2_data");
                var tableData3 = document.getElementById("staff_category_3_data");
                var tableData4 = document.getElementById("staff_category_4_data");
                var row;
                console.log(payrollData);
                for (var i = 0; i < payrollData.length; i++) {
                  switch (payrollData[i].staffCategoryId) {
                    case 1:
                      row = tableData1.insertRow();
                      document.getElementById("staff_category_1").style.display = "block";
                      break;
                    case 2:
                      row = tableData2.insertRow();
                      document.getElementById("staff_category_2").style.display = "block";
                      break;
                    case 3:
                      row = tableData3.insertRow();
                      document.getElementById("staff_category_3").style.display = "block";
                      break;
                    case 4:
                      row = tableData4.insertRow();
                      document.getElementById("staff_category_4").style.display = "block";
                      break;
                    default:
                      break;
                  }
                  var employeefnameCell = row.insertCell(0);
                  var employeelastnameCell = row.insertCell(1);
                  var paymentDateCell = row.insertCell(2);
                  var basicSalaryCell = row.insertCell(3);
                  var appliedAllowancesCell = row.insertCell(4);
                  var totalAmountCell = row.insertCell(5);
                  employeefnameCell.innerHTML = payrollData[i].fname;
                  employeelastnameCell.innerHTML = payrollData[i].lastname;
                  paymentDateCell.innerHTML = payrollData[i].paymentDate;
                  basicSalaryCell.innerHTML = payrollData[i].basicSalary;
                  appliedAllowancesCell.innerHTML = payrollData[i].appliedAllowances;
                  totalAmountCell.innerHTML = payrollData[i].totalAmount;
                }
                alert("Fired/retired employee successfully.");
              } else {
                alert("No employee with such name exists.");
              }
            }
          };
        }
      }
      let buttonsCreated = false;

      function questions() {
        const questionsContainer = document.getElementById("questionsContainer");
        if (!buttonsCreated) {
          const buttons = [{
            text: "Check payroll info",
            onClick: function() {
              payrollInfo()
            }
          }, {
            text: "Average, minimum and maximum salary per staff category",
            onClick: function() {
              payrollStats()
            }
          }, {
            text: "Employee payroll and data",
            onClick: function() {
              employeeData()
            }
          }, {
            text: "Total pay per staff category",
            onClick: function() {
              totalPay()
            }
          }, {
            text: "Show all employee names",
            onClick: function() {
              getAllEmployees()
            }
          }, {
            text: "Show Payment History",
            onClick: function() {
              getPaymentHistory()
            }
          }];
          buttons.forEach(function(button) {
            const newButton = document.createElement("button");
            newButton.innerText = button.text;
            newButton.classList.add("btn", "btn-success", "mx-1", "my-2");
            newButton.addEventListener("click", button.onClick);
            questionsContainer.appendChild(newButton);
          });
          buttonsCreated = true;
        } else {
          questionsContainer.innerHTML = "";
          buttonsCreated = false;
        }
      }

      function payrollInfo() {
        var question = "payroll_list"
        var data = "questionType=" + question;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/HY360/question", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var payrollData = JSON.parse(xhr.responseText);
            var tableData1 = document.getElementById("staff_category_1_data");
            var tableData2 = document.getElementById("staff_category_2_data");
            var tableData3 = document.getElementById("staff_category_3_data");
            var tableData4 = document.getElementById("staff_category_4_data");
            var row;
            console.log(payrollData);
            for (var i = 0; i < payrollData.length; i++) {
              switch (payrollData[i].staffCategoryId) {
                case 1:
                  row = tableData1.insertRow();
                  break;
                case 2:
                  row = tableData2.insertRow();
                  break;
                case 3:
                  row = tableData3.insertRow();
                  break;
                case 4:
                  row = tableData4.insertRow();
                  break;
                default:
                  break;
              }
              var employeefnameCell = row.insertCell(0);
              var employeelastnameCell = row.insertCell(1);
              var paymentDateCell = row.insertCell(2);
              var basicSalaryCell = row.insertCell(3);
              var appliedAllowancesCell = row.insertCell(4);
              var totalAmountCell = row.insertCell(5);
              employeefnameCell.innerHTML = payrollData[i].fname;
              employeelastnameCell.innerHTML = payrollData[i].lastname;
              paymentDateCell.innerHTML = payrollData[i].paymentDate;
              basicSalaryCell.innerHTML = payrollData[i].basicSalary;
              appliedAllowancesCell.innerHTML = payrollData[i].appliedAllowances;
              totalAmountCell.innerHTML = payrollData[i].totalAmount;
            }
            document.getElementById("staff_category_1").style.display = "block";
            document.getElementById("staff_category_2").style.display = "block";
            document.getElementById("staff_category_3").style.display = "block";
            document.getElementById("staff_category_4").style.display = "block";
          }
        };
      }

      function payrollStats() {
        var question = "payroll_stats"
        var data = "questionType=" + question;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/HY360/question", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var payrollStats = JSON.parse(xhr.responseText);
            var tableData = document.getElementById("staff_category_5_data");
            var row;
            console.log(payrollStats);
            for (var i = 0; i < 4; i++) {
              row = tableData.insertRow();
              var staffCategoryCell = row.insertCell(0);
              var minimumPayCell = row.insertCell(1);
              var maximumPayCell = row.insertCell(2);
              var averagePayCell = row.insertCell(3);
              switch (i) {
                case 0:
                  staffCategoryCell.innerHTML = "Administrative Permanent";
                  break;
                case 1:
                  staffCategoryCell.innerHTML = "Teaching Permanent";
                  break;
                case 2:
                  staffCategoryCell.innerHTML = "Administrative Contractual";
                  break;
                case 3:
                  staffCategoryCell.innerHTML = "Teaching Contractual";
                  break;
              }
              minimumPayCell.innerHTML = payrollStats[i].min;
              maximumPayCell.innerHTML = payrollStats[i].max;
              averagePayCell.innerHTML = payrollStats[i].avg;
            }
            document.getElementById("staff_category_5").style.display = "block";
          }
        };
      }

      function totalPay() {
        var question = "total_pay"
        var data = "questionType=" + question;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/HY360/question", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var totalPayStats = JSON.parse(xhr.responseText);
            var tableData = document.getElementById("staff_category_6_data");
            var row;
            console.log(payrollStats);
            for (var i = 0; i < 4; i++) {
              row = tableData.insertRow();
              var staffCategoryCell = row.insertCell(0);
              var totalPayCell = row.insertCell(1);
              switch (i) {
                case 0:
                  staffCategoryCell.innerHTML = "Administrative Permanent";
                  break;
                case 1:
                  staffCategoryCell.innerHTML = "Teaching Permanent";
                  break;
                case 2:
                  staffCategoryCell.innerHTML = "Administrative Contractual";
                  break;
                case 3:
                  staffCategoryCell.innerHTML = "Teaching Contractual";
                  break;
              }
              totalPayCell.innerHTML = totalPayStats[i].sum;
            }
            document.getElementById("staff_category_6").style.display = "block";
          }
        };
      }

      function employeeData() {
        var question = "employee_data"
        var username = prompt("Please enter the username of the employee you are searching for");
        var data = "questionType=" + question + "&username=" + username;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/HY360/question", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var resp = JSON.parse(xhr.responseText);
            if (resp[0].error == 0) {
              var tableData = document.getElementById("staff_category_7_data");
              var row = tableData.insertRow();
              var employeeusernameCell = row.insertCell(0);
              var employeefnameCell = row.insertCell(1);
              var employeelastnameCell = row.insertCell(2);
              var maritalStatusCell = row.insertCell(3);
              var childrenCell = row.insertCell(4);
              var agesCell = row.insertCell(5);
              var staffCategoryIdCell = row.insertCell(6);
              var departmentIdCell = row.insertCell(7);
              var startDateCell = row.insertCell(8);
              var addressCell = row.insertCell(9);
              var telephoneCell = row.insertCell(10);
              var bankAccountCell = row.insertCell(11);
              var bankNameCell = row.insertCell(12);
              var basicSalaryCell = row.insertCell(13);
              var appliedAllowancesCell = row.insertCell(14);
              var totalAmountCell = row.insertCell(15);
              employeeusernameCell.innerHTML = resp[0].username;
              employeefnameCell.innerHTML = resp[0].fname;
              employeelastnameCell.innerHTML = resp[0].lastname;
              maritalStatusCell.innerHTML = resp[0].marital_status;
              if (resp[0].children == 0) {
                childrenCell.innerHTML = "-"
                agesCell.innerHTML = "-"
              } else {
                childrenCell.innerHTML = resp[0].children;
                agesCell.innerHTML = resp[0].ages;
              }
              staffCategoryIdCell.innerHTML = resp[0].staffCategoryId;
              departmentIdCell.innerHTML = resp[0].departmentId;
              startDateCell.innerHTML = resp[0].start_date;
              addressCell.innerHTML = resp[0].address;
              telephoneCell.innerHTML = resp[0].telephone;
              bankAccountCell.innerHTML = resp[0].bank_account;
              bankNameCell.innerHTML = resp[0].bank_name;
              basicSalaryCell.innerHTML = resp[0].basicSalary;
              appliedAllowancesCell.innerHTML = resp[0].appliedAllowances;
              totalAmountCell.innerHTML = resp[0].totalAmount;
              document.getElementById("staff_category_7").style.display = "block";
            } else {
              alert("No employee with such name exists.");
            }
          }
        };
      }

      function getAllEmployees() {
        var question = "all_employees";
        var data = "questionType=" + question;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/HY360/question", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var resp = JSON.parse(xhr.responseText);
            console.log(resp);
            var row;
            for (var i = 0; i < resp.length; i++) {
              var tableData = document.getElementById("staff_category_7_data");
              row = tableData.insertRow();
              var employeeusernameCell = row.insertCell(0);
              var employeefnameCell = row.insertCell(1);
              var employeelastnameCell = row.insertCell(2);
              var maritalStatusCell = row.insertCell(3);
              var childrenCell = row.insertCell(4);
              var agesCell = row.insertCell(5);
              var staffCategoryIdCell = row.insertCell(6);
              var departmentIdCell = row.insertCell(7);
              var startDateCell = row.insertCell(8);
              var addressCell = row.insertCell(9);
              var telephoneCell = row.insertCell(10);
              var bankAccountCell = row.insertCell(11);
              var bankNameCell = row.insertCell(12);
              var basicSalaryCell = row.insertCell(13);
              var appliedAllowancesCell = row.insertCell(14);
              var totalAmountCell = row.insertCell(15);
              employeeusernameCell.innerHTML = resp[i].username;
              employeefnameCell.innerHTML = resp[i].fname;
              employeelastnameCell.innerHTML = resp[i].lastname;
              maritalStatusCell.innerHTML = resp[i].marital_status;
              if (resp[i].children == 0) {
                childrenCell.innerHTML = "-"
                agesCell.innerHTML = "-"
              } else {
                childrenCell.innerHTML = resp[i].children;
                agesCell.innerHTML = resp[i].ages;
              }
              staffCategoryIdCell.innerHTML = resp[i].staffCategoryId;
              departmentIdCell.innerHTML = resp[i].departmentId;
              startDateCell.innerHTML = resp[i].start_date;
              addressCell.innerHTML = resp[i].address;
              telephoneCell.innerHTML = resp[i].telephone;
              bankAccountCell.innerHTML = resp[i].bank_account;
              bankNameCell.innerHTML = resp[i].bank_name;
              basicSalaryCell.innerHTML = resp[i].basicSalary;
              appliedAllowancesCell.innerHTML = resp[i].appliedAllowances;
              totalAmountCell.innerHTML = resp[i].totalAmount;
              document.getElementById("staff_category_7").style.display = "block";
            }
          }
        };
      }

      function getPaymentHistory() {
        var question = "payment_history";
        var data = "questionType=" + question;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/HY360/question", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(data);
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var resp = JSON.parse(xhr.responseText);
            console.log(resp);
            var row;
            for (var i = 0; i < resp.length; i++) {
              var tableData = document.getElementById("staff_category_8_data");
              row = tableData.insertRow();
              var paymentIdCell = row.insertCell(0);
              var fnameCell = row.insertCell(1);
              var lastnameCell = row.insertCell(2);
              var paymentDateCell = row.insertCell(3);
              var totalAmountCell = row.insertCell(4);
              paymentIdCell.innerHTML = resp[i].payment_id;
              fnameCell.innerHTML = resp[i].fname;
              lastnameCell.innerHTML = resp[i].lastname;
              paymentDateCell.innerHTML = resp[i].payment_date;
              totalAmountCell.innerHTML = resp[i].total_amount;
              document.getElementById("staff_category_8").style.display = "block";
            }
          }
        };
      }

      function closeTable(tableId) {
        var table = document.getElementById("staff_category_" + tableId);
        tbody = document.getElementById("staff_category_" + tableId + "_data");
        tbody.innerHTML = "";
        table.style.display = "none";
      }
    </script>
  </body>
</html>