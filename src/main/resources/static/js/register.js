var register = (function () {

    var verificarDatos = function (data) {
        var infoCompleta = true;
        if (data.nombre === "" || data.apellido === "" || data.cuentaCorreo.correo === "" || data.cuentaCorreo.contrasena === "" || data.cuentaCorreo.nickname === "") {
            infoCompleta = false;
        }
        return infoCompleta;
    };

    var borrarDatos = function () {
        $('#nombreInput').val('');
        $('#apellidoInput').val('');
        $('#nicknameInput').val('');
        $('#emailInput').val('');
        $('#passwordInput').val('');
        $('#passwordConfirmarInput').val('');
    };

    var verificarCodigoCuenta = function (codigo, data) {
        if (codigo === data) {
            apiUsuario.usuarioVerificado(Cookies.get("correoVerificar"));
            alert("Código verificado satisfactoriamente.");
            $(location).attr("href", "../login.html");
            Cookies.remove('correoVerificar', { path: '/' });
        } else {
            alert("El código es incorrecto. Vuelva a ingresarlo.");
        }
    }

    return {
        registrarUsuario: function () {
            if ($('#passwordConfirmarInput').val() !== $('#passwordInput').val()) {
                alert("Las contraseñas no coinciden, por favor vuelva a ingresarlas.")
            } else {
                var data = {
                    nombre: $('#nombreInput').val(),
                    apellido: $('#apellidoInput').val(),
                    cuentaCorreo: {
                        correo: $('#emailInput').val(),
                        contrasena: $('#passwordInput').val(),
                        nickname: $('#nicknameInput').val()
                    }
                };
                Cookies.set('correoVerificar', data.cuentaCorreo.correo, { expires: 1, path: '/' });
                apiUsuario.postUsuario(data, verificarDatos, borrarDatos);
            }
        },
        verificarCodigo: function () {
            var codigo = $("#codigoVerificacion").val();
            apiUsuario.getCodigoComprobacion(Cookies.get("correoVerificar"), codigo, verificarCodigoCuenta);
        }
    };

})();