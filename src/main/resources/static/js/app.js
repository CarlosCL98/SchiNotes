var app = (function() {

    var UsuarioActual = {};

    var verificarUsuario = function(cuenta) {
        var contrasena = $("#ContraseñaInput").val(); 
        UsuarioActual = cuenta.usuario;
        if (cuenta.contrasena == contrasena) {
            $(location).attr("href", "../index.html");
        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var mostrarPerfil = function() {
        console.log(UsuarioActual);
        $(location).attr("href", "../perfil.html");
        $("#nombre").find("output").attr("name",UsuarioActual.nombre);
    }

    return {
        agregarUsuario:function() {
            apiUser.postUser();
        },
        autenticarUsuario:function() {
            var correo = $("#UsuarioInput").val(); 
            apiUser.getUser(correo, verificarUsuario);
        },
        consultarPerfil:function() {
            mostrarPerfil();
        }
    }
}       
)();