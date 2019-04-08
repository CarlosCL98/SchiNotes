/* global Cookies, apiUsuario, apiHorario */
var app = (function () {


    var actividadHorario;
    var actividadDia;
    var actividadHora;

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
        
        $("#perfilCard").find("h1").text("" + usuario.nombre + " " + usuario.apellido + " ");
        $("#perfilCard").find("p:last").text("correo: " + usuario.cuentaCorreo.correo)
        $("#perfilCard").append("<p style =\"font-size: 20px\"> nickname: " + usuario.cuentaCorreo.nickname + "</p>");

    };

    var mostrarHorario = function (horario) {
        console.log(horario);
        $("#schedule").empty();
        
        var dias = horario.diasDeLaSemana;

        
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
        
        for (var i = 0; i < data.length; i++) {
            $("#deployHorariosButton").append("<a class='dropdown-item' href='#' data-horario="+data[i].nombre+">"+data[i].nombre+"</a>");
        }
        $("#deployHorariosButton").on("click", "a", function (event){
            cambiarHorario(event.target.dataset.horario);
            
        });

    }

    var cambiarHorario = function(nombre){
        
        apiHorario.getHorario(Cookies.get('username'), nombre, mostrarHorario);
    }

    var agregaropcionesHorariosActividades = function(data){
        $("#opcionesHorarioCrearActividad").empty();
        
        for (var i = 0; i < data.length; i++) {
            $("#opcionesHorarioCrearActividad").append("<a class='dropdown-item' href='#' data-horario="+data[i].nombre+">"+data[i].nombre+"</a>");
        }
        $("#opcionesHorarioCrearActividad").on("click", "a", function (event){
            
            var nombreHorario = event.target.dataset.horario;
            actividadHorario = nombreHorario;
            console.log(actividadHorario);
            apiHorario.getHorario(Cookies.get('username'),nombreHorario, cambiarOpcionesCreacionActividad);
            
        });
    };

    var cambiarOpcionesCreacionActividad = function(data){
        $("#opcionesHorarioCrearActividadDias").empty();
        diasDeLaSemana = data.diasDeLaSemana;
        
        for (var i = 0; i < diasDeLaSemana.length; i++) {
            $("#opcionesHorarioCrearActividadDias").append("<a class='dropdown-item' href='#' data-diaSemana="+diasDeLaSemana[i].nombre+">"+diasDeLaSemana[i].nombre+"</a>");
        }
        $("#opcionesHorarioCrearActividadDias").on("click", "a", function (event){
            console.log(event);
            var diaSemana = event.target.dataset.diasemana;
            actividadDia = diaSemana;
            console.log(event.target.dataset.diasemana);
        });



        $("#opcionesHorarioCrearActividadHoras").empty();
        horasDelHorario = data.horas;
        
        for (var i = 0; i < horasDelHorario.length; i++) {
            $("#opcionesHorarioCrearActividadHoras").append("<a class='dropdown-item' href='#' data-hora="+horasDelHorario[i].hora+">"+horasDelHorario[i].hora+"</a>");
        }
        $("#opcionesHorarioCrearActividadHoras").on("click", "a", function (event){
            var horaHorario = event.target.dataset.hora;
            actividadHora = horaHorario;
            console.log(actividadHora);
        });

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
        },
        consultarMisHorariosParaActividades: function(){
            console.log("aqui entre")
            var usuario = Cookies.get('username');
            apiHorario.getHorarios(usuario,agregaropcionesHorariosActividades);
        },
        agregarActividad: function(){
            var data ={
                id:"1",
                nombre:$('#actividadNombre').val(),
                descripcion:$('#actividadDescripcion').val(),
                fecha:"2019-02-98",
                horario: actividadHorario,
                dia: actividadDia,
                hora: actividadHora
            }
            console.log(data)
            apiHorario.postActividad(data,Cookies.get('username'),data.horario);
        }
        
    };
})();