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
                    $(location).attr("href", "../login.html");
                }).fail(function (data1) {
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
        }
    };

})();