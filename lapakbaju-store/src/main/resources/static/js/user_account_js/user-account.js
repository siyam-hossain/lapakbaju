document.addEventListener("DOMContentLoaded", () => {
    /* =========================================================
         ACCOUNT MENU
         ========================================================= */

    const accountLinks = document.querySelectorAll(".menu-item");

    accountLinks.forEach((link) => {
        link.addEventListener("click", () => {
            accountLinks.forEach((item) => {
                item.classList.remove("active");
            });

            link.classList.add("active");
        });
    });

    /* =========================================================
         PROFILE EDIT MODE
         ========================================================= */

    const editButton = document.getElementById("editProfileButton");

    const profileView = document.getElementById("profileView");

    const profileEditForm = document.getElementById("profileEditForm");

    if (editButton && profileView && profileEditForm) {
        editButton.addEventListener("click", () => {
            const editing = profileEditForm.classList.contains("active");

            if (!editing) {
                /* ==============================
                           ENTER EDIT MODE
                           ============================== */

                profileView.style.display = "none";

                profileEditForm.classList.add("active");

                editButton.textContent = "Cancel";

                /* Scroll to profile editor */

                profileEditForm.scrollIntoView({
                    behavior: "smooth",
                    block: "center",
                });
            } else {
                /* ==============================
                           EXIT EDIT MODE
                           ============================== */

                profileEditForm.classList.remove("active");

                profileView.style.display = "flex";

                editButton.textContent = "Edit Profile";

                /* Reset form */

                profileEditForm.reset();

                resetImagePreview();
            }
        });
    }

    /* =========================================================
         PROFILE IMAGE
         ========================================================= */

    const profileImageInput = document.getElementById("profileImage");

    const editAvatar = document.getElementById("editAvatar");

    const avatarInitial = document.getElementById("avatarInitial");

    if (profileImageInput) {
        profileImageInput.addEventListener("change", (event) => {
            const file = event.target.files[0];

            if (!file) {
                return;
            }

            /* ==============================
                     VALIDATE TYPE
                     ============================== */

            const allowedTypes = ["image/jpeg", "image/png", "image/webp"];

            if (!allowedTypes.includes(file.type)) {
                alert("Please select a JPG, PNG or WebP image.");

                profileImageInput.value = "";

                return;
            }

            /* ==============================
                     VALIDATE SIZE
                     ============================== */

            const maxSize = 5 * 1024 * 1024;

            if (file.size > maxSize) {
                alert("Image size must be less than 5MB.");

                profileImageInput.value = "";

                return;
            }

            /* ==============================
                     CREATE PREVIEW
                     ============================== */

            const reader = new FileReader();

            reader.onload = (e) => {
                /* Remove initial */

                if (avatarInitial) {
                    avatarInitial.style.display = "none";
                }

                /* Remove old preview */

                const oldImage = document.getElementById("profileImagePreview");

                if (oldImage) {
                    oldImage.remove();
                }

                /* Create new image */

                const image = document.createElement("img");

                image.id = "profileImagePreview";

                image.src = e.target.result;

                image.alt = "Profile Preview";

                editAvatar.prepend(image);
            };

            reader.readAsDataURL(file);
        });
    }

    /* =========================================================
         RESET IMAGE PREVIEW
         ========================================================= */

    function resetImagePreview() {
        if (!editAvatar) {
            return;
        }

        const preview = document.getElementById("profileImagePreview");

        if (preview) {
            preview.remove();
        }

        if (profileImageInput) {
            profileImageInput.value = "";
        }

        if (avatarInitial) {
            avatarInitial.style.display = "block";
        }
    }

    /* =========================================================
         UPDATE BUTTON
         ========================================================= */

    const updateButton = document.getElementById("updateButton");

    if (updateButton) {
        updateButton.addEventListener("click", (event) => {
            /*
             * If the user hasn't clicked Edit Profile,
             * don't submit the hidden form.
             */

            if (!profileEditForm || !profileEditForm.classList.contains("active")) {
                event.preventDefault();

                alert("Click 'Edit Profile' before updating your information.");
            }
        });
    }
});
