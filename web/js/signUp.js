/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Function to show the error modal with a specific message
function showModal(error) {
    var errorMessageDiv = document.getElementById("error-message");
    errorMessageDiv.innerText = error;
    var modal = document.getElementById("error-modal");
    modal.style.display = "block";
}

// Function to close the error modal
function closeModal() {
    var modal = document.getElementById("error-modal");
    modal.style.display = "none";
}

// Close the modal when the user clicks outside of it
window.onclick = function(event) {
    var modal = document.getElementById("error-modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

// Function to open the Terms of Service & Privacy Policy modal
function openTermsModal() {
    document.getElementById("terms-modal").style.display = "block";
}

// Function to close the Terms of Service & Privacy Policy modal
function closeTermsModal() {
    document.getElementById("terms-modal").style.display = "none";
}

