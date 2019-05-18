var perfil = (function () {

    var usuarioParaAgregar = null;
    var misAmigos = null;
    var activeUser = null;

    var mostrarPerfil = function (usuario) {
        activeUser = usuario;
        misAmigos = usuario.misAmigos;
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
        actualizarListaAmigos(usuario);
    };

    var actualizarListaAmigos = function (usuario) {
        activeUser = usuario;
        $("#listaAmigos").empty();
        $("#nombrePerfilAmigo").text("");
        $("#nicknamePerfilAmigo").text("");
        $("#interesesPerfilAmigo").text("");
        for (let i = 0; i < usuario.misAmigos.length; i++) {
            $("#listaAmigos").append("<a type='button' data-amigo-id='" + usuario.misAmigos[i].cuentaCorreo.nickname + "' class='list-group-item list-group-item-action'>" + usuario.misAmigos[i].cuentaCorreo.nickname + "</a>");
        }
        $(".single-event").on("click", "a", function (event) {
            actualizarPerfilAmigo(event.target.dataset.amigoId, actualizarPerfilAmigo);
        });
    }

    var actualizarPerfilAmigo = function (amigo) {
        for (let i = 0; i < misAmigos.length; i++) {
            if (misAmigos[i].cuentaCorreo.nickname === amigo) {
                $("#nombrePerfilAmigo").text(misAmigos[i].cuentaCorreo.correo);
                $("#nicknamePerfilAmigo").text(misAmigos[i].cuentaCorreo.nickname);
                if (misAmigos[i].intereses === null) {
                    $("#interesesPerfilAmigo").text("Intereses: no se han descrito aún.");
                } else {
                    $("#interesesPerfilAmigo").text("Intereses: " + misAmigos[i].intereses);
                }
                $("#botonEliminarAmigo").attr("data-amigo-id", misAmigos[i].identificacion);
            }
        }
    }

    var actualizarPerfilAmigo = function(amigo){
        console.log(misAmigos)
        for (let i = 0; i < misAmigos.length; i++) {
            if(misAmigos[i].cuentaCorreo.nickname === amigo){
                $("#nombrePerfilAmigo").text(misAmigos[i].cuentaCorreo.correo);
                $("#nicknamePerfilAmigo").text(misAmigos[i].cuentaCorreo.nickname);
                if (misAmigos[i].intereses === null) {
                    $("#interesesPerfilAmigo").text("Intereses: no se han descrito aún.");
                } else {
                    $("#interesesPerfilAmigo").text("Intereses: " + misAmigos[i].intereses);
                }
            }
        }
    }

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

    var agregarOpcionesGrupos = function (data) {
        $("#deployGruposButton").empty();
        for (var i = 0; i < data.length; i++) {
            $("#deployGruposButton").append("<a class='dropdown-item' href='homeGrupo.html' data-grupo-identificacion=" + data[i].identificacion + " data-grupo-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#deployGruposButton").on("click", "a", function (event) {
            Cookies.set('grupoId', event.target.dataset.grupoIdentificacion, { expires: 1, path: '/' });
            Cookies.set('grupoNombre', event.target.dataset.grupoNombre, { expires: 1, path: '/' });
        });
    };

    var mostrarNotificaciones = function (data) {
        $("#notificationNum").append(data.length);
        for (var i = 0; i < data.length; i++) {
            $("#notificationsList").append("<li class='icon' style='list-style-type: none'></li>");
            $("#notificationsList").find("li:last").append("<span class='icon'><i class='fa fa-user'></i></span>");
            $("#notificationsList").find("li:last").append("<span class='text'>&nbsp;" + data[i].descripcion + "</span><br/>");
            $("#notificationsList").find("li:last").append("<span class='text'>------------------------------------------------------------</span>");
        }
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
            var usuario = Cookies.get('username');
            apiGrupo.getGrupos(usuario, agregarOpcionesGrupos);
        },
        agregarNotificaciones: function () {
            apiUsuario.getNotificaciones(Cookies.get("username"), mostrarNotificaciones);
        },
        eliminarAmigo: function (data) {
            apiUsuario.deleteAmigo(Cookies.get("username"), data.dataset.amigoId, activeUser, actualizarListaAmigos);
        }
    };

})();