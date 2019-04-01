/* global app */
var apiUsuario = (function () {

    return{
        postUsuario: function (data) {            
            var infoCompleta = app.verificarDatos(data);
            if (infoCompleta) {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/schinotes/usuarios/registrar",
                    data: JSON.stringify(data),
                    dataType: 'json'
                });
                alert("Usuario registrado exitosamente.");
                $(location).attr("href", "../login.html");
            } else {
                alert("Debe ingresar todos los campos para continuar con el registro.");
            }
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