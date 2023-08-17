<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Business Trip Reimbursement Calculation Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <link href=”https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Business Trip Reimbursement Calculator</h1>
    <form method="post" action="calculate-reimbursement">
        <div class="container" id="calculatorFormContainer">
            <h3>Provide data:</h3>
            <div class="input-group mb-3">
                <label for="tripStartDate" class="input-group-text" id="basic-addon1">Trip start date</label>
                <input type="date" class="form-control" id="tripStartDate" name="tripStartDate">
            </div>
            <div class="input-group mb-3">
                <input type="date" class="form-control" id="tripEndDate" name="tripEndDate">
                <span class="input-group-text">Choose end date or trip duration in days</span>
                <input type="number" id="numberOfDays" name="numberOfDays" min="1">
            </div>

            <div>
                <label for="receiptValue">Receipt value in dollars $:</label>
                <input type="text" id="receiptValue" name="receiptValue">
                <select id="receiptType">
                    <option value="taxi">Taxi</option>
                    <option value="hotel">Hotel</option>
                    <option value="plane">Plane Ticket</option>
                    <option value="train">Train</option>
                </select>
                <button type="button" id="addNewReceiptButton">Add new Receipt</button>
                <br>
                <table id="receiptList">
                    <tr>
                        <th>Receipt Type</th>
                        <th>Receipt Value</th>
                        <th>Action</th>
                    </tr>
                </table>
            </div>


            <label for="inputPassword5" class="form-label">Password</label>
            <input type="password" id="inputPassword5" class="form-control" aria-describedby="passwordHelpBlock">
            <div id="passwordHelpBlock" class="form-text">
                Your password must be 8-20 characters long, contain letters and numbers, and must not contain spaces, special characters, or emoji.
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </form>
    <script src="scripts2.js"></script>
</body>
</html>
