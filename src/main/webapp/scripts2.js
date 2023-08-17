document.addEventListener("DOMContentLoaded", function() {
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
});

$('#sandbox-container .input-daterange').datepicker({
    todayHighlight: true
});

