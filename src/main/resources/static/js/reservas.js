// Verificar sesión
fetch("/api/usuario/sesion")
    .then(res => {
        if (!res.ok) window.location.href = "login.html";
        return res.json();
    })
    .then(() => {
        cargarPistas();
        cargarReservas();
    });

// Cargar opciones de pistas dinámicamente
function cargarPistas() {
    fetch("/api/pistas")
        .then(res => res.json())
        .then(pistas => {
            const select = document.getElementById("pista");
            select.innerHTML = `<option value="">--Selecciona una clase--</option>`;
            pistas.forEach(p => {
                const option = document.createElement("option");
                option.value = p.nombre;
                option.textContent = p.nombre;
                select.appendChild(option);
            });
        });
}

// Manejar formulario
document.querySelector(".reserva-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const reserva = {
        fecha: document.getElementById("fecha").value,
        hora: document.getElementById("hora").value,
        pista: document.getElementById("pista").value
    };

    fetch("/api/usuario/reservas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(reserva)
    })
    .then(res => {
        if (res.ok) {
            this.reset();
            cargarReservas();
        } else {
            alert("No se pudo registrar la reserva.");
        }
    });
});

// Mostrar reservas del usuario
function cargarReservas() {
    fetch("/api/usuario/reservas")
        .then(res => res.json())
        .then(reservas => {
            const lista = document.querySelector(".reservas-lista");
            lista.innerHTML = "";

            reservas.forEach(r => {
                const fecha = new Date(r.fecha).toLocaleDateString("es-ES");
                const hora = r.hora?.slice(0,5);
                const icono = getIcono(r.pista);

                const li = document.createElement("li");
                li.innerHTML = `🗓️ ${fecha} - 🕓 ${hora} - ${icono} ${r.pista}
                    <button class="eliminar-reserva">❌</button>`;

                li.querySelector(".eliminar-reserva").addEventListener("click", () => {
                    if (confirm("¿Eliminar esta reserva?")) {
                        fetch(`/api/usuario/reservas/${r.idReserva}`, {
                            method: "DELETE"
                        }).then(resp => {
                            if (resp.ok) cargarReservas();
                            else alert("Error al eliminar");
                        });
                    }
                });

                lista.appendChild(li);
            });
        });
}

// Icono por tipo de pista
function getIcono(pista) {
    switch (pista.toLowerCase()) {
        case "yoga": return "💪";
        case "spinning": return "🚴";
        case "crossfit": return "🏋️";
        default: return "📌";
    }
}
