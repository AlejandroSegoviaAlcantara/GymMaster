document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("login-form").addEventListener("submit", async function (e) {
        e.preventDefault();
        await iniciarSesion();
    });

    document.getElementById("register-submit").addEventListener("click", async function () {
        await registrarUsuario();
    });
});

function login(event) {
    event.preventDefault();
    document.getElementById("carrusel").classList.add("hidden");
    document.getElementById("Login").classList.remove("hidden");
}

function registro() {
    document.getElementById("register-form").classList.remove("hidden");
    document.getElementById("login-form").classList.add("hidden");
}

function mostrarLoginForm() {
    document.getElementById("register-form").classList.add("hidden");
    document.getElementById("login-form").classList.remove("hidden");
}

async function iniciarSesion() {
    const datos = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    const response = await fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(datos)
    });

    if (response.ok) {
        const result = await response.json();
        localStorage.setItem("token", result.token);
        console.log("Token recibido:", result.token);
        if (result.rol === "ADMIN") {
            window.location.href = "admin.html";
        } else {
            window.location.href = "perfil.html";
        }
    } else {
        alert("Credenciales incorrectas");
    }
}

async function registrarUsuario() {
    const datos = {
        email: document.getElementById("register-email").value,
        password: document.getElementById("register-password").value
    };

    const response = await fetch("/api/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(datos)
    });

    if (response.ok) {
        alert("Usuario registrado. Inicia sesión.");
        mostrarLoginForm();
    } else {
        alert("Error al registrar usuario.");
    }
}
