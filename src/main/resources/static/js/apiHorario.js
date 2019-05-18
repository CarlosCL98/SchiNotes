var apiHorario = (function () {

    return {
        getHorarios: function (usuario, callback) {
            $.get("/schinotes/usuarios/" + usuario + "/horarios", function (data) {
                callback(data);
            });
        },
        getHorarioByName: function (usuario, nombreHorario, callback) {
            $.get("/schinotes/usuarios/" + usuario + "/horarios/" + nombreHorario, function (data) {
                callback(data);
            });
        },
        getHorarioById: function (usuario, idHorario, callback) {
            $.get("/schinotes/usuarios/" + usuario + "/horarios/id/" + idHorario, function (data) {
                callback(data);
            });
        },
        postHorario: function (data, correo, callback) {
            var infoCompleta = callback(data);
            if (infoCompleta) {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/schinotes/usuarios/" + correo + "/horarios",
                    data: JSON.stringify(data)
                }).done(function () {
                    setTimeout(function () {
                        $("#modalCargandoHome").modal("hide");
                        $("#crearHorarioAlert").append("<div id='alertHorarioCreado' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>El horario se creo exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertHorarioCreado").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                    setTimeout(function () {
                        $("#alertHorarioCreado").remove();
                        $("#modalCrearHorario").modal("hide");
                    }, 2500);
                }).fail(function () {
                    setTimeout(function () {
                        $("#modalCargandoHome").modal("hide");
                        $("#crearHorarioAlert").append("<div id='alertFalloCrearHorario' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>El horario no se pudo crear. Intentelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertFalloCrearHorario").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                });
            } else {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                    $("#crearHorarioAlert").append("<div id='alertHorInfoIncom' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe llenar todos los campos para crear un horario.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertHorInfoIncom").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            }            
        }
    };

})();
