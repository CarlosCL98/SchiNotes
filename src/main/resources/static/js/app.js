/* global Cookies, apiUsuario, apiHorario */
var app = (function () {

    var verificarUsuario = function (cuenta) {
        var contrasena = $("#ContraseñaInput").val();
        if (cuenta.contrasena === contrasena) {
            Cookies.set('username', cuenta.correo, { expires: 1, path: '/' });
            $(location).attr("href", "../home.html");
        } else {
            alert("Credenciales inválidas. No puede ingresar.");
        }
    };

    var mostrarPerfil = function (usuario) {
        console.log(usuario);
        $("#perfilCard").find("h1").text("" + usuario.nombre + " " + usuario.apellido + " ");
        $("#perfilCard").find("p:last").text("correo: " + usuario.cuentaCorreo.correo)
        $("#perfilCard").append("<p style =\"font-size: 20px\"> nickname: " + usuario.cuentaCorreo.nickname + "</p>");

    };

    var mostrarHorario = function (horario) {
        $("#schedule").empty();
        console.log(horario);
        var dias = horario.diasDeLaSemana;

        for (var i = 0; i < dias.length; i++) {
            $("#PerfilHorario").find("thead tr").append("<th scope='col'>" + dias[i].nombre + "</th>");
            console.log(i);
        }
        var horas = horario.horas;
        $("#schedule").append("<div class='timeline'><ul></ul></div>");

        for (var i = 0; i < horas.length; i++) {
            $("#schedule").find("ul").append("<li><span>"+horas[i].hora+"</span></li>");
        }
        $("#schedule").append("<div class='events'><ul></ul></div>");

        for (var i = 0; i < dias.length; i++) {
            $("#schedule").find("ul:last").append("<li class='events-group'><div class='top-info'><span>" + dias[i].nombre + "</span></div></li>");
        }
        
    };

    var creacionDeHorario = function (){
        alert("su horario ha sido creado exitosamanete");
    };


    var agregarOpcionesHorarios = function(data){
        
        $("#deployHorariosButton").empty();
        console.log(data);
        for (var i = 0; i < data.length; i++) {
            $("#deployHorariosButton").append("<a class='dropdown-item' href='#' data-horario="+data[i].nombre+">"+data[i].nombre+"</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event){
            cambiarHorario(event.target.dataset.horario);
            console.log(event.target.dataset.horario);
        });

    }

    var cambiarHorario = function(nombre){
        
        apiHorario.getHorario(Cookies.get('username'), nombre, mostrarHorario);
    }
    
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
    }

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
                apiUsuario.postUsuario(data, verificarDatos, borrarDatos);
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
        
        agregarHorario: function(){
            var data = {
                nombre: $('#horarioNombre').val(),
                intervaloHoras: $('#intervaloHoras').val(),
                numeroDias: $('#horarioNumDias').val()
            }
            apiHorario.postHorario(data,Cookies.get('username'));
        },
        consultarMisHorarios: function(){
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario,agregarOpcionesHorarios);
        }
        
    };
})();