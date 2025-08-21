// Simple enhancement: focus first field, show/hide password convenience
document.addEventListener('DOMContentLoaded', () => {
	const identifier = document.querySelector('#identifier');
	if (identifier) identifier.focus();
});

// Function to toggle password visibility
function togglePassword(inputId) {
    const input = document.getElementById(inputId);
    const eyeIcon = input.parentElement.querySelector('.eye-icon');
    const eyeOffIcon = input.parentElement.querySelector('.eye-off-icon');
    
    if (input.type === 'password') {
        input.type = 'text';
        eyeIcon.style.display = 'none';
        eyeOffIcon.style.display = 'block';
    } else {
        input.type = 'password';
        eyeIcon.style.display = 'block';
        eyeOffIcon.style.display = 'none';
    }
}

// Function to clear rememberMe cookie from client side
function clearRememberMeCookie() {
    document.cookie = "rememberMe=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    document.cookie = "rememberMe=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=" + window.location.pathname + ";";
}

