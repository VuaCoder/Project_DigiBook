document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("customerModal");
    const modalName = document.getElementById("modalName");
    const modalInfo = document.getElementById("modalInfo");
    const closeBtn = document.querySelector(".close");

    document.querySelectorAll(".customer-item").forEach(item => {
        item.addEventListener("click", () => {
            modal.style.display = "flex";
            modalName.textContent = item.dataset.name;
            modalInfo.textContent = item.dataset.info;
        });
    });

    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    window.addEventListener("click", (e) => {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    });
});
