document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");

    // Verificar rol
    fetch("/api/usuario/rol", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(res => {
            if (!res.ok) {
                window.location.href = "index.html";
                return;
            }
            return res.text();
        })
        .then(rol => {
            if (rol !== "ADMIN") {
                window.location.href = "index.html";
            }
        })
        .catch(error => {
            console.error("Error verificando rol:", error);
            window.location.href = "index.html";
        });

    // Cargar pistas al entrar
    cargarListaPistas();
});

// Mostrar/ocultar el formulario
function mostrarFormularioClase() {
    const form = document.getElementById("formCrearClase");
    form.classList.toggle("hidden");
}

// Crear nueva clase/pista
document.getElementById("formCrearClase").addEventListener("submit", function (e) {
    e.preventDefault();
    const nombre = document.getElementById("nombrePista").value;
    const token = localStorage.getItem("token");

    fetch("/api/pistas", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ nombre: nombre })
    }).then(res => {
        if (res.ok) {
            this.reset();
            cargarListaPistas();
        } else {
            alert("Error al crear la clase/pista");
        }
    });
});

// Mostrar lista de clases
function cargarListaPistas() {
    const token = localStorage.getItem("token");

    fetch("/api/pistas", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(res => res.json())
        .then(pistas => {
            const lista = document.getElementById("listaPistas");
            lista.innerHTML = "";

            pistas.forEach(p => {
                const li = document.createElement("li");
                li.textContent = p.nombre + " ";

                const btn = document.createElement("button");
                btn.textContent = "❌";
                btn.onclick = () => {
                    fetch(`/api/pistas/${p.id}`, {
                        method: "DELETE",
                        headers: {
                            "Authorization": "Bearer " + token
                        }
                    }).then(() => cargarListaPistas());
                };

                li.appendChild(btn);
                lista.appendChild(li);
            });
        });
}
