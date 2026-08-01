const loginTab = document.getElementById('loginTab');
const registerTab = document.getElementById('registerTab');
const switcherBg = document.getElementById('switcherBg');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');

loginTab.addEventListener('click', () => {
    loginTab.classList.add('active');
    registerTab.classList.remove('active');
    switcherBg.classList.remove('register');

    loginForm.classList.remove('hidden');
    registerForm.classList.add('hidden');
});

registerTab.addEventListener('click', () => {
    registerTab.classList.add('active');
    loginTab.classList.remove('active');
    switcherBg.classList.add('register');

    registerForm.classList.remove('hidden');
    loginForm.classList.add('hidden');
});