var apiGrupo = (function () {

    return {
        postGrupo: function (nombreUsuario, datosGrupo) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/" + nombreUsuario + "/grupos",
                data: JSON.stringify(datosGrupo)
            }).done(function () {
                alert("El grupo se creo exitosamente");
            }).fail(function () {
                alert("El grupo no se pudo crear. Intentelo nuevamente.");
            });
        },
        getGrupos: function (correoUsuario, callback) {
            $.get("/schinotes/usuarios/" + correoUsuario + "/grupos", function (data) {
                callback(data);
            });
        },
        getAllGrupos: function (callback) {
            $.get("/schinotes/grupos", function (data) {
                callback(data);
            });
        },
        getHorarioGrupo: function (idGrupo, callback) {
            $.get("/schinotes/grupos/" + idGrupo + "/horarios", function (data) {
                callback(data)
            });
        },
        postAgregarIntegrante: function (usuarioCorreo, horarioNombre, idGrupo) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/grupos/" + idGrupo + "/integrantes/" + usuarioCorreo + "/horarios/" + horarioNombre,
            }).done(function () {
                alert("Se ha unido exitosamente al grupo.");
            }).fail(function () {
                alert("No fue posible unirse al grupo. Intentelo nuevamente.");
            });
        },
        deleteIntegranteDeGrupo: function (usuarioCorreo, idGrupo) {
            $.ajax({
                type: 'DELETE',
                url: "/schinotes/grupos/" + idGrupo + "/integrantes/" + usuarioCorreo,
            }).done(function () {
                alert("Ha sido eliminado con éxito.");
                $(location).attr("href", "../home.html");
            }).fail(function () {
                alert("No fue posible eliminarlo del grupo. Inténtelo nuevamente.");
            });
        }
    };

})();