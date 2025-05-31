let carrito = [];

// Verificar sesión
fetch("/api/usuario/rol")
    .then(res => {
        if (!res.ok) window.location.href = "login.html";
        else cargarProductos();
    });

// Cargar productos desde backend
function cargarProductos() {
    fetch("/api/productos")
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

// Agregar al carrito
function agregarAlCarrito(id, nombre, precio) {
    const existente = carrito.find(p => p.id === id);
    if (existente) {
        existente.cantidad++;
    } else {
        carrito.push({ id, nombre, precio, cantidad: 1 });
    }
    mostrarCarrito();
}

// Mostrar carrito (puedes agregar esto como modal o parte visible)
function mostrarCarrito() {
    let resumen = "🛍 Carrito:\n";
    let total = 0;
    carrito.forEach(p => {
        resumen += `${p.nombre} x${p.cantidad} = ${(p.precio * p.cantidad).toFixed(2)} €\n`;
        total += p.precio * p.cantidad;
    });
    resumen += `\nTotal: ${total.toFixed(2)} €`;
    alert(resumen);
}
