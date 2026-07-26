document.addEventListener("DOMContentLoaded", () => {
    const categoryTrack = document.getElementById('categoryTrack');

    if (!categoryTrack) return;

    // 1. Fetch categories from Spring Boot REST API using Promise chains
    function fetchCategories() {
        fetch('/api/categories')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(categories => {
                renderCategories(categories);
            })
            .catch(error => {
                console.error('Error fetching categories:', error);
                categoryTrack.innerHTML = '<p class="error-msg">Failed to load categories.</p>';
            });
    }

    // 2. Render HTML items dynamically
    function renderCategories(categories) {
        categoryTrack.innerHTML = '';

        categories.forEach((cat) => {
            const item = document.createElement('div');
            item.className = `category-item ${cat.defaultActive ? 'active' : ''}`;

            item.innerHTML = `
                <div class="category-icon-wrapper" style="background-color: ${cat.bg}; color: ${cat.color};">
                    <i class="fa-solid ${cat.icon}"></i>
                </div>
                <span class="category-name">${cat.name}</span>
            `;

            item.addEventListener('click', () => {
                if (isDragged) return; // Prevent click if user was dragging
                document.querySelectorAll('.category-item').forEach(el => el.classList.remove('active'));
                item.classList.add('active');
            });

            categoryTrack.appendChild(item);
        });
    }

    // 3. Drag to Scroll Logic
    let isDown = false;
    let startX;
    let scrollLeft;
    let isDragged = false;

    categoryTrack.addEventListener('mousedown', (e) => {
        isDown = true;
        isDragged = false;
        categoryTrack.classList.add('active');
        startX = e.pageX - categoryTrack.offsetLeft;
        scrollLeft = categoryTrack.scrollLeft;
    });

    categoryTrack.addEventListener('mouseleave', () => {
        isDown = false;
        categoryTrack.classList.remove('active');
    });

    categoryTrack.addEventListener('mouseup', () => {
        isDown = false;
        categoryTrack.classList.remove('active');
    });

    categoryTrack.addEventListener('mousemove', (e) => {
        if (!isDown) return;
        e.preventDefault();
        isDragged = true;
        const x = e.pageX - categoryTrack.offsetLeft;
        const walk = (x - startX) * 2;
        categoryTrack.scrollLeft = scrollLeft - walk;
    });

    // Initial Trigger
    fetchCategories();
});