document.addEventListener("DOMContentLoaded", () => {
    cargarPistas();       // <-- Añadir esto
    cargarReservas();

    document.querySelector(".reserva-form").addEventListener("submit", function (e) {
        e.preventDefault();
        crearReserva();
    });
});

function cargarReservas() {
    fetch("/api/usuario/reservas", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
        .then(res => {
            if (!res.ok) {
                window.location.href = "index.html";
            }
            return res.json();
        })
        .then(reservas => {
            const lista = document.querySelector(".reservas-lista");
            lista.innerHTML = "";

            reservas.forEach(reserva => {
                const li = document.createElement("li");
                const fecha = new Date(reserva.fecha).toLocaleDateString("es-ES");
                const hora = reserva.hora;
                li.textContent = `${fecha} - ${hora} - ${reserva.pista} `;

                // Botón eliminar
                const btnEliminar = document.createElement("button");
                btnEliminar.textContent = "❌";
                btnEliminar.style.marginLeft = "10px";
                btnEliminar.onclick = () => {
                    if (confirm("¿Seguro que quieres eliminar esta reserva?")) {
                        fetch(`/api/usuario/reservas/${reserva.idReserva}`, {
                            method: "DELETE",
                            headers: {
                                "Authorization": "Bearer " + localStorage.getItem("token")
                            }
                        }).then(res => {
                            if (res.ok) {
                                cargarReservas();
                            } else {
                                alert("Error al eliminar la reserva.");
                            }
                        });
                    }
                };

                li.appendChild(btnEliminar);
                lista.appendChild(li);
            });
        });
}

function crearReserva() {
    const fecha = document.getElementById("fecha").value;
    const hora = document.getElementById("hora").value;
    const pista = document.getElementById("pista").value;

    // Validar fecha en el pasado
    const ahora = new Date();
    const fechaSeleccionada = new Date(`${fecha}T${hora}`);
    if (fechaSeleccionada < ahora) {
        alert("No puedes hacer una reserva en el pasado.");
        return;
    }

    fetch("/api/usuario/reservas", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify({ fecha, hora, pista })
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
            return res.text();
        })
        .then(msg => {
            alert(msg);
            document.querySelector(".reserva-form").reset();
            cargarReservas();
        })
        .catch(err => {
            alert("Error: " + err.message);
        });
}
function cargarPistas() {
    fetch("/api/pistas")
        .then(res => {
            if (!res.ok) throw new Error("No se pudieron cargar las pistas");
            return res.json();
        })
        .then(pistas => {
            const select = document.getElementById("pista");
            select.innerHTML = '<option value="">--Selecciona pista--</option>'; // reset

            pistas.forEach(p => {
                const option = document.createElement("option");
                option.value = p.id;
                option.textContent = p.nombre;
                select.appendChild(option);
            });
        })
        .catch(err => {
            console.error("Error cargando pistas:", err);
            alert("Error al cargar las pistas disponibles.");
        });
}

