$(document).ready(function () {
// on ready
});


async function registrarUsuarios() {

    let datos = {};
    datos.nombre = document.getElementById("nombre").value;
    datos.apellido = document.getElementById("apellido").value;
    datos.email = document.getElementById("email").value;
    datos.password = document.getElementById("password").value;
    let repeat = document.getElementById("repeat").value;

    if (repeat != datos.password){
        alert("La contraseña es diferente");
        return;
    }

    const request = await fetch('api/usuarios', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
    const usuarios = await request.json();
}