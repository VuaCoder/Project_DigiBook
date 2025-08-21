document.addEventListener('DOMContentLoaded', function () {
    /* ----------------- Hamburger Menu ----------------- */
    const hamburger = document.getElementById('hamburger-btn');
    const navMenu = document.getElementById('nav-menu');
    const navLinks = navMenu.querySelectorAll('a');

    hamburger.addEventListener('click', () => {
        hamburger.classList.toggle('active');
        navMenu.classList.toggle('active');
    });

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            hamburger.classList.remove('active');
            navMenu.classList.remove('active');
        });
    });

    /* ----------------- Background rotation ----------------- */
    const backgrounds = document.querySelectorAll('.background-blurred img');
    let currentBgIndex = 0;

    function rotateBackground() {
        backgrounds[currentBgIndex].classList.remove('active');
        currentBgIndex = (currentBgIndex + 1) % backgrounds.length;
        backgrounds[currentBgIndex].classList.add('active');
    }
    if (backgrounds.length > 1) setInterval(rotateBackground, 5000);

    /* ----------------- Grid Layout ----------------- */
    function adjustGridLayout() {
        const framesContainer = document.querySelector('.digi-frames');
        const frames = document.querySelectorAll('.digi-frame');
        const bookCount = frames.length;

        if (!framesContainer) return;

        framesContainer.classList.remove('single-book', 'two-books', 'three-books', 'many-books');
        if (bookCount === 1) framesContainer.classList.add('single-book');
        else if (bookCount === 2) framesContainer.classList.add('two-books');
        else if (bookCount === 3) framesContainer.classList.add('three-books');
        else framesContainer.classList.add('many-books');

        frames.forEach(frame => {
            frame.style.minHeight = bookCount <= 3 ? '320px' : '280px';
        });
    }
    adjustGridLayout();
    window.addEventListener('resize', adjustGridLayout);

    /* ----------------- Smooth Scroll ----------------- */
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({behavior: 'smooth', block: 'start'});
            }
        });
    });

    /* ----------------- Reveal Expert Section ----------------- */
    const expertSection = document.querySelector('.expert-section');
    if (expertSection) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('revealed');
                    observer.unobserve(entry.target);
                }
            });
        }, {threshold: 0.25});
        observer.observe(expertSection);
    }

    /* ----------------- Expert Slider with Dots ----------------- */
    const cards = document.querySelectorAll(".expert-card");
    const dotsContainer = document.querySelector(".expert-dots");
    let currentExpert = 0;
    let expertInterval;

    function createDot(index) {
        const dot = document.createElement("span");
        dot.classList.add("dot");
        dot.dataset.index = index;
        dot.addEventListener("click", () => {
            showExpert(index);
            clearInterval(expertInterval);
            startExpertAutoSlide();
        });
        dotsContainer.appendChild(dot);
    }

    function renderDots() {
        dotsContainer.innerHTML = "";
        const total = cards.length;

        if (total <= 6) {
            for (let i = 0; i < total; i++) {
                createDot(i);
            }
        } else {
            for (let i = 0; i < total; i++) {
                if (
                    i === 0 || i === total - 1 ||
                    i === currentExpert || i === currentExpert - 1 || i === currentExpert + 1
                ) {
                    createDot(i);
                } else if (
                    (i === 1 && currentExpert > 3) ||
                    (i === total - 2 && currentExpert < total - 4)
                ) {
                    const span = document.createElement("span");
                    span.textContent = "...";
                    span.classList.add("dot", "disabled");
                    dotsContainer.appendChild(span);
                }
            }
        }
        updateActiveDot();
    }

    function updateActiveDot() {
        const dots = dotsContainer.querySelectorAll(".dot");
        dots.forEach(dot => dot.classList.remove("active"));
        const activeDot = [...dots].find(dot => dot.dataset.index == currentExpert);
        if (activeDot) activeDot.classList.add("active");
    }

    function showExpert(index) {
        cards.forEach(card => card.classList.remove("active"));
        cards[index].classList.add("active");
        currentExpert = index;
        renderDots();
    }

    function startExpertAutoSlide() {
        expertInterval = setInterval(() => {
            let next = (currentExpert + 1) % cards.length;
            showExpert(next);
        }, 10000); // <-- 10 giây tự động chuyển
    }

    if (cards.length > 0) {
        showExpert(0);
        startExpertAutoSlide();
    }

    /* ----------------- Book Frames Load Animation ----------------- */
    const bookFrames = document.querySelectorAll('.digi-frame');
    bookFrames.forEach((frame, index) => {
        frame.style.opacity = '0';
        frame.style.transform = 'translateY(20px)';

        setTimeout(() => {
            frame.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
            frame.style.opacity = '1';
            frame.style.transform = 'translateY(0)';
        }, index * 100);
    });
});
