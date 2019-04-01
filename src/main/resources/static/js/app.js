/* global Cookies, apiUsuario, apiHorario */
var app = (function () {

    var verificarUsuario = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (cuenta.contrasena === contrasena) {
            Cookies.set('username', cuenta.correo, {expires: 2});
            $(location).attr("href", "../home.html");
        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var mostrarPerfil = function (usuario) {
        console.log(usuario.nombre);
        $("#perfilNombre").text(usuario.nombre);
        $("#perfilApellido").text(usuario.apellido);
        $("#perfilCorreo").text(usuario.cuentaCorreo.correo);
    };

    var mostrarHorario = function (horario) {
        console.log(horario);
        var dias = horario.diasDeLaSemana;
        
        for (var i = 0; i < dias.length; i++) {
            $("#PerfilHorario").find("thead tr").append("<th scope='col'>" + dias[i].nombre + "</th>");
            console.log(i);
        }
        var horas = horario.horas;
        for (var i = 0; i < horas.length; i++) {
            $("#PerfilHorario").find("tbody").append("<tr><th scope='row'>" + horas[i].hora + "</th></tr>");
            for (var j = 0; j < dias.length; j++) {
                $("#PerfilHorario").find("tbody tr:last").append("<td><button><img src='../images/mas.png' style='width: 20px'></button></td>");
            }            
        }
    };

    return {
        agregarUsuario: function () {
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
                apiUsuario.postUsuario(data);
            }
        },
        autenticarUsuario: function () {
            var correo = $("#UsuarioInput").val();
            apiUsuario.getCuenta(correo, verificarUsuario);
        },
        consultarPerfil: function () {
            var correo = Cookies.get('username');
            apiUsuario.getUsuario(correo, mostrarPerfil);
        },
        verificarDatos: function (data) {
            var infoCompleta = true;
            if (data.nombre === "" || data.apellido === "" || data.cuentaCorreo.correo === "" || data.cuentaCorreo.contrasena === "" || data.cuentaCorreo.nickname === "") {
                infoCompleta = false;
            }
            return infoCompleta;
        },
        mostrarHorario: function () {
            var nombre = $("#PerfilHorarioNombre").val();
            apiHorario.getHorario(Cookies.get('username'), nombre, mostrarHorario);
        }
    };
})();