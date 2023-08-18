/*document.addEventListener("DOMContentLoaded", function() {
    const receiptTypeSelect = document.getElementById("receiptType");
    const receiptValue = document.getElementById("receiptValue");
    const addReceiptButton = document.getElementById("addNewReceiptButton");
    const receiptList = document.getElementById("receiptList");

    addReceiptButton.addEventListener("click", () => {
        const selectedOption = receiptTypeSelect.options[receiptTypeSelect.selectedIndex];
        const receiptText = selectedOption.text;
        const receiptValueText = receiptValue.value;

        const newRow = receiptList.insertRow();
        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);

        cell1.textContent = receiptText;
        cell2.textContent = receiptValueText;

        const removeButton = document.createElement("button");
        removeButton.textContent = "Remove";
        removeButton.addEventListener("click", () => {
            receiptList.deleteRow(newRow.rowIndex);
        });

        cell3.appendChild(removeButton);
    });
});*/

/*$(document).ready(function() {
    $('#tripEndDate').on('input', function() {
        let currentSetStartDate = new Date(document.getElementById("tripStartDate").value);
        let currentSetEndDate = new Date($(this).val());
        let differenceInMs = currentSetEndDate - currentSetStartDate;
        let differenceInDays = Math.floor(differenceInMs / (1000 * 60 * 60 * 24));
        $('#numberOfDays').val(differenceInDays);
    });

    $('#numberOfDays').on('input', function() {
        const numberOfDays = parseInt($(this).val());
        const tripStartDate = new Date(document.getElementById("tripStartDate").value);

        if (!isNaN(numberOfDays)) {
            const newEndDateTimestamp = tripStartDate.getTime() + numberOfDays * 24 * 60 * 60 * 1000;
            const newEndDate = new Date(newEndDateTimestamp);

            const formattedNewEndDate = newEndDate.toISOString().split('T')[0];
            $('#tripEndDate').val(formattedNewEndDate);
        }
    });

});*/

document.addEventListener("DOMContentLoaded", function() {
    let tripStartDate = document.getElementById("tripStartDate");
    let tripEndDate = document.getElementById("tripEndDate");
    let numberOfDays = document.getElementById("numberOfDays");
    let tripFieldChanged = document.getElementById("tripFieldChanged");

    tripStartDate.addEventListener("input", () => {
        tripFieldChanged.value = "tripStartDate";
        if(tripStartDate.value) {
            let form = document.getElementById("calculate-reimbursement/modify-duration");
            form.submit();
        }
    });

    tripEndDate.addEventListener("input", () => {
        tripFieldChanged.value = "tripEndDate";
        if(tripEndDate.value) {
            let form = document.getElementById("calculate-reimbursement/modify-duration");
            form.submit();
        }
    });


    numberOfDays.addEventListener("input", () => {
        tripFieldChanged.value = "numberOfDays";
        if(typeof numberOfDays.value === 'number' && numberOfDays.value > 0) {
            let form = document.getElementById("calculate-reimbursement/modify-duration");
            form.submit();
        }
    });


});