var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    getUsuario().then(function () {
        
        $("#mi-perfil-btn").attr("href","profile.html?username=" + username);
        
        $("#user-saldo").html(user.saldo.toFixed(2) + "$");

        getPeliculas(false, "ASC");

        $("#ordenar-genero").click(ordenarPeliculas);
    });
});


async function getUsuario() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioPedir",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;
            } else {
                console.log("Error recuperando los datos del usuario");
            }
        }
    });

}
function getPeliculas(ordenar, orden) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletPeliculaListar",
        data: $.param({
            ordenar: ordenar,
            orden: orden
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                mostrarPeliculas(parsedResult);
            } else {
                console.log("Error recuperando los datos de las peliculas");
            }
        }
    });
}
function mostrarPeliculas(peliculas) {

    let contenido = "";

    $.each(peliculas, function (index, pelicula) {

        pelicula = JSON.parse(pelicula);
        let precio;

        if (pelicula.copias > 0) {

            if (user.premium) {

                if (pelicula.novedad) {
                    precio = (20000 - (20000 * 0.1));
                } else {
                    precio = (10000 - (10000 * 0.1));
                }
            } else {
                if (pelicula.novedad) {
                    precio = 20000;
                } else {
                    precio = 10000;
                }
            }

            contenido += '<tr><th scope="row">' + pelicula.id + '</th>' +
                    '<td>' + pelicula.titulo + '</td>' +
                    '<td>' + pelicula.genero + '</td>' +
                    '<td>' + pelicula.autor + '</td>' +
                    '<td>' + pelicula.copias + '</td>' +
                    '<td><input type="checkbox" name="novedad" id="novedad' + pelicula.id + '" disabled ';
            if (pelicula.novedad) {
                contenido += 'checked';
            }
            contenido += '></td>' +
                    '<td>' + precio + '</td>' +
                    '<td><button onclick="alquilarPelicula(' + pelicula.id + ',' + precio + ');" class="btn btn-success" ';
            if (user.saldo < precio) {
                contenido += ' disabled ';
            }

            contenido += '>Reservar</button></td></tr>'

        }
    });
    $("#peliculas-tbody").html(contenido);
    
}

function ordenarPeliculas() {

    if ($("#icono-ordenar").hasClass("fa-sort")) {
        getPeliculas(true, "ASC");
        $("#icono-ordenar").removeClass("fa-sort");
        $("#icono-ordenar").addClass("fa-sort-down");
    } else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
        getPeliculas(true, "DESC");
        $("#icono-ordenar").removeClass("fa-sort-down");
        $("#icono-ordenar").addClass("fa-sort-up");
    } else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
        getPeliculas(false, "ASC");
        $("#icono-ordenar").removeClass("fa-sort-up");
        $("#icono-ordenar").addClass("fa-sort");
    }
    
    
}

function alquilarPelicula(id, precio){
    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletPeliculaAlquilar",
        data: $.param({
            id: id,
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                restarDinero(precio).then(function (){
                    location.reload();
                });
            } else {
                console.log("Error en la reserva de la pelicula");
            }
        }
    });
}

async function restarDinero(precio){
    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioRestarDinero",
        data: $.param({
            username: username,
            saldo: parseFloat(user.saldo - precio)
            
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                console.log("Saldo actualizado")
            } else {
                console.log("Error en el proceso de pago");
            }
        }
    });
}