/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$(document).ready(function () {
    $('#auth-icon').on('click', function () {
        $('.account_dropdown_menu').toggleClass('show');
    });

    // Close the dropdown if clicked outside
    $(document).on('click', function (e) {
        if (!$(e.target).closest('.auth-links').length) {
            $('.account_dropdown_menu').removeClass('show');
        }
    });
});

