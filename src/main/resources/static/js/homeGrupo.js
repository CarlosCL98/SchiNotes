var homeGrupo = (function () {

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

        for (var i = 0; i < horario.actividades.length; i++) {
            $("#" + horario.actividades[i].dia).append("<ul></ul>");
            $("#" + horario.actividades[i].dia).find("ul").append("<li class='single-event drag' data-start='" + (horario.actividades[i].hora_ini).substring(0, 5) + "' data-end='" + (horario.actividades[i].hora_fin).substring(0, 5) + "' data-content='event-actividades' data-event='event-" + (i + 1) + "'><a href='#0' data-actividad-id=" + horario.actividades[i].id + "><em class='event-name'>" + horario.actividades[i].nombre + "</em></a></li>");
        }
        crearModalActividades();
        $(".single-event").on("click", "a", function (event) {
            apiActividad.getActividadById(event.target.dataset.actividadId, mostrarDescripcionActividad);
        });

        console.log(horario)
        apiHorario.getActividades(Cookies.get('username'), horario.nombre, mostrarActividadesHorario);
        
    };

    var mostrarActividadesHorario = function (actividades) {
        console.log(actividades)
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

    return{
        mostrarHorario : function () {
            apiGrupo.getHorarioGrupo(Cookies.get('grupo'), mostrarHorario);
        }
    }
})();