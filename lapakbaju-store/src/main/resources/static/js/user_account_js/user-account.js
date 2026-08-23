document.addEventListener("DOMContentLoaded", () => {

    const accountLinks = document.querySelectorAll(".menu-item");

    accountLinks.forEach(link => {

        link.addEventListener("click", () => {

            accountLinks.forEach(item => {
                item.classList.remove("active");
            });

            link.classList.add("active");

        });

    });

});