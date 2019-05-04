var apiActividad = (function () {

    return {
        getActividadesByUsuario: function (usuario, horario, callback) {
            $.get("/schinotes/usuarios/" + usuario + "/horarios/" + horario + "/actividades", function (data) {
                callback(data);
            });
        },
        getActividadById: function (idActividad, callback) {
            $.get("/schinotes/actividades/" + idActividad, function (data) {
                callback(data);
            });
        },
        getActividadesByGrupo: function (grupoId, nombreHorario, callback) {
            $.get("/schinotes/grupos/" + grupoId + "/horarios/" + nombreHorario, function (data) {
                callback(data);
            });
        },
        postActividad: function (data, correo, idHorario) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/" + correo + "/horarios/" + idHorario + "/actividades",
                data: JSON.stringify(data)
            }).done(function () {
                alert("La Actividad se creo exitosamente");
            }).fail(function () {
                alert("La Actividad no se pudo crear. Intentelo nuevamente.");
            });
        }
    };

})();