// Cargar datos del usuario
fetch("/api/usuario/sesion")
    .then(res => {
        if (!res.ok) {
            window.location.href = "login.html";
        }
        return res.json();
    })
    .then(usuario => {
        document.querySelector(".profile-info h3").textContent = `Usuario: ${usuario.nombre} ${usuario.apellido}`;
        document.querySelector(".profile-info").innerHTML += `
            <p>Peso: ${usuario.peso} kg</p>
            <p>Altura: ${usuario.altura} cm</p>
            <p>Correo: ${usuario.email}</p>
        `;
    });

// Cargar entrenamientos
function cargarEntrenamientos() {
    fetch("/api/usuario/entrenamientos")
        .then(res => {
            if (!res.ok) {
                window.location.href = "login.html";
            }
            return res.json();
        })
        .then(entrenamientos => {
            const tabla = document.querySelector("#tablaEntrenos tbody");
            tabla.innerHTML = "";

            entrenamientos.forEach(entreno => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${entreno.dia}</td>
                    <td>${entreno.ejercicio}</td>
                    <td>${entreno.repeticiones}</td>
                    <td>${entreno.peso} kg</td>
                    <td>
                        <button class="editar-btn">✏️</button>
                        <button class="eliminar-btn">🗑️</button>
                    </td>
                `;

                // Editar
                fila.querySelector(".editar-btn").addEventListener("click", () => {
                    document.getElementById("dia").value = entreno.dia;
                    document.getElementById("ejercicio").value = entreno.ejercicio;
                    document.getElementById("repeticiones").value = entreno.repeticiones;
                    document.getElementById("peso").value = entreno.peso;
                    document.getElementById("entrenoId").value = entreno.idEntrenamiento;
                });

                // Eliminar
                fila.querySelector(".eliminar-btn").addEventListener("click", () => {
                    if (confirm("¿Seguro que deseas eliminar este entrenamiento?")) {
                        fetch(`/api/usuario/entrenamientos/${entreno.idEntrenamiento}`, {
                            method: "DELETE"
                        }).then(res => {
                            if (res.ok) {
                                cargarEntrenamientos();
                            } else {
                                alert("Error al eliminar el entrenamiento.");
                            }
                        });
                    }
                });

                tabla.appendChild(fila);
            });
        });
}
cargarEntrenamientos();

// Crear / editar entrenamiento
document.getElementById("formEntreno").addEventListener("submit", e => {
    e.preventDefault();

    const entrenamiento = {
        dia: document.getElementById("dia").value,
        ejercicio: document.getElementById("ejercicio").value,
        repeticiones: parseInt(document.getElementById("repeticiones").value),
        peso: parseFloat(document.getElementById("peso").value)
    };

    const id = document.getElementById("entrenoId").value;
    const url = id ? `/api/usuario/entrenamientos/${id}` : "/api/usuario/entrenamientos";
    const method = id ? "PUT" : "POST";

    fetch(url, {
        method: method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(entrenamiento)
    }).then(res => {
        if (res.ok) {
            document.getElementById("formEntreno").reset();
            document.getElementById("entrenoId").value = "";
            cargarEntrenamientos();
        } else {
            alert("Error al guardar el entrenamiento.");
        }
    });
});
