/* global Cookies, apiUsuario, apiHorario */
var home = (function () {

    var actividadHorarioId = null;
    var actividadHorario = null;
    var actividadDia = null;
    var actividadHoraInicio = null;
    var actividadHoraFin = null;

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
            $("#" + actividades[i].dia).find("ul").append("<li class='single-event drag' data-start='" + (actividades[i].hora_ini).substring(0, 5) + "' data-end='" + (actividades[i].hora_fin).substring(0, 5) + "' data-content='event-actividades' data-event='event-" + (i + 1) + "'><a href='#0' data-actividad-id=" + actividades[i].id + "><em class='event-name'>" + actividades[i].nombre + "</em></a></li>");
        }
        crearModalActividades();
        $(".single-event").on("click", "a", function (event) {
            apiActividad.getActividadById(event.target.dataset.actividadId, mostrarDescripcionActividad);
        });
    };

    var mostrarDescripcionActividad = function (actividad) {
        $("#descripcion-actividad").empty();
        $("#fecha-creacion-actividad").empty();
        $("#hora-ini-actividad").empty();
        $("#hora-fin-actividad").empty();
        $("#descripcion-actividad").append("<h5>Descripción</h5><p>" + actividad.descripcion + "</p>");
        $("#fecha-creacion-actividad").append("<h5>Fecha Creación</h5><p>" + actividad.fecha + "</p>");
        $("#hora-ini-actividad").append("<h5>Hora Inicio</h5><p>" + actividad.hora_ini + "</p>");
        $("#hora-fin-actividad").append("<h5>Hora Fin</h5><p>" + actividad.hora_fin + "</p>");
    }

    var crearModalActividades = function () {
        $("#schedule").append('<div class="event-modal" tabindex="-1">        <header class="header">          <div class="content"><span class="event-date"></span>            <h3 class="event-name"></h3>          </div>          <div class="header-bg"></div>        </header>        <div class="body">          <div class="event-info">            <div class="row">              <div class="col-md-12">                <span id="descripcion-actividad"></span>              </div>              <div class="col-md-12">                <span id="fecha-creacion-actividad"></span>              </div>              <div class="col-md-12">                <span id="hora-ini-actividad"></span>              </div>              <div class="col-md-12">                <span id="hora-fin-actividad"></span>              </div>            </div>          </div>          <div class="body-bg"></div>        </div>        <a href="#0" class="close"></a>      </div>      <div class="cover-layer"></div>');
        main.cargarHorario();
        appFunctions.draggable();
    };

    var agregarOpcionesHorarios = function (data) {
        $("#deployHorariosButton").empty();
        for (var i = 0; i < data.length; i++) {
            $("#deployHorariosButton").append("<a class='dropdown-item' href='#' data-horario-id=" + data[i].id + " data-horario-nombre=" + data[i].nombre + ">" + data[i].nombre + "</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event) {
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
    };

    return {        
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
            cambiarHorario(horario.nombre);
        },
        
        crearGrupo: function () {
            data = {
                identificacion: "1",
                nombre: $("#inputGrupoNombre").val(),
                descripcion: $("#inputGrupoDescripcion").val()
            };
            apiGrupo.postGrupo(Cookies.get("username"), data);
        }
    };

})();