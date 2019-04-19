/* global Cookies, apiUsuario, apiHorario */
var app = (function () {

    var actividadHorarioId = null;
    var actividadHorario = null;
    var actividadDia = null;
    var actividadHoraInicio = null;
    var actividadHoraFin = null;

    var usuarioParaAgregar = null;

    var verificarUsuario = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (cuenta.contrasena === contrasena) {
            Cookies.set('username', cuenta.correo, { expires: 1, path: '/' });
            $(location).attr("href", "../home.html");
        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var mostrarPerfil = function (usuario) {
        $("#tituloPerfil").text("Perfil de " + usuario.nombre);
        $("#nombrePerfil").text("" + usuario.nombre + " " + usuario.apellido + " ");
        if (usuario.intereses === null) {
            $("#interesesPerfil").text("Intereses: no se han descrito aún.");
        } else {
            $("#interesesPerfil").text("Intereses: " + usuario.intereses);
        }
        $("#nicknamePerfil").text("Nickname: " + usuario.cuentaCorreo.nickname);
        $("").text("correo: " + usuario.cuentaCorreo.correo)
        console.log(usuario);
        $("#numeroDeAmigos").append(usuario.misAmigos.length);
        for (let i = 0; i < usuario.misAmigos.length; i++) {
            $("#listaAmigos").append("<button type='button' class='list-group-item list-group-item-action'>"+usuario.misAmigos[i].cuentaCorreo.nickname+"</button>");
        }  

    };

    var mostrarHorario = function (horario) {
        $("#schedule").remove();
        $("#mainContent").append('<div id="schedule" class="cd-schedule loading"></div>');
        var dias = horario.diasDeLaSemana;
        var horas = horario.horas;
        $("#schedule").append("<div id='timelineHorario' class='timeline'><ul></ul></div>");
        for (var i = 0; i < horas.length; i++) {
            $("#timelineHorario").find("ul").append("<li><span>" + (horas[i].hora).substring(0, 5) + "</span></li>");
        }
        $("#schedule").append("<div id='eventosHorario' class='events'><ul></ul></div>");
        for (var i = 0; i < dias.length; i++) {
            $("#eventosHorario").find("ul").append("<li class='events-group' id=" + dias[i].nombre + "><div class='top-info'><span>" + dias[i].nombre + "</span></div></li>");
        }
        apiHorario.getActividades(Cookies.get('username'), horario.nombre, mostrarActividadesHorario);
    };

    var mostrarActividadesHorario = function (actividades) {
        for (var i = 0; i < actividades.length; i++) {
            $("#" + actividades[i].dia).append("<ul></ul>");
            $("#" + actividades[i].dia).find("ul").append("<li class='single-event drag' data-start='" + (actividades[i].hora_ini).substring(0, 5) + "' data-end='" + (actividades[i].hora_fin).substring(0, 5) + "' data-content='event-" + (actividades[i].nombre).toLowerCase() + "' data-event='event-" + (i + 1) + "'><a href='#0'><em class='event-name'>" + actividades[i].nombre + "</em></a></li>");
        }
        crearModalActividades();
    };

    var crearModalActividades = function () {
        $("#schedule").append('<div class="event-modal" tabindex="-1"><header class="header"><div class="content"><span class="event-date"></span><h3 class="event-name"></h3></div><div class="header-bg"></div></header><div class="body"><div class="event-info"></div><div class="body-bg"></div></div><a href="#0" class="close">Close</a></div><div class="cover-layer"></div>');
        main.cargarHorario();
        appFunctions.draggable();
    };

    var agregarOpcionesHorarios = function (data) {
        $("#deployHorariosButton").empty();
        for (var i = 0; i < data.length; i++) {
            $("#deployHorariosButton").append("<a class='dropdown-item' href='#' data-horario-id=" + data[i].id + " data-horario-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event) {
            //document.location.reload();
            cambiarHorario(event.target.dataset.horarioNombre);
            appStomp.init(event.target.dataset.horarioId);
        });
    };

    var cambiarHorario = function (nombre) {
        apiHorario.getHorarioByName(Cookies.get('username'), nombre, mostrarHorario);
    }

    var agregaropcionesHorariosActividades = function (data) {
        $("#opcionesHorarioCrearActividad").empty();
        for (var i = 0; i < data.length; i++) {
            $("#opcionesHorarioCrearActividad").append("<a class='dropdown-item' href='#' data-horario-id=" + data[i].id + " data-horario-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#opcionesHorarioCrearActividad").on("click", "a", function (event) {
            var nombreHorario = event.target.dataset.horarioNombre;
            var idHorario = event.target.dataset.horarioId;
            actividadHorarioId = idHorario;
            actividadHorario = nombreHorario;
            apiHorario.getHorarioByName(Cookies.get('username'), nombreHorario, cambiarOpcionesCreacionActividad);
            $("#labelHorario").text("Horario seleccionado: " + nombreHorario);
        });
    };

    var cambiarOpcionesCreacionActividad = function (data) {
        $("#opcionesHorarioCrearActividadDias").empty();
        diasDeLaSemana = data.diasDeLaSemana;
        for (var i = 0; i < diasDeLaSemana.length; i++) {
            $("#opcionesHorarioCrearActividadDias").append("<a class='dropdown-item' href='#' data-dia-semana=" + diasDeLaSemana[i].nombre + ">" + diasDeLaSemana[i].nombre + "</a>");
        }
        $("#opcionesHorarioCrearActividadDias").on("click", "a", function (event) {
            var diaSemana = event.target.dataset.diaSemana;
            actividadDia = diaSemana;
            $("#labelDias").text("Día seleccionado: " + diaSemana);
        });
        $("#opcionesHorarioCrearActividadHorasInicio").empty();
        $("#opcionesHorarioCrearActividadHorasFin").empty();
        horasDelHorario = data.horas;
        for (var i = 0; i < horasDelHorario.length; i++) {
            $("#opcionesHorarioCrearActividadHorasInicio").append("<a class='dropdown-item' href='#' data-hora=" + (horasDelHorario[i].hora).substring(0, 5) + ">" + (horasDelHorario[i].hora).substring(0, 5) + "</a>");
            $("#opcionesHorarioCrearActividadHorasFin").append("<a class='dropdown-item' href='#' data-hora=" + (horasDelHorario[i].hora).substring(0, 5) + ">" + (horasDelHorario[i].hora).substring(0, 5) + "</a>");
        }
        $("#opcionesHorarioCrearActividadHorasInicio").on("click", "a", function (event) {
            var horaHorario = event.target.dataset.hora;
            actividadHoraInicio = horaHorario;
            $("#labelHorasIni").text("Hora inicial seleccionada: " + horaHorario);
        });
        $("#opcionesHorarioCrearActividadHorasFin").on("click", "a", function (event) {
            var horaHorario = event.target.dataset.hora;
            actividadHoraFin = horaHorario;
            $("#labelHorasFin").text("Hora final seleccionada: " + horaHorario);
        });
    }

    var verificarDatos = function (data) {
        var infoCompleta = true;
        if (data.nombre === "" || data.apellido === "" || data.cuentaCorreo.correo === "" || data.cuentaCorreo.contrasena === "" || data.cuentaCorreo.nickname === "") {
            infoCompleta = false;
        }
        return infoCompleta;
    };

    var borrarDatos = function () {
        $('#nombreInput').val('');
        $('#apellidoInput').val('');
        $('#nicknameInput').val('');
        $('#emailInput').val('');
        $('#passwordInput').val('');
        $('#passwordConfirmarInput').val('');
    }

    var refrescarBusquedaPersonas = function (param) {
        var usuarioCorreo;
        var usuarios = [];
        console.log(param.length);
        for (var i = 0; i < param.length; i++) {
            usuarioCorreo = param[i].cuentaCorreo.correo;
            usuario = { "correo": usuarioCorreo };
            usuarios.push(usuario);
        }
        actualizarTablaBusqueda(usuarios);
    };

    var actualizarTablaBusqueda = function (usuarios) {
        $("#tablaResultadoBusqueda").find('tbody').empty();       
        console.log(usuarios.length);
        if(usuarios.length === 0){            
            $("#tablaResultadoBusqueda").find('tbody').append("<tr><th colspan='5'>No existen usuarios</th></tr>");
        }else{
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
            var rowSelected = true;
            usuarioParaAgregar = event.target.dataset.correo;
        });
    };

    var agregarAmigoSeleccionado = function(data){
        apiUsuario.postAmigo(Cookies.get('username'),data,confirmarAgregarAmigo);
    };

    var confirmarAgregarAmigo = function(data){
        alert("se ingresooo el amigooo");
    };

    return {
        agregarUsuario: function () {
            if ($('#passwordConfirmarInput').val() !== $('#passwordInput').val()) {
                alert("Las contraseñas no coinciden, por favor vuelva a ingresarlas.")
            } else {
                var data = {
                    nombre: $('#nombreInput').val(),
                    apellido: $('#apellidoInput').val(),
                    cuentaCorreo: {
                        correo: $('#emailInput').val(),
                        contrasena: $('#passwordInput').val(),
                        nickname: $('#nicknameInput').val()
                    }
                };
                apiUsuario.postUsuario(data, verificarDatos, borrarDatos);
            }
        },
        autenticarUsuario: function () {
            var correo = $("#UsuarioInput").val();
            apiUsuario.getCuenta(correo, verificarUsuario);
        },
        consultarPerfil: function () {
            var correo = Cookies.get('username');
            apiUsuario.getUsuario(correo, mostrarPerfil);
        },
        verificarDatos: function (data) {
            var infoCompleta = true;
            if (data.nombre === "" || data.apellido === "" || data.cuentaCorreo.correo === "" || data.cuentaCorreo.contrasena === "" || data.cuentaCorreo.nickname === "") {
                infoCompleta = false;
            }
            return infoCompleta;
        },
        agregarHorario: function () {
            var data = {
                id: "1",
                nombre: $('#horarioNombre').val(),
                intervaloHoras: $('#intervaloHoras').val(),
                numeroDias: $('#horarioNumDias').val()
            }
            apiHorario.postHorario(data, Cookies.get('username'));
        },
        consultarMisHorarios: function () {
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario, agregarOpcionesHorarios);
        },
        consultarMisHorariosParaActividades: function () {
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario, agregaropcionesHorariosActividades);
        },
        agregarActividad: function () {
            var data = {
                id: "1",
                nombre: $('#actividadNombre').val(),
                descripcion: $('#actividadDescripcion').val(),
                fecha: new Date(),
                horario_id: actividadHorarioId,
                dia: actividadDia,
                hora_ini: actividadHoraInicio,
                hora_fin: actividadHoraFin
            }
            apiHorario.postActividad(data, Cookies.get('username'), data.horario_id);
            //cambiarHorario(actividadHorario);
            appStomp.cambiarHorarioConActividades(data);
        },
        recargarHorario: function (horario) {
            console.log(horario);
            cambiarHorario(horario.nombre);
        },
        buscarUsuarios: function () {
            var cinema = $("#inputBuscarPersonas").val();
            apiUsuario.getUsuarioIncompleto(cinema, refrescarBusquedaPersonas);
        },
        agregarAmigo: function () {
            apiUsuario.getUsuario(usuarioParaAgregar,agregarAmigoSeleccionado);
        },
        crearGrupo: function () {
            data = {
                identificacion: "1",
                nombre: $("#inputGrupoNombre").val(),
                descripcion: $("#inputGrupoDescripcion").val()
            };
            apiGrupo.postGrupo(Cookies.get("username"),data);
        }
    };

})();