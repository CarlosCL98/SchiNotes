var perfil = (function () {

    var usuarioParaAgregar = null;

    var mostrarPerfil = function (usuario) {
        $("#tituloPerfil").text("Perfil de " + usuario.nombre);
        $("#nombrePerfil").text("" + usuario.nombre + " " + usuario.apellido + " ");
        if (usuario.intereses === null) {
            $("#interesesPerfil").text("Intereses: no se han descrito aún.");
        } else {
            $("#interesesPerfil").text("Intereses: " + usuario.intereses);
        }
        $("#nicknamePerfil").text("Nickname: " + usuario.cuentaCorreo.nickname);
        $("").text("correo: " + usuario.cuentaCorreo.correo);
        $("#numeroDeAmigos").append(usuario.misAmigos.length);
        for (let i = 0; i < usuario.misAmigos.length; i++) {
            $("#listaAmigos").append("<button type='button' class='list-group-item list-group-item-action'>" + usuario.misAmigos[i].cuentaCorreo.nickname + "</button>");
        }
    };

    var refrescarBusquedaPersonas = function (param) {
        var usuarioCorreo;
        var usuarios = [];
        for (var i = 0; i < param.length; i++) {
            usuarioCorreo = param[i].cuentaCorreo.correo;
            usuario = { "correo": usuarioCorreo };
            usuarios.push(usuario);
        }
        actualizarTablaBusqueda(usuarios);
    };

    var actualizarTablaBusqueda = function (usuarios) {
        $("#tablaResultadoBusqueda").find('tbody').empty();
        if (usuarios.length === 0) {
            $("#tablaResultadoBusqueda").find('tbody').append("<tr><th colspan='5'>No existen usuarios</th></tr>");
        } else {
            for (var i = 0; i < usuarios.length; i++) {
                var usuario = usuarios[i];
                $("#tablaResultadoBusqueda").find('tbody').append('<tr class="clickable-row"><th scope="row" data-correo="' + usuario.correo + '">' + (i + 1) + '</th><td data-correo="' + usuario.correo + '">' + usuario.correo + '</td></tr>');
            }
            mantenerUsuario();
        }
    };

    var mantenerUsuario = function () {
        $('#tablaResultadoBusqueda').on('click', 'tbody tr', function (event) {
            $(this).addClass('highlight').siblings().removeClass('highlight');
            usuarioParaAgregar = event.target.dataset.correo;
        });
    };

    var agregarAmigoSeleccionado = function (data) {
        apiUsuario.postAmigo(Cookies.get('username'), data);
    };

    var mostrarGrupo = function () {

    };

    var agregarOpcionesGrupos = function (data) {
        $("#deployGruposButton").empty();
        for (var i = 0; i < data.length; i++) {
            $("#deployGruposButton").append("<a class='dropdown-item' href='#' data-grupo-id=" + data[i].identificacion + " data-grupo-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event) {
            mostrarGrupo(event.target.dataset.grupoId);
        });
    };

    return {
        consultarMiPerfil: function () {
            var correo = Cookies.get('username');
            apiUsuario.getUsuario(correo, mostrarPerfil);
        },
        buscarUsuarios: function () {
            var correo = $("#inputBuscarPersonas").val();
            apiUsuario.getUsuarioIncompleto(correo, refrescarBusquedaPersonas);
        },
        agregarAmigo: function () {
            apiUsuario.getUsuario(usuarioParaAgregar, agregarAmigoSeleccionado);
        },
        consultarMisGrupos: function () {
            apiGrupo.getGrupos(Cookies.get("username"), agregarOpcionesGrupos);
        }
    };

})();