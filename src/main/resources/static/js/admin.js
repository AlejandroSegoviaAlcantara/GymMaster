document.addEventListener("DOMContentLoaded", () => {
    fetch("/api/usuario/rol")
        .then(res => {
            if (!res.ok) {
                // Si no está autenticado
                window.location.href = "login.html";
                return;
            }
            return res.text();
        })
        .then(rol => {
            if (rol !== "ADMIN") {
                // Redirige a otra página si no es admin
                window.location.href = "index.html";
            }
        })
        .catch(error => {
            console.error("Error verificando rol:", error);
            window.location.href = "login.html";
        });
});
document.getElementById("formPista").addEventListener("submit", function (e) {
    e.preventDefault();
    const nombre = document.getElementById("nombrePista").value;

    fetch("/api/pistas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre })
    }).then(res => {
        if (res.ok) {
            this.reset();
            cargarListaPistas();
        } else {
            alert("Error al crear la pista");
        }
    });
});

function cargarListaPistas() {
    fetch("/api/pistas")
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
                    fetch(`/api/pistas/${p.id}`, { method: "DELETE" })
                        .then(() => cargarListaPistas());
                };
                li.appendChild(btn);
                lista.appendChild(li);
            });
        });
}

cargarListaPistas();
