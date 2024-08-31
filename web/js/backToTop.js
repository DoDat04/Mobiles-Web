/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
(function () {
    var backTop = document.querySelector('.js-cd-top'),
        offset = 300, // Scroll position after which the "back to top" link is shown
        offsetOpacity = 1200; // Scroll position after which the "back to top" link opacity is reduced

    if (backTop) {
        // Update back to top visibility on scrolling
        window.addEventListener("scroll", function () {
            var windowTop = window.scrollY || document.documentElement.scrollTop;
            
            if (windowTop > offset) {
                backTop.classList.add('cd-top--is-visible');
            } else {
                backTop.classList.remove('cd-top--is-visible', 'cd-top--fade-out');
            }
            
            if (windowTop > offsetOpacity) {
                backTop.classList.add('cd-top--fade-out');
            } else {
                backTop.classList.remove('cd-top--fade-out');
            }
        });

        // Smooth scroll to top
        backTop.addEventListener('click', function (event) {
            event.preventDefault();
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }
})();


