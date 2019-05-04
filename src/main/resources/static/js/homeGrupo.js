var homeGrupo = (function () {

    var mostrarHorario = function (horario) {
        $("#schedule").empty();
        var dias = horario.diasDeLaSemana;
        var horas = horario.horas;
        $("#schedule").append("<thead></thead><tbody></tbody>");
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
        apiActividad.getActividadesByGrupo(Cookies.get('grupoId'), horario.nombre, mostrarActividadesHorario);
    };

    var mostrarActividadesHorario = function (actividades) {
        for (var i = 0; i < actividades.length; i++) {
            $("#" + $.escapeSelector((actividades[i].hora_ini).substring(0, 5) + "-" + actividades[i].dia)).append("<div class='single-event drag t1'><a type='button' data-toggle='modal' data-target='#modalActividad' data-actividad-id=" + actividades[i].id + "><em class='event-name'>" + actividades[i].nombre + "</em></a></div>");
        }
        crearModalActividades();
        appFunctions.hacerDraggable();
        $(".single-event").on("click", "a", function (event) {
            apiActividad.getActividadById(event.target.dataset.actividadId, mostrarDescripcionActividad);
        });
    };

    var crearModalActividades = function () {
        $("#mainContent").append('<div class="modal fade" id="modalActividad" tabindex="-1" role="dialog" aria-labelledby="modalLabelActividad" aria-hidden="true"><div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="modalLabelActividad">Actividad: </h5><button type="button" class="close col-md-2" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><form><div class="row"><div class="col-md-12"><span id="descripcion-actividad"></span></div><div class="col-md-12"><span id="fecha-creacion-actividad"></span></div><div class="col-md-12"><span id="hora-ini-actividad"></span></div><div class="col-md-12"><span id="hora-fin-actividad"></span></div></div></form></div><div class="modal-footer"><button class="btn btn-secondary btnNav" type="button" class="close" data-dismiss="modal" aria-label="Cerrar"><i class="fas fa-window-close"></i>&nbsp;Cerrar</button></div></div></div></div>');
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
    };

    return {
        mostrarHorario: function () {
            apiGrupo.getHorarioGrupo(Cookies.get('grupoId'), mostrarHorario);
        },
        salirseDelGrupo: function () {
            apiGrupo.deleteIntegranteDeGrupo(Cookies.get('username'), Cookies.get('grupoId'));
        }
    }
})();