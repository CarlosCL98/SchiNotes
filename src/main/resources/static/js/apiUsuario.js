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
                        $("#modalCargandoRegistrar").modal("hide");
                        $("#registerAlert1").append("<div id='alertEnvioCorreo' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>Usuario registrado exitosamente. Se le enviará un correo de confirmación a " + data.cuentaCorreo.correo + "<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertEnvioCorreo").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                    setTimeout(function () {
                        $(location).attr("href", "../comprobarCuenta.html");
                    }, 2000);
                }).fail(function (fallo) {
                    setTimeout(function () {
                        $("#modalCargandoRegistrar").modal("hide");
                        $("#registerAlert1").append("<div id='alertFalloRegistro' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>El usuario no pudo ser creado. El nickname o correo ya existe.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                        $("#alertFalloRegistro").on("close.bs.alert", function () {
                            $(this).remove();
                        });
                    }, 500);
                });
            } else {
                setTimeout(function () {
                    $("#modalCargandoRegistrar").modal("hide");
                    $("#registerAlert1").append("<div id='alertInfoIncom' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe ingresar todos los campos para continuar con el registro.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertInfoIncom").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            }
            callback2();
        },
        postNotificacion: function (nombreUsuario, datosNotificacion, callback) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/" + nombreUsuario + "/notificaciones",
                data: JSON.stringify(datosNotificacion)
            }).done(function () {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                }, 500);
            }).fail(function () {
                setTimeout(function () {
                    $("#modalCargandoHome").modal("hide");
                }, 500);
            });
            callback(datosNotificacion);
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
                    $("#modalCargandoLogin").modal("hide");
                    $("#loginAlert1").append("<div id='alertVacioU2' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>" + fallo.responseJSON.message + "<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertVacioU2").on("close.bs.alert", function () {
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
                setTimeout(function () {
                    $("#modalCargandoPerfil").modal("hide");
                    $("#agregarAmigoAlert").append("<div id='alertAmigoAgregado' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>Amigo añadido exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertAmigoAgregado").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
                setTimeout(function () {
                    $(location).attr("href", "../perfil.html");
                }, 2000);
            }).fail(function () {
                setTimeout(function () {
                    $("#modalCargandoPerfil").modal("hide");
                    $("#agregarAmigoAlert").append("<div id='alertNoAgregoAmigo' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>El amigo no pudo ser añadido. Inténtelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertNoAgregoAmigo").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
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
        },
        getNotificaciones: function (correo, callback) {
            $.get("/schinotes/usuarios/" + correo + "/notificaciones", function (data) {
                callback(data);
            });
        },
        deleteAmigo: function (correo, idAmigo, activeUser, callback) {
            var newUser = null;
            $.ajax({
                url: "/schinotes/usuarios/" + correo + "/amigos/" + idAmigo,
                type: 'DELETE',
            }).done(function () {
                setTimeout(function () {
                    $("#modalCargandoPerfil").modal("hide");
                    $("#eliminarAmigoAlert").append("<div id='alertAmigoEliminado' class='col-md-12'><div class='alert alert-success alert-dismissible fade show' role='alert'>Amigo eliminado exitosamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertAmigoEliminado").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
                $.get("/schinotes/usuarios/" + correo, function (data) {
                    newUser = data;
                    callback(newUser);
                });
                setTimeout(function () {
                    $("#modalVerAmigos").modal("hide");
                    $(location).attr("href", "../perfil.html");
                }, 2000);                
            }).fail(function () {
                setTimeout(function () {
                    $("#modalCargandoPerfil").modal("hide");
                    $("#eliminarAmigoAlert").append("<div id='alertAmigoNoEliminado' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>El amigo no pudo ser eliminado. Inténtelo nuevamente.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $("#alertAmigoNoEliminado").on("close.bs.alert", function () {
                        $(this).remove();
                    });
                }, 500);
            });
        }
    };

})();