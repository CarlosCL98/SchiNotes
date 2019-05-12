var appStomp = (function () {

    var stompClient = null;
    var idGrupoGlobal = null;

    var connectAndSubscribe = function (idHorario) {
        console.info('Connecting to SchiNotes WebSocket...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idGrupo} when connections succeed
        stompClient.connect("cayumjwz", "GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO", function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/horario.' + idHorario, function (eventbody) {
                apiHorario.getHorarioById(Cookies.get("username"), idHorario, home.recargarHorario);
            });
        }, function (error) {
            console.info("error" + error);
        }, "cayumjwz");
    };

    var connectAndSubscribeGrupo = function (idGrupo) {
        console.info('Connecting to SchiNotes WebSocket...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idGrupo} when connections succeed
        stompClient.connect("cayumjwz", "GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO", function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/horarioGrupo.' + idGrupo, function (eventbody) {
                apiGrupo.getHorarioGrupo(idGrupo, homeGrupo.recargarHorario);
            });
        }, function (error) {
            console.info("error" + error);
        }, "cayumjwz");
    };

    var enviarCambios = function (actividad) {
        stompClient.send('/app/horario.' + idHorarioGlobal, {}, JSON.stringify(actividad));
    };

    var enviarCambiosGrupo = function (actividad) {
        stompClient.send('/app/horarioGrupo.' + idGrupoGlobal, {}, JSON.stringify(actividad));
    };

    return {
        init: function (idHorario) {
            idHorarioGlobal = idHorario;
            //websocket connection
            connectAndSubscribe(idHorario);
        },
        initGrupo: function (idGrupo) {
            idGrupoGlobal = idGrupo;
            //websocket connection
            connectAndSubscribeGrupo(idGrupo);
        },
        cambiarHorarioConActividades: function (actividad) {
            enviarCambios(actividad);
        },
        cambiarHorarioGrupoConActividades: function (actividad) {
            enviarCambiosGrupo(actividad);
        },
        disconnectHorario: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Horario Desconectado");
        },
        disconnectGroup: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Grupo Desconectado");
        }
    };
})();