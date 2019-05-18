var appStomp = (function () {

    var stompClient = null;
    var idHorarioGlobal = null;
    var idGrupoGlobal = null;

    var connectAndSubscribe = function (idHorario) {
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idGrupo} when connections succeed
        stompClient.connect("cayumjwz", "GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO", function (frame) {
            stompClient.subscribe("/topic/horario." + idHorario, function (eventbody) {
                apiHorario.getHorarioById(Cookies.get("username"), idHorario, home.recargarHorario);
            });
        }, function (error) {
        }, "cayumjwz");
    };

    var connectAndSubscribeGrupo = function (idGrupo) {
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idGrupo} when connections succeed
        stompClient.connect("cayumjwz", "GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO", function (frame) {
            stompClient.subscribe("/topic/horarioGrupo." + idGrupo, function (eventbody) {
                //apiUsuario.postNotificacion(Cookies.get("username"));
                apiGrupo.getHorarioGrupo(idGrupo, homeGrupo.recargarHorario);
            });
        }, function (error) {
        }, "cayumjwz");
    };

    var connectAndSubscribeNotificacion = function (idGrupo) {
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idGrupo} when connections succeed
        stompClient.connect("cayumjwz", "GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO", function (frame) {
            stompClient.subscribe("/topic/notificacion." + idGrupo, function (eventbody) {
                apiUsuario.postNotificacion(Cookies.get("username"), JSON.parse(eventbody.body), homeGrupo.actualizarNotificaciones);
            });
        }, function (error) {
        }, "cayumjwz");
    };

    var enviarCambios = function (actividad) {
        stompClient.send("/app/horario." + idHorarioGlobal, {}, JSON.stringify(actividad));
    };

    var enviarCambiosGrupo = function (actividad) {
        stompClient.send("/app/horarioGrupo." + idGrupoGlobal, {}, JSON.stringify(actividad));
    };

    var enviarNotificacion = function (notificacion) {
        stompClient.send("/app/notificacion." + idGrupoGlobal, {}, JSON.stringify(notificacion));
    }

    return {
        init: function (idHorario) {
            idHorarioGlobal = idHorario;
            //websocket connection 
            connectAndSubscribe(idHorario);
        },
        initGrupo: function (idGrupo) {
            idGrupoGlobal = idGrupo;
            connectAndSubscribeGrupo(idGrupo);
        },
        initNotificaciones: function (idGrupo) {
            idGrupoGlobal = idGrupo;
            //websocket connection
            connectAndSubscribeNotificacion(idGrupoGlobal);
        },
        cambiarHorarioConActividades: function (actividad) {
            enviarCambios(actividad);
        },
        cambiarHorarioGrupoConActividades: function (actividad) {
            enviarCambiosGrupo(actividad);
        },
        actualizarNotificaciones: function (notificacion) {
            enviarNotificacion(notificacion);
        },
        disconnectHorario: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
        },
        disconnectGroup: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
        }
    };
})();