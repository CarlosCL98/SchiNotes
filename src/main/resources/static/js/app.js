/* global Cookies, apiUsuario */

var app = (function () {

    var verificarUsuario = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (cuenta.contrasena == contrasena) {
            Cookies.set('username', cuenta.correo, {expires: 7});
            $(location).attr("href", "../home.html");

        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var mostrarPerfil = function (usuario) {
        $("#perfilNombre").text(usuario.nombre);
        $("#perfilApellido").text(usuario.apellido);
        $("#perfilCorreo").text(usuario.cuentaCorreo.correo);
    };

    return {
        agregarUsuario: function () {
            apiUsuario.postUser();
        },
        autenticarUsuario: function () {
            var correo = $("#UsuarioInput").val();
            apiUsuario.getCuenta(correo, verificarUsuario);
        },
        consultarPerfil: function () {
            var correo = Cookies.get('username');
            apiUsuario.getUsuario(correo, mostrarPerfil);
        }
    };
})();