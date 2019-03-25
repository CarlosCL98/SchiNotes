var app = (function () {
    
    

    var verificarUsuario = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (cuenta.contrasena == contrasena) {
            Cookies.set('username', cuenta.correo,{ expires: 7});
            $(location).attr("href", "../home.html");
            
        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var mostrarPerfil = function (cuenta) {        
        console.log(cuenta);
        $("#perfilNombre").text(cuenta.usuario.nombre);
        $("#perfilApellido").text(cuenta.usuario.apellido);
        $("#perfilCorreo").text(cuenta.correo);
        console.log();
    };

    return {
        agregarUsuario: function () {
            apiUser.postUser();
        },
        autenticarUsuario: function () {
            correo = $("#UsuarioInput").val();
            apiUser.getUser(correo, verificarUsuario);
            
        },
        consultarPerfil: function () {            
            var correoCookie = Cookies.get('username');
            apiUser.getUser(correoCookie, mostrarPerfil);
        }
    }
}
)();