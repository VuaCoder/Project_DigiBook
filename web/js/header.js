/**
 * DigiBook Header JavaScript
 * Cung cấp tất cả chức năng header cho mọi trang
 * Hỗ trợ đầy đủ responsive và mobile navigation
 */

class DigiBookHeader {
    constructor() {
        this.hamburger = null;
        this.navMenu = null;
        this.header = null;
        this.isMenuOpen = false;
        this.currentBreakpoint = this.getBreakpoint();
        this.init();
    }

    /**
     * Khởi tạo header
     */
    init() {
        console.log('DigiBook Header initializing...');
        
        // Tìm các elements
        this.hamburger = document.getElementById('hamburger-btn');
        this.navMenu = document.getElementById('nav-menu');
        this.header = document.querySelector('.header');
        
        if (!this.hamburger || !this.navMenu || !this.header) {
            console.error('Header elements not found:', { 
                hamburger: this.hamburger, 
                navMenu: this.navMenu, 
                header: this.header 
            });
            return;
        }

        console.log('Header elements found successfully');
        
        // Thiết lập event listeners
        this.setupEventListeners();
        
        // Thiết lập responsive behavior
        this.setupResponsive();
        
        // Thiết lập scroll behavior
        this.setupScrollBehavior();
        
        // Thiết lập keyboard navigation
        this.setupKeyboardNavigation();
        
        console.log('DigiBook Header initialized successfully');
    }

    /**
     * Thiết lập tất cả event listeners
     */
    setupEventListeners() {
        // Hamburger click
        this.hamburger.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            this.toggleMenu();
        });

        // Click outside để đóng menu
        document.addEventListener('click', (e) => {
            if (!this.hamburger.contains(e.target) && !this.navMenu.contains(e.target)) {
                this.closeMenu();
            }
        });

        // Click menu items để đóng menu (mobile)
        const menuItems = this.navMenu.querySelectorAll('a');
        menuItems.forEach(item => {
            item.addEventListener('click', () => {
                if (this.currentBreakpoint === 'mobile') {
                    this.closeMenu();
                }
            });
        });

        // Touch events cho mobile
        this.setupTouchEvents();
    }

    /**
     * Thiết lập responsive behavior
     */
    setupResponsive() {
        // Theo dõi thay đổi kích thước màn hình
        window.addEventListener('resize', () => {
            const newBreakpoint = this.getBreakpoint();
            
            if (newBreakpoint !== this.currentBreakpoint) {
                this.currentBreakpoint = newBreakpoint;
                this.handleBreakpointChange(newBreakpoint);
            }
        });

        // Xử lý breakpoint ban đầu
        this.handleBreakpointChange(this.currentBreakpoint);
    }

    /**
     * Thiết lập scroll behavior
     */
    setupScrollBehavior() {
        let lastScrollTop = 0;
        const scrollThreshold = 100;

        window.addEventListener('scroll', () => {
            const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            
            // Auto-hide header khi scroll xuống, show khi scroll lên
            if (scrollTop > lastScrollTop && scrollTop > scrollThreshold) {
                this.header.classList.add('header-hidden');
            } else if (scrollTop < lastScrollTop) {
                this.header.classList.remove('header-hidden');
            }
            
            lastScrollTop = scrollTop;
        });
    }

    /**
     * Thiết lập keyboard navigation
     */
    setupKeyboardNavigation() {
        // ESC key để đóng menu
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && this.isMenuOpen) {
                this.closeMenu();
            }
        });

        // Tab navigation trong menu
        this.navMenu.addEventListener('keydown', (e) => {
            if (e.key === 'Tab') {
                const focusableElements = this.navMenu.querySelectorAll(
                    'a, button, input, select, textarea, [tabindex]:not([tabindex="-1"])'
                );
                
                if (e.shiftKey && document.activeElement === focusableElements[0]) {
                    e.preventDefault();
                    focusableElements[focusableElements.length - 1].focus();
                } else if (!e.shiftKey && document.activeElement === focusableElements[focusableElements.length - 1]) {
                    e.preventDefault();
                    focusableElements[0].focus();
                }
            }
        });
    }

    /**
     * Thiết lập touch events cho mobile
     */
    setupTouchEvents() {
        let touchStartY = 0;
        let touchEndY = 0;

        // Touch start
        this.navMenu.addEventListener('touchstart', (e) => {
            touchStartY = e.touches[0].clientY;
        });

        // Touch end
        this.navMenu.addEventListener('touchend', (e) => {
            touchEndY = e.changedTouches[0].clientY;
            this.handleTouchGesture(touchStartY, touchEndY);
        });

        // Swipe để đóng menu
        this.navMenu.addEventListener('touchmove', (e) => {
            const touchY = e.touches[0].clientY;
            const touchDiff = touchStartY - touchY;
            
            // Swipe up để đóng menu
            if (touchDiff > 50) {
                this.closeMenu();
            }
        });
    }

    /**
     * Xử lý touch gesture
     */
    handleTouchGesture(startY, endY) {
        const diff = startY - endY;
        const threshold = 30;

        if (Math.abs(diff) > threshold) {
            if (diff > 0) {
                // Swipe up
                this.closeMenu();
            } else {
                // Swipe down
                this.openMenu();
            }
        }
    }

    /**
     * Mở menu
     */
    openMenu() {
        if (this.isMenuOpen) return;
        
        this.navMenu.classList.add('active');
        this.isMenuOpen = true;
        
        // Thêm class cho body để ngăn scroll
        document.body.classList.add('menu-open');
        
        // Focus vào menu đầu tiên
        const firstMenuItem = this.navMenu.querySelector('a');
        if (firstMenuItem) {
            firstMenuItem.focus();
        }
        
        console.log('Menu opened');
    }

    /**
     * Đóng menu
     */
    closeMenu() {
        if (!this.isMenuOpen) return;
        
        this.navMenu.classList.remove('active');
        this.isMenuOpen = false;
        
        // Xóa class từ body
        document.body.classList.remove('menu-open');
        
        // Focus về hamburger
        this.hamburger.focus();
        
        console.log('Menu closed');
    }

    /**
     * Toggle menu
     */
    toggleMenu() {
        if (this.isMenuOpen) {
            this.closeMenu();
        } else {
            this.openMenu();
        }
    }

    /**
     * Xử lý thay đổi breakpoint
     */
    handleBreakpointChange(breakpoint) {
        console.log('Breakpoint changed to:', breakpoint);
        
        switch (breakpoint) {
            case 'mobile':
                this.handleMobileBreakpoint();
                break;
            case 'tablet':
                this.handleTabletBreakpoint();
                break;
            case 'desktop':
                this.handleDesktopBreakpoint();
                break;
        }
    }

    /**
     * Xử lý breakpoint mobile
     */
    handleMobileBreakpoint() {
        // Đóng menu nếu đang mở
        if (this.isMenuOpen) {
            this.closeMenu();
        }
        
        // Thêm mobile-specific classes
        this.header.classList.add('mobile-view');
        this.header.classList.remove('tablet-view', 'desktop-view');
    }

    /**
     * Xử lý breakpoint tablet
     */
    handleTabletBreakpoint() {
        // Đóng menu nếu đang mở
        if (this.isMenuOpen) {
            this.closeMenu();
        }
        
        // Thêm tablet-specific classes
        this.header.classList.add('tablet-view');
        this.header.classList.remove('mobile-view', 'desktop-view');
    }

    /**
     * Xử lý breakpoint desktop
     */
    handleDesktopBreakpoint() {
        // Đóng menu nếu đang mở
        if (this.isMenuOpen) {
            this.closeMenu();
        }
        
        // Thêm desktop-specific classes
        this.header.classList.add('desktop-view');
        this.header.classList.remove('mobile-view', 'tablet-view');
    }

    /**
     * Lấy breakpoint hiện tại
     */
    getBreakpoint() {
        const width = window.innerWidth;
        
        if (width <= 768) {
            return 'mobile';
        } else if (width <= 1024) {
            return 'tablet';
        } else {
            return 'desktop';
        }
    }

    /**
     * Public methods để các trang khác có thể sử dụng
     */
    
    /**
     * Kiểm tra xem menu có đang mở không
     */
    isMenuOpen() {
        return this.isMenuOpen;
    }

    /**
     * Lấy breakpoint hiện tại
     */
    getCurrentBreakpoint() {
        return this.currentBreakpoint;
    }

    /**
     * Force đóng menu (dùng khi chuyển trang)
     */
    forceCloseMenu() {
        this.closeMenu();
    }

    /**
     * Thêm custom menu item
     */
    addCustomMenuItem(html, position = 'end') {
        const li = document.createElement('li');
        li.innerHTML = html;
        
        if (position === 'start') {
            this.navMenu.querySelector('ul').prepend(li);
        } else {
            this.navMenu.querySelector('ul').appendChild(li);
        }
        
        // Thêm event listener cho custom item
        const link = li.querySelector('a');
        if (link) {
            link.addEventListener('click', () => {
                if (this.currentBreakpoint === 'mobile') {
                    this.closeMenu();
                }
            });
        }
    }

    /**
     * Cập nhật active menu item
     */
    setActiveMenuItem(url) {
        const menuItems = this.navMenu.querySelectorAll('a');
        menuItems.forEach(item => {
            item.classList.remove('active');
            if (item.getAttribute('href') === url) {
                item.classList.add('active');
            }
        });
    }
}

// Khởi tạo header khi DOM ready
document.addEventListener('DOMContentLoaded', () => {
    // Tạo instance global để các trang khác có thể truy cập
    window.digiBookHeader = new DigiBookHeader();
    
    // Thêm CSS classes cho body dựa trên breakpoint
    const breakpoint = window.digiBookHeader.getCurrentBreakpoint();
    document.body.classList.add(`breakpoint-${breakpoint}`);
    
    console.log('DigiBook Header ready!');
});

// Export cho module systems (nếu cần)
if (typeof module !== 'undefined' && module.exports) {
    module.exports = DigiBookHeader;
}
