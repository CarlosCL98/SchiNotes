/* global Cookies, apiUsuario, apiHorario */
var app = (function () {

    var actividadHorarioId = null;
    var actividadHorario = null;
    var actividadDia = null;
    var actividadHoraInicio = null;
    var actividadHoraFin = null;

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
        $("#tituloPerfil").text("Perfil de "+usuario.nombre);
        $("#nombrePerfil").text("" + usuario.nombre + " " + usuario.apellido + " ");
        if (usuario.intereses === null) {
            $("#interesesPerfil").text("Intereses: no se han descrito aún.");
        } else {
            $("#interesesPerfil").text("Intereses: "+usuario.intereses);
        }
        $("#nicknamePerfil").text("Nickname: "+usuario.cuentaCorreo.nickname);
        $("").text("correo: " + usuario.cuentaCorreo.correo)
        $("#perfilCard").append("<p style =\"font-size: 20px\"> nickname: " + usuario.cuentaCorreo.nickname + "</p>");
    };

    var mostrarHorario = function (horario) {
        console.log(horario);
        $("#schedule").empty();
        var dias = horario.diasDeLaSemana;
        var horas = horario.horas;
        $("#schedule").append("<div class='timeline'><ul></ul></div>");
        for (var i = 0; i < horas.length; i++) {
            $("#schedule").find("ul").append("<li><span>" + horas[i].hora + "</span></li>");
        }
        $("#schedule").append("<div class='events'><ul></ul></div>");
        for (var i = 0; i < dias.length; i++) {
            $("#schedule").find("ul:last").append("<li class='events-group' id="+dias[i].nombre+"><div class='top-info'><span>" + dias[i].nombre + "</span></div></li>");
        }
        
        apiHorario.getActividades(Cookies.get('username'),horario.nombre,mostrarActividadesHorario);
        
    };

    var mostrarActividadesHorario = function(actividades){
        
        for (var i = 0; i < actividades.length; i++){
            $("#"+actividades[i].dia).append("<ul></ul>");
            $("#"+actividades[i].dia).find("ul").append(" <li class='single-event' data-start="+actividades[i].hora_ini+" data-end="+actividades[i].hora_fin+" data-content='event-abs-circuit'data-event='event-1'><a href='#0'><em class='event-name'>"+actividades[i].nombre+"</em></a></li>");
        }
    };

    var creacionDeHorario = function () {
        alert("su horario ha sido creado exitosamanete");
    };

    var agregarOpcionesHorarios = function (data) {
        $("#deployHorariosButton").empty();
        for (var i = 0; i < data.length; i++) {
            $("#deployHorariosButton").append("<a class='dropdown-item' href='#' data-horarioId="+data[i].id+" data-horario=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event) {
            cambiarHorario(event.target.dataset.horario);
        });
    }

    var cambiarHorario = function (nombre) {
        apiHorario.getHorario(Cookies.get('username'), nombre, mostrarHorario);
    }

    var agregaropcionesHorariosActividades = function (data) {
        console.log(data);
        $("#opcionesHorarioCrearActividad").empty();
        for (var i = 0; i < data.length; i++) {
            $("#opcionesHorarioCrearActividad").append("<a class='dropdown-item' href='#' data-horario-id="+data[i].id+" data-horario-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#opcionesHorarioCrearActividad").on("click", "a", function (event) {
            var nombreHorario = event.target.dataset.horarioNombre;
            console.log(event.target.dataset.horarioId);
            var idHorario = event.target.dataset.horarioId;
            actividadHorarioId = idHorario;
            actividadHorario = nombreHorario;
            apiHorario.getHorario(Cookies.get('username'), nombreHorario, cambiarOpcionesCreacionActividad);
            $("#labelHorario").text("Horario seleccionado: "+nombreHorario);
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
            $("#labelDias").text("Día seleccionado: "+diaSemana);
        });
        $("#opcionesHorarioCrearActividadHorasInicio").empty();
        $("#opcionesHorarioCrearActividadHorasFin").empty();
        horasDelHorario = data.horas;
        for (var i = 0; i < horasDelHorario.length; i++) {
            $("#opcionesHorarioCrearActividadHorasInicio").append("<a class='dropdown-item' href='#' data-hora=" + horasDelHorario[i].hora + ">" + horasDelHorario[i].hora + "</a>");
            $("#opcionesHorarioCrearActividadHorasFin").append("<a class='dropdown-item' href='#' data-hora=" + horasDelHorario[i].hora + ">" + horasDelHorario[i].hora + "</a>");
        }
        $("#opcionesHorarioCrearActividadHorasInicio").on("click", "a", function (event) {
            var horaHorario = event.target.dataset.hora;
            actividadHoraInicio = horaHorario;
            $("#labelHorasIni").text("Hora inicial seleccionada: "+horaHorario);
        });
        $("#opcionesHorarioCrearActividadHorasFin").on("click", "a", function (event) {
            var horaHorario = event.target.dataset.hora;
            actividadHoraFin = horaHorario;
            $("#labelHorasFin").text("Hora final seleccionada: "+horaHorario);
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
            console.log(data)
            apiHorario.postActividad(data, Cookies.get('username'), data.horario_id);
        }
    };

})();