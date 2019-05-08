var login = (function () {

    var verificarCredenciales = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (cuenta.contrasena === contrasena) {
            Cookies.set('username', cuenta.correo, { expires: 1, path: '/' });
            
            apiUsuario.cuentaYaVerificada(Cookies.get("username"), comprobarCuentaVerificada);
        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var comprobarCuentaVerificada = function (data) {
        if (data === true) {
            $(location).attr("href", "../home.html");
        } else {
            alert("La cuenta aún no ha sido verificada. No puede ingresar.");
        }
    }

    return {
        autenticarUsuario: function () {
            var correo = $("#UsuarioInput").val();
            apiUsuario.getCuenta(correo, verificarCredenciales);
        }
    };

})();