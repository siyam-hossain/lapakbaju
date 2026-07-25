const navRight = document.querySelector('.nav-right');
const menuBtn = document.querySelector('.nav-menu-btn');
const closeBtn = document.querySelector('.nav-close-btn');
const navItems = document.querySelector('.nav-right-items');
const navLinks = document.querySelectorAll('.nav-link');

menuBtn.addEventListener('click', () => {
    navRight.classList.add('active');
    navItems.classList.add('show');
});

closeBtn.addEventListener('click', () => {
    navRight.classList.remove('active');
    navItems.classList.remove('show');
});

navLinks.forEach(link => {
    link.addEventListener('click', () => {
        navRight.classList.remove('active');
        navItems.classList.remove('show');
    });
});