var apiUsuario = (function () {

    return {
        postUsuario: function (data, callback, callback2) {
            var infoCompleta = callback(data);
            if (infoCompleta) {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/schinotes/usuarios/registrar",
                    data: JSON.stringify(data)
                }).done(function () {
                    setTimeout(function () {
                        $('#modalCargandoRegistrar').modal('hide');
                        $("#registerAlert1").append("<div id='alertEnvioCorreo' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>Usuario registrado exitosamente. Se le enviará un correo de confirmación a " + data.cuentaCorreo.correo + "<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $('#alertEnvioCorreo').on('close.bs.alert', function () {
                            $(this).remove();
                        });
                    }, 500);
                    setTimeout(function () {
                        $(location).attr("href", "../comprobarCuenta.html");
                    }, 2500);
                }).fail(function (fallo) {
                    setTimeout(function () {
                        $('#modalCargandoRegistrar').modal('hide');
                        $("#registerAlert1").append("<div id='alertFalloRegistro' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>El usuario no pudo ser creado. El nickname o correo ya existe.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $('#alertFalloRegistro').on('close.bs.alert', function () {
                            $(this).remove();
                        });
                    }, 500);
                });
            } else {
                setTimeout(function () {
                    $('#modalCargandoRegistrar').modal('hide');
                    $("#registerAlert1").append("<div id='alertInfoIncom' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe ingresar todos los campos para continuar con el registro.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $('#alertInfoIncom').on('close.bs.alert', function () {
                        $(this).remove();
                    });
                }, 500);
            }
            callback2();
        },
        getUsuario: function (correo, callback) {
            $.get("/schinotes/usuarios/" + correo, function (data) {
                callback(data);
            });
        },
        getCuenta: function (correo, callback) {
            $.get("/schinotes/cuentas/" + correo, function (data) {
                callback(data);
            }).fail(function (fallo) {
                setTimeout(function () {
                    $('#modalCargandoLogin').modal('hide');
                    $("#loginAlert1").append("<div id='alertVacioU2' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>" + fallo.responseJSON.message + "<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $('#alertVacioU2').on('close.bs.alert', function () {
                        $(this).remove();
                    });
                }, 500);
            });
        },
        getUsuarioIncompleto: function (emailFragment, callback) {
            $.get("/schinotes/usuarios/busqueda/" + emailFragment, function (data) {
                callback(data);
            });
        },
        postAmigo: function (correo, data) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/" + correo + "/amigos",
                data: JSON.stringify(data)
            }).done(function () {
                alert("Amigo añadido exitosamente");
            }).fail(function () {
                alert("El amigo no pudo ser añadido. Inténtelo nuevamente.");
            });
        },
        getCodigoComprobacion: function (correoUsuario, codigo, callback) {
            $.get("/schinotes/usuarios/" + correoUsuario + "/comprobar", function (data) {
                callback(codigo, data);
            });
        },
        usuarioVerificado: function (correo) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/" + correo + "/verificado"
            });
        },
        cuentaYaVerificada: function (correo, callback) {
            $.get("/schinotes/usuarios/" + correo + "/cuentas/verificar", function (data) {
                callback(data);
            });
        }
    };

})();