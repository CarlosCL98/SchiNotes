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
        postActividad: function (data, correo, idHorario, callback, callback2) {
            var infoCompleta = callback(data);
            if (infoCompleta) {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/schinotes/usuarios/" + correo + "/horarios/" + idHorario + "/actividades",
                    data: JSON.stringify(data)
                }).done(function () {
                    setTimeout(function () {
                        $("#modalCargandoHome").modal("hide");
                        $("#crearActividadAlert").append("<div id='alertActividadCreada' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>La Actividad se creo exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertActividadCreada").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                    setTimeout(function () {
                        $("#alertActividadCreada").remove();
                        $("#modalCrearActividad").modal("hide");
                        callback2(data);
                    }, 2000);
                }).fail(function () {
                    setTimeout(function () {
                        $("#modalCargandoHome").modal("hide");
                        $("#crearActividadAlert").append("<div id='alertFalloCrearActividad' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>La Actividad no se pudo crear. Intentelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertFalloCrearActividad").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                });
            } else {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                    $("#crearActividadAlert").append("<div id='alertActInfoIncom' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe llenar todos los campos para crear una actividad.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertActInfoIncom").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            }
        },
        postActividadGrupo: function (data, idHorario, callback, callback2) {
            console.log(data);
            var infoCompleta = callback(data);
            if (infoCompleta) {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/schinotes/grupos/horarios/" + idHorario + "/actividades",
                    data: JSON.stringify(data)
                }).done(function () {
                    setTimeout(function () {
                        $("#modalCargandoHomeGrupo").modal("hide");
                        $("#crearActividadGrupoAlert").append("<div id='alertActividadGrupoCreada' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>La Actividad se creo exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertActividadGrupoCreada").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                    setTimeout(function () {
                        $("#alertActividadGrupoCreada").remove();
                        $("#modalCrearActividadGrupo").modal("hide");
                        callback2(data);
                    }, 2000);
                }).fail(function () {
                    setTimeout(function () {
                        $("#modalCargandoHomeGrupo").modal("hide");
                        $("#crearActividadGrupoAlert").append("<div id='alertFalloCrearActividadGrupo' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>La Actividad no se pudo crear. Intentelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertFalloCrearActividadGrupo").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                });
            } else {
                setTimeout(function () {
                    $("#modalCargandoHomeGrupo").modal("hide");
                    $("#crearActividadGrupoAlert").append("<div id='alertActGrInfoIncom' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe llenar todos los campos para crear una actividad.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertActGrInfoIncom").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            }
        },
        deleteActividad: function (idHorario, idActividad, nombreHorario, callback) {
            $.ajax({
                url: "/schinotes/horarios/" + idHorario + "/actividades/" + idActividad,
                type: 'DELETE',
            }).done(function () {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                    $("#eliminarActividadAlert").append("<div id='alertEliminarActividad' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>La Actividad se eliminó exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertEliminarActividad").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
                setTimeout(function () {
                    $("#alertEliminarActividad").remove();
                    $("#modalActividad").modal("hide");
                    callback2(data);
                }, 2000);
            }).fail(function () {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                    $("#eliminarActividadAlert").append("<div id='alertNoEliminarActividad' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>La Actividad no se eliminó. Inténtelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertNoEliminarActividad").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            });
            callback(nombreHorario);
        }
    };

})();