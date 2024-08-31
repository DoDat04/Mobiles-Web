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

document.addEventListener('DOMContentLoaded', function() {
    // Select password fields and their respective toggle icons
    var newPasswordField = document.getElementById('newPassword');
    var confirmPasswordField = document.getElementById('confirmPassword');
    var toggleNewPasswordIcon = document.getElementById('toggleNewPasswordIcon');
    var toggleConfirmPasswordIcon = document.getElementById('toggleConfirmPasswordIcon');

    // Toggle visibility for New Password field
    toggleNewPasswordIcon.addEventListener('click', function () {
        if (newPasswordField.type === 'password') {
            newPasswordField.type = 'text';
            toggleNewPasswordIcon.classList.remove('fa-eye');
            toggleNewPasswordIcon.classList.add('fa-eye-slash');
        } else {
            newPasswordField.type = 'password';
            toggleNewPasswordIcon.classList.remove('fa-eye-slash');
            toggleNewPasswordIcon.classList.add('fa-eye');
        }
    });

    // Toggle visibility for Confirm Password field
    toggleConfirmPasswordIcon.addEventListener('click', function () {
        if (confirmPasswordField.type === 'password') {
            confirmPasswordField.type = 'text';
            toggleConfirmPasswordIcon.classList.remove('fa-eye');
            toggleConfirmPasswordIcon.classList.add('fa-eye-slash');
        } else {
            confirmPasswordField.type = 'password';
            toggleConfirmPasswordIcon.classList.remove('fa-eye-slash');
            toggleConfirmPasswordIcon.classList.add('fa-eye');
        }
    });
});




