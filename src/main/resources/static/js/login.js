var login = (function () {

    var verificarCredenciales = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (contrasena === "") {
            setTimeout(function () {
                $('#modalCargandoLogin').modal('hide');
                $("#loginAlert2").append("<div id='alertVacioP' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>No ha ingresado ninguna contraseña.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                $('#alertVacioP').on('close.bs.alert', function () {
                    $(this).remove();
                });
            }, 500);
        } else if (cuenta.contrasena === contrasena) {
            Cookies.set('username', cuenta.correo, { expires: 1, path: '/' });
            apiUsuario.cuentaYaVerificada(Cookies.get("username"), comprobarCuentaVerificada);
        } else {
            setTimeout(function () {
                $('#modalCargandoLogin').modal('hide');
                $("#loginAlert3").append("<div id='alertCredencialesInvalidas' class='col-md-12'><div class='alert alert-danger alert-dismissible fade show' role='alert'>¡Credenciales inválidas! No puede ingresar.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");

                $('#alertCredencialesInvalidas').on('close.bs.alert', function () {
                    $(this).remove();
                });
            }, 500);
        }
    };

    var comprobarCuentaVerificada = function (data) {
        if (data === true) {
            $('#modalCargandoLogin').modal('hide');
            $(location).attr("href", "../home.html");
        } else {
            setTimeout(function () {
                $('#modalCargandoLogin').modal('hide');
                $("#loginAlert3").append("<div id='alertCuentaNoVerificada' class='col-md-12'><div class='alert alert-info alert-dismissible fade show' role='alert'>La cuenta aún no ha sido verificada. No puede ingresar.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                $('#alertCuentaNoVerificada').on('close.bs.alert', function () {
                    $(this).remove();
                });
            }, 500);
        }
    }

    return {
        autenticarUsuario: function () {
            var correo = $("#UsuarioInput").val();
            if (correo === "") {
                setTimeout(function () {
                    $('#modalCargandoLogin').modal('hide');
                    $("#loginAlert1").append("<div id='alertVacioU1' class='col-md-12'><div class='alert alert-warning alert-dismissible fade show' role='alert'>Debe colocar una cuenta para poder ingresar.<button type='button' class='close col-md-2' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div></div>");
                    $('#alertVacioU1').on('close.bs.alert', function () {
                        $(this).remove();
                    });
                }, 500);
            } else {
                apiUsuario.getCuenta(correo, verificarCredenciales);
            }
        }
    };

})();