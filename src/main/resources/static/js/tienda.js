let carrito = [];

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "index.html";
        return;
    }

    fetch("/api/usuario/rol", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("No autorizado");
            return res.text();
        })
        .then(() => cargarProductos())
        .catch(() => {
            alert("Sesión no válida");
            window.location.href = "index.html";
        });

    document.getElementById("btnGuardarCompra").addEventListener("click", guardarCompra);
});

function cargarProductos() {
    fetch("/api/productos", {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
        .then(res => res.json())
        .then(productos => {
            const contenedor = document.querySelector(".productos");
            contenedor.innerHTML = "";

            productos.forEach(prod => {
                const div = document.createElement("div");
                div.classList.add("producto");
                div.innerHTML = `
                    <img src="${prod.imagen}" alt="${prod.nombre}" />
                    <h3>${prod.nombre}</h3>
                    <p>${prod.descripcion}</p>
                    <p><strong>${prod.precio.toFixed(2)} €</strong></p>
                    <button onclick="agregarAlCarrito(${prod.id}, '${prod.nombre}', ${prod.precio})">Agregar al carrito 🛒</button>
                `;
                contenedor.appendChild(div);
            });
        });
}

function agregarAlCarrito(id, nombre, precio) {
    const existente = carrito.find(p => p.id === id);
    if (existente) {
        existente.cantidad++;
    } else {
        carrito.push({ id, nombre, precio, cantidad: 1 });
    }
    actualizarCarritoUI();
}

function actualizarCarritoUI() {
    const lista = document.getElementById("carrito-lista");
    lista.innerHTML = "";

    carrito.forEach(p => {
        const li = document.createElement("li");
        li.textContent = `${p.nombre} x${p.cantidad} - ${(p.precio * p.cantidad).toFixed(2)} €`;
        lista.appendChild(li);
    });

    const btn = document.getElementById("btnGuardarCompra");
    btn.style.display = carrito.length > 0 ? "inline-block" : "none";
}

function guardarCompra() {
    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const compra = {
        productos: carrito.map(p => ({
            productoId: p.id,
            nombre: p.nombre,
            precio: p.precio,
            cantidad: p.cantidad
        }))
    };

    fetch("/api/compras", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(compra)
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
            carrito = [];
            actualizarCarritoUI();
            alert("¡Compra realizada con éxito!");
        })
        .catch(err => alert("Error al guardar la compra: " + err.message));
}
function cargarHistorialCompras() {
    const token = localStorage.getItem("token");

    fetch("/api/compras", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Error al obtener el historial");
            return res.json();
        })
        .then(compras => {
            const contenedor = document.getElementById("historial-compras");
            contenedor.innerHTML = "";

            if (compras.length === 0) {
                contenedor.textContent = "No has realizado ninguna compra aún.";
                return;
            }

            compras.forEach(compra => {
                const div = document.createElement("div");
                div.classList.add("compra-historial");

                let productosHTML = compra.productos.map(p => {
                    return `<li>${p.nombre} x${p.cantidad} - ${(p.precio * p.cantidad).toFixed(2)} €</li>`;
                }).join("");

                div.innerHTML = `
                <p><strong>Fecha:</strong> ${new Date(compra.fecha).toLocaleString()}</p>
                <p><strong>Total:</strong> ${compra.total.toFixed(2)} €</p>
                <ul>${productosHTML}</ul>
                <hr/>
            `;

                contenedor.appendChild(div);
            });
        })
        .catch(err => console.error("Error mostrando historial:", err));
}
