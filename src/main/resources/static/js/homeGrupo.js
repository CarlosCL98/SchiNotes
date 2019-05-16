var homeGrupo = (function () {
  var actividadHorarioId = null;
  var actividadHorario = null;
  var actividadDia = null;
  var actividadHoraInicio = null;
  var actividadHoraFin = null;

  var grupoHorarioId = null;
  var grupoHorarioNombre = null;
  var grupoNombre = null;
  var idGrupo = null;

  var mostrarHorario = function (horario) {
    grupoHorarioId = horario.id;
    grupoHorarioNombre = horario.nombre;
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
      $("#schedule")
        .find("tbody > tr:last")
        .append(
          "<th id=" +
          horas[i].hora.substring(0, 5) +
          " scope='row'>" +
          horas[i].hora.substring(0, 5) +
          "</th>"
        );
      for (var j = 0; j < dias.length; j++) {
        $("#schedule")
          .find("tbody > tr:last")
          .append(
            "<td id='" +
            horas[i].hora.substring(0, 5) +
            "-" +
            dias[j].nombre +
            "'></td>"
          );
      }
    }
    apiActividad.getActividadesByGrupo(
      Cookies.get("grupoId"),
      grupoHorarioNombre,
      mostrarActividadesHorario
    );
  };

  var mostrarActividadesHorario = function (actividades) {
    for (var i = 0; i < actividades.length; i++) {
      $(
        "#" +
        $.escapeSelector(
          actividades[i].hora_ini.substring(0, 5) + "-" + actividades[i].dia
        )
      ).append(
        "<div class='single-event drag t1'><a type='button' class='btn btn-sm btn-secondary btnNav' data-toggle='modal' data-target='#modalActividad' data-actividad-id=" +
        actividades[i].id +
        "><em class='event-name'>" +
        actividades[i].nombre +
        "</em></a></div>"
      );
    }
    crearModalActividades();
    appFunctions.hacerDraggable();
    $(".single-event").on("click", "a", function (event) {
      apiActividad.getActividadById(
        event.target.dataset.actividadId,
        mostrarDescripcionActividad
      );
    });
  };

  var cambiarHorario = function (id) {
    apiGrupo.getHorarioGrupo(id, mostrarHorario);
  };

  var cambiarOpcionesCreacionActividad = function (data) {
    $("#opcionesHorarioCrearActividadDias").empty();
    diasDeLaSemana = data.diasDeLaSemana;
    for (var i = 0; i < diasDeLaSemana.length; i++) {
      $("#opcionesHorarioCrearActividadDias").append(
        "<a class='dropdown-item' href='#' data-dia-semana=" +
        diasDeLaSemana[i].nombre +
        ">" +
        diasDeLaSemana[i].nombre +
        "</a>"
      );
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
      $("#opcionesHorarioCrearActividadHorasInicio").append(
        "<a class='dropdown-item' href='#' data-hora=" +
        horasDelHorario[i].hora.substring(0, 5) +
        ">" +
        horasDelHorario[i].hora.substring(0, 5) +
        "</a>"
      );
      $("#opcionesHorarioCrearActividadHorasFin").append(
        "<a class='dropdown-item' href='#' data-hora=" +
        horasDelHorario[i].hora.substring(0, 5) +
        ">" +
        horasDelHorario[i].hora.substring(0, 5) +
        "</a>"
      );
    }
    $("#opcionesHorarioCrearActividadHorasInicio").on("click", "a", function (
      event
    ) {
      var horaHorario = event.target.dataset.hora;
      actividadHoraInicio = horaHorario;
      $("#labelHorasIni").text("Hora inicial seleccionada: " + horaHorario);
    });
    $("#opcionesHorarioCrearActividadHorasFin").on("click", "a", function (
      event
    ) {
      var horaHorario = event.target.dataset.hora;
      actividadHoraFin = horaHorario;
      $("#labelHorasFin").text("Hora final seleccionada: " + horaHorario);
    });
  };

  var crearModalActividades = function () {
    $("#mainContent").append(
      '<div class="modal fade" id="modalActividad" tabindex="-1" role="dialog" aria-labelledby="modalLabelActividad" aria-hidden="true"><div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="modalLabelActividad">Actividad: </h5><button type="button" class="close col-md-2" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><form><div class="row"><div class="col-md-12"><span id="descripcion-actividad"></span></div><div class="col-md-12"><span id="fecha-creacion-actividad"></span></div><div class="col-md-12"><span id="hora-ini-actividad"></span></div><div class="col-md-12"><span id="hora-fin-actividad"></span></div></div></form></div><div class="modal-footer"><button class="btn btn-secondary btnNav" type="button" class="close" data-dismiss="modal" aria-label="Cerrar"><i class="fas fa-window-close"></i>&nbsp;Cerrar</button></div></div></div></div>'
    );
  };

  var mostrarDescripcionActividad = function (actividad) {
    $("#modalLabelActividad").empty();
    $("#descripcion-actividad").empty();
    $("#fecha-creacion-actividad").empty();
    $("#hora-ini-actividad").empty();
    $("#hora-fin-actividad").empty();
    $("#modalLabelActividad").text("Actividad: " + actividad.nombre);
    $("#descripcion-actividad").append(
      "<h5>Descripción</h5><p>" + actividad.descripcion + "</p>"
    );
    $("#fecha-creacion-actividad").append(
      "<h5>Fecha Creación</h5><p>" + actividad.fecha + "</p>"
    );
    $("#hora-ini-actividad").append(
      "<h5>Hora Inicio</h5><p>" + actividad.hora_ini.substring(0, 5) + "</p>"
    );
    $("#hora-fin-actividad").append(
      "<h5>Hora Fin</h5><p>" + actividad.hora_fin.substring(0, 5) + "</p>"
    );
  };

  var verficarDatosCrearActividadGrupo = function (data) {
    var infoCompleta = true;
    if (data.nombre === "" || data.descripcion === "" || data.horario_id === null || data.dia === null || data.hora_ini === null || data.hora_fin === null) {
      infoCompleta = false;
    }
    return infoCompleta;
  };

  var mostrarNotificaciones = function(data){
    console.log(data);
    $("#notificationNum").empty;
    $("#notificationsList").empty;
    $("#notificationNum").append(data.length);
    for (var i = 0; i < data.length; i++) {
        $("#notificationsList").append("<li class='icon'></li>");
        $("#notificationsList").find("li:last").append("<span class='icon'><i class='fa fa-user'></i></span>");
        $("#notificationsList").find("li:last").append("<span class='text'>"+data[i].descripcion+"</span>");
    }
  };

  return {
    initHorario: function () {
      apiGrupo.getHorarioGrupo(Cookies.get("grupoId"), mostrarHorario);
      appStomp.initGrupo(Cookies.get("grupoId"));
      appStomp.initNotificaciones(Cookies.get("grupoId"));
    },
    salirseDelGrupo: function () {
      apiGrupo.deleteIntegranteDeGrupo(
        Cookies.get("username"),
        Cookies.get("grupoId")
      );
    },
    agregarActividad: function () {
      var dataActividad = {
        id: "1",
        nombre: $("#actividadNombre").val(),
        descripcion: $("#actividadDescripcion").val(),
        fecha: new Date(),
        horario_id: actividadHorarioId,
        dia: actividadDia,
        hora_ini: actividadHoraInicio,
        hora_fin: actividadHoraFin
      };

      var dataNotificacion = {
        id: "1",
        descripcion: "Se ha creado la actividad'"+$("#actividadDescripcion").val()+"'en tu grupo "+Cookies.get("grupoNombre")
      };

      apiActividad.postActividadGrupo(dataActividad, dataActividad.horario_id, verficarDatosCrearActividadGrupo, appStomp.cambiarHorarioGrupoConActividades);
      apiUsuario.postNotificacion(Cookies.get("username"),dataNotificacion,appStomp.actualizarNotificaciones);
    },
    agregaropcionesHorariosActividades: function () {
      actividadHorarioId = grupoHorarioId;
      actividadHorario = grupoHorarioNombre;
      apiGrupo.getHorarioGrupo(
        Cookies.get("grupoId"),
        cambiarOpcionesCreacionActividad
      );
    },
    recargarHorario: function (horario) {
      cambiarHorario(Cookies.get("grupoId"));
    },
    agregarNotificaciones:function(){
      apiUsuario.getNotificaciones(Cookies.get("username"),mostrarNotificaciones);
    },
    actualizarNotificaciones:function(notificacion){
      apiUsuario.getNotificaciones(Cookies.get("username"),mostrarNotificaciones);
    }
  };
})();
