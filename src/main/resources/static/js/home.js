/* global Cookies, apiUsuario, apiHorario */
var home = (function () {

    var actividadHorarioId = null;
    var actividadHorario = null;
    var actividadDia = null;
    var actividadHoraInicio = null;
    var actividadHoraFin = null;
    var activeHorario = null;

    var grupoHorarioId = null;
    var grupoHorarioNombre = null;
    var grupoNombre = null;
    var idGrupo = null;

    var mostrarHorario = function (horario) {
        $("#schedule").empty();
        var dias = horario.diasDeLaSemana;
        var horas = horario.horas;
        $("#schedule").append("<thead class='thead-dark'></thead><tbody></tbody>");
        $("#schedule").find("thead").append("<tr><th scope='col'>Horas/Días</th></tr>");
        for (var i = 0; i < dias.length; i++) {
            $("#schedule").find("thead > tr").append("<th id=" + dias[i].nombre + " scope='col'>" + dias[i].nombre + "</th>");
        }
        for (var i = 0; i < horas.length; i++) {
            $("#schedule").find("tbody").append("<tr></tr>");
            $("#schedule").find("tbody > tr:last").append("<th id=" + (horas[i].hora).substring(0, 5) + " scope='row'>" + (horas[i].hora).substring(0, 5) + "</th>");
            for (var j = 0; j < dias.length; j++) {
                $("#schedule").find("tbody > tr:last").append("<td id='" + (horas[i].hora).substring(0, 5) + "-" + dias[j].nombre + "'></td>");
            }
        }
        apiActividad.getActividadesByUsuario(Cookies.get('username'), horario.nombre, mostrarActividadesHorario);
    };

    var mostrarActividadesHorario = function (actividades) {
        for (var i = 0; i < actividades.length; i++) {
            $("#" + $.escapeSelector((actividades[i].hora_ini).substring(0, 5) + "-" + actividades[i].dia)).append("<div class='single-event drag t1'><a type='button' class='btn btn-sm btn-secondary btnNav' data-toggle='modal' data-target='#modalActividad' data-actividad-id=" + actividades[i].id + " data-horario-id=" + actividades[i].horario_id + "><em class='event-name'>" + actividades[i].nombre + "</em></a></div>");
        }
        crearModalActividades();
        appFunctions.hacerDraggable();
        $(".single-event").on("click", "a", function (event) {
            apiActividad.getActividadById(event.target.dataset.actividadId, mostrarDescripcionActividad);
        });
    };

    var crearModalActividades = function () {
        $("#mainContent").append('<div class="modal fade" id="modalActividad" tabindex="-1" role="dialog" aria-labelledby="modalLabelActividad" aria-hidden="true"><div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="modalLabelActividad">Actividad: </h5><button type="button" class="close col-md-2" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><form><div class="row"><div class="col-md-12"><span id="descripcion-actividad"></span></div><div class="col-md-12"><span id="fecha-creacion-actividad"></span></div><div class="col-md-12"><span id="hora-ini-actividad"></span></div><div class="col-md-12"><span id="hora-fin-actividad"></span></div></div><div class="single-event"><button id="buttonDeleteActividad" class="btn btn-secondary btnNav" type="button" data-actividad-id="" data-horario-id="" onClick="home.eliminarActividad(this)" class="close" data-dismiss="modal" aria-label="Eliminar actividad"><i class="fas fa-window-close"></i>&nbsp;Eliminiar Actividad</button></div></form></div><div class="modal-footer"><button class="btn btn-secondary btnNav" type="button" class="close" data-dismiss="modal" aria-label="Cerrar"><i class="fas fa-window-close"></i>&nbsp;Cerrar</button></div></div></div></div>');
        $(".single-event").on("click", "button", function (event) {
            apiActividad.getActividadById(event.target.dataset.actividadId, mostrarDescripcionActividad);
        });
    };


    var mostrarDescripcionActividad = function (actividad) {
        $("#modalLabelActividad").empty();
        $("#descripcion-actividad").empty();
        $("#fecha-creacion-actividad").empty();
        $("#hora-ini-actividad").empty();
        $("#hora-fin-actividad").empty();
        $("#modalLabelActividad").text("Actividad: " + actividad.nombre);
        $("#descripcion-actividad").append("<h5>Descripción</h5><p>" + actividad.descripcion + "</p>");
        $("#fecha-creacion-actividad").append("<h5>Fecha Creación</h5><p>" + actividad.fecha + "</p>");
        $("#hora-ini-actividad").append("<h5>Hora Inicio</h5><p>" + (actividad.hora_ini).substring(0, 5) + "</p>");
        $("#hora-fin-actividad").append("<h5>Hora Fin</h5><p>" + (actividad.hora_fin).substring(0, 5) + "</p>");
        $("#buttonDeleteActividad").attr("data-actividad-id", actividad.id);
        $("#buttonDeleteActividad").attr("data-horario-id", actividad.horario_id);
    };

    var agregarOpcionesHorarios = function (data) {
        $("#deployHorariosButton").empty();
        for (var i = 0; i < data.length; i++) {
            $("#deployHorariosButton").append("<a class='dropdown-item' href='#' data-horario-id=" + data[i].id + " data-horario-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event) {
            activeHorario = event.target.dataset.horarioNombre;
            cambiarHorario(event.target.dataset.horarioNombre);
            appStomp.init(event.target.dataset.horarioId);
        });
    };

    var cambiarHorario = function (nombre) {
        apiHorario.getHorarioByName(Cookies.get("username"), nombre, mostrarHorario);
    };

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
            apiHorario.getHorarioByName(Cookies.get("username"), nombreHorario, cambiarOpcionesCreacionActividad);
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
    };

    var agregaropcionesHorariosUnirseGrupo = function (data) {
        $("#opcionesHorarioUnirseGrupo").empty();
        for (var i = 0; i < data.length; i++) {
            $("#opcionesHorarioUnirseGrupo").append("<a class='dropdown-item' href='#' data-horario-id=" + data[i].id + " data-horario-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#opcionesHorarioUnirseGrupo").on("click", "a", function (event) {
            var nombreHorario = event.target.dataset.horarioNombre;
            var idHorario = event.target.dataset.horarioId;
            grupoHorarioNombre = nombreHorario;
            grupoHorarioId = idHorario;
            $("#labelHorarioParaGrupo").text("Horario seleccionado: " + nombreHorario);
        });
    };

    var agregarOpcionesGruposUnirseGrupo = function (data) {
        $("#opcionesGruposUnirseGrupo").empty();
        for (var i = 0; i < data.length; i++) {
            $("#opcionesGruposUnirseGrupo").append("<a class='dropdown-item' href='#' data-grupo-identificacion=" + data[i].identificacion + " data-grupo-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#opcionesGruposUnirseGrupo").on("click", "a", function (event) {
            nombreGrupo = event.target.dataset.grupoNombre;
            idGrupo = event.target.dataset.grupoIdentificacion;
            $("#labelGrupoParaGrupo").text("Grupo seleccionado: " + nombreGrupo);
        });
    };

    var verficarDatosCrearHorario = function (data) {
        var infoCompleta = true;
        if (data.nombre === "" || data.intervaloHoras === "" || data.numeroDias === "") {
            infoCompleta = false;
        }
        return infoCompleta;
    };

    var verficarDatosCrearActividad = function (data) {
        var infoCompleta = true;
        if (data.nombre === "" || data.descripcion === "" || data.horario_id === null || data.dia === null || data.hora_ini === null || data.hora_fin === null) {
            infoCompleta = false;
        }
        return infoCompleta;
    };

    var verficarDatosCrearGrupo = function (data) {
        var infoCompleta = true;
        if (data.nombre === "" || data.descripcion === "") {
            infoCompleta = false;
        }
        return infoCompleta;
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
        agregarHorario: function () {
            var data = {
                id: "1",
                nombre: $("#horarioNombre").val(),
                intervaloHoras: $("#intervaloHoras").val(),
                numeroDias: $("#horarioNumDias").val()
            }
            apiHorario.postHorario(data, Cookies.get("username"), verficarDatosCrearHorario);
        },
        consultarMisHorarios: function () {
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario, agregarOpcionesHorarios);
        },
        consultarMisHorariosParaActividades: function () {
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario, agregaropcionesHorariosActividades);
        },
        consultarMisHorariosParaElGrupo: function () {
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario, agregaropcionesHorariosUnirseGrupo);
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
            apiActividad.postActividad(data, Cookies.get('username'), data.horario_id, verficarDatosCrearActividad, appStomp.cambiarHorarioConActividades);
        },
        recargarHorario: function (horario) {
            cambiarHorario(horario.nombre);
        },
        crearGrupo: function () {
            data = {
                identificacion: "1",
                nombre: $("#inputGrupoNombre").val(),
                descripcion: $("#inputGrupoDescripcion").val()
            };
            apiGrupo.postGrupo(Cookies.get("username"), data, verficarDatosCrearGrupo);
        },
        consultarGrupos: function () {
            apiGrupo.getAllGrupos(agregarOpcionesGruposUnirseGrupo);
        },
        uniserAlGrupo: function () {
            apiGrupo.postAgregarIntegrante(Cookies.get("username"), grupoHorarioNombre, idGrupo);
        },
        agregarNotificaciones: function () {
            apiUsuario.getNotificaciones(Cookies.get("username"), mostrarNotificaciones);
        },
        eliminarActividad: function (data) {
            apiActividad.deleteActividad(data.dataset.horarioId, data.dataset.actividadId, activeHorario, cambiarHorario);
        }
    };

})();