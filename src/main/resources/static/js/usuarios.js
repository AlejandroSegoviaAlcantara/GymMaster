$(document).ready(function () {

    cargarUsuarios();

    $('#usuarios').DataTable();

});


async function cargarUsuarios() {

    const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    ,
        headers: {
            ...}.headers,
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    const usuarios = await request.json();


    let listadoHtml ='';

    for (let usuario of usuarios) {
        let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"> \n <i class="fas fa-trash"></i></a>';
        let tipo = usuario.tipo == null ? '-' : usuario.tipo;
        let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>'
            + usuario.email+'</td><td>'+tipo
            + '</td><td>' + botonEliminar + '</td></tr>';
        listadoHtml += usuarioHtml;
    }

    console.log(usuarios);

    document.querySelector('#usuarios tbody').innerHTML = listadoHtml;

}

async function eliminarUsuario(id){
    if(!confirm('Desea Eliminar este usuario?')){
        return;
    }
    const request = await fetch('api/usuarios/'+id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    ,
    {
        ...},
        headers: {
            ...}.headers,
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    });

    cargarUsuarios();
}