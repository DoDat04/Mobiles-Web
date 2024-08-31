/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function saveScrollPosition() {
    console.log('Saving scroll position:', window.scrollY); // Log current scroll position
    sessionStorage.setItem('scrollPosition', window.scrollY);
}

window.onload = function () {
    setTimeout(function () {
        const scrollPosition = sessionStorage.getItem('scrollPosition');
        console.log('Restoring scroll position:', scrollPosition); // Log stored scroll position
        if (scrollPosition) {
            window.scrollTo(0, parseInt(scrollPosition, 10));
            sessionStorage.removeItem('scrollPosition');
        }
    }, 100); // Adjust the delay as needed
};

window.onbeforeunload = function () {
    saveScrollPosition();
};

function loadComments() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "MobilesController?action=loadComments", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.querySelector('.comments-display').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

setInterval(loadComments, 1000); // Tự động làm mới bình luận sau mỗi 1 giây

function showModal(message) {
    var messageDiv = document.getElementById("success-message");
    messageDiv.innerText = message;
    var modal = document.getElementById("success-modal");
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("success-modal");
    modal.style.display = "none";
}

window.onclick = function (event) {
    var modal = document.getElementById("success-modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};
