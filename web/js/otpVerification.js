/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function showModal(error) {
    var errorMessageDiv = document.getElementById("error-message");
    errorMessageDiv.innerText = error;
    var modal = document.getElementById("error-modal");
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("error-modal");
    modal.style.display = "none";
}

window.onclick = function (event) {
    var modal = document.getElementById("error-modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

var timeLeft = 60;
var timer = setInterval(function () {
    timeLeft--;
    document.getElementById("time").textContent = timeLeft;
    if (timeLeft <= 0) {
        clearInterval(timer);
        alert("OTP has expired! Please request a new one.");
        window.location.href = 'forgotPassword.jsp'; // Redirect to forgot password page
    }
}, 1000);

