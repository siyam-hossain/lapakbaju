const slides = document.querySelectorAll('.banner-slide');
const dots = document.querySelectorAll('.dot');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');

let currentIndex = 0;
let slideInterval;
const intervalTime = 5000; // Auto-slide every 5 seconds

// Function to go to a specific slide
function goToSlide(index) {
    slides.forEach((slide, i) => {
        slide.classList.remove('active');
        dots[i].classList.remove('active');
    });

    currentIndex = (index + slides.length) % slides.length;

    slides[currentIndex].classList.add('active');
    dots[currentIndex].classList.add('active');
}

// Next Slide
function nextSlide() {
    goToSlide(currentIndex + 1);
}

// Previous Slide
function prevSlide() {
    goToSlide(currentIndex - 1);
}

// Start Auto Slide
function startAutoSlide() {
    slideInterval = setInterval(nextSlide, intervalTime);
}

// Reset Timer on Manual Interaction
function resetTimer() {
    clearInterval(slideInterval);
    startAutoSlide();
}

// Event Listeners for Buttons
nextBtn.addEventListener('click', () => {
    nextSlide();
    resetTimer();
});

prevBtn.addEventListener('click', () => {
    prevSlide();
    resetTimer();
});

// Event Listeners for Dots
dots.forEach(dot => {
    dot.addEventListener('click', (e) => {
        const slideIndex = parseInt(e.target.getAttribute('data-index'));
        goToSlide(slideIndex);
        resetTimer();
    });
});

// Initialize Auto Play
startAutoSlide();