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