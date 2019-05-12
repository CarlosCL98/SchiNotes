var apiGrupo = (function () {

    return {
        postGrupo: function (nombreUsuario, datosGrupo, callback) {
            var infoCompleta = callback(data);
            if (infoCompleta) {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/schinotes/usuarios/" + nombreUsuario + "/grupos",
                    data: JSON.stringify(datosGrupo)
                }).done(function () {
                    setTimeout(function () {
                        $("#modalCargandoHome").modal("hide");
                        $("#crearGrupoAlert").append("<div id='alertGrupoCreado' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>El grupo se creo exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertGrupoCreado").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                    setTimeout(function () {
                        $("#alertGrupoCreado").remove();
                        $("#modalCrearGrupo").modal("hide");
                    }, 2000);
                }).fail(function () {
                    setTimeout(function () {
                        $("#modalCargandoHome").modal("hide");
                        $("#crearGrupoAlert").append("<div id='alertFalloCrearGrupo' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>El grupo no se pudo crear. Intentelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertFalloCrearGrupo").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                    alert("El grupo no se pudo crear. Intentelo nuevamente.");
                });
            } else {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                    $("#crearGrupoAlert").append("<div id='alertGrupoInfoIncom' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe llenar todos los campos para crear un grupo.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertGrupoInfoIncom").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            }
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
                type: "DELETE",
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