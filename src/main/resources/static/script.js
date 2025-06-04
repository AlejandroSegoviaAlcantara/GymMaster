document.addEventListener("DOMContentLoaded", showSlides);
function registro() {
    // Mostrar el formulario de registro
    document.getElementById("register-form").classList.remove("hidden");

    // Ocultar el formulario de login
    document.getElementById("login-form").classList.add("hidden");
}

/*function login() {
    document.getElementById("carrusel").setAttribute(hidden);
    document.getElementById("login").removeAttribute(hidden);
}*/
function login(event) {
        document.getElementById("carrusel").classList.add("hidden");
        document.getElementById("Login").classList.remove("hidden");
}


let slideIndex = 0;

function showSlides() {
    const slides = document.getElementsByClassName("carrusel-slide");
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) { slideIndex = 1; }
    slides[slideIndex - 1].style.display = "block";
    setTimeout(showSlides, 3000); // Cambia cada 3 segundos
}