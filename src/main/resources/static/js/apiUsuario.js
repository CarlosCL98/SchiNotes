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
                    alert("Usuario registrado exitosamente. Se le enviará un correo de confirmación a " + data.cuentaCorreo.correo);
                    $(location).attr("href", "../comprobarCuenta.html");
                }).fail(function () {
                    alert("El usuario no pudo ser creado. Inténtelo nuevamente.");
                });
            } else {
                alert("Debe ingresar todos los campos para continuar con el registro.");
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
            $.get("/schinotes/usuarios/"+correoUsuario+"/comprobar", function (data) {
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
            $.get("/schinotes/usuarios/"+correo+"/cuentas/verificar", function (data) {
                callback(data);
            });
        }
    };

})();