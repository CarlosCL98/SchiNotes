var appStomp = (function () {

    var stompClient = null;
    var idHorarioGlobal = null;

    var connectAndSubscribe = function (idHorario) {
        console.info('Connecting to SchiNotes WebSocket...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idHorario} when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/horario.' + idHorario, function (eventbody) {
                //alert(eventbody);
                console.log("Recibi la actividad "+eventbody.body);
                console.log("Horarioid = " + idHorario);
                apiHorario.getHorarioById(Cookies.get("username"), idHorario, app.recargarHorario);
            });
        });
    };

    var enviarCambios = function (actividad) {
        stompClient.send('/app/horario.' + idHorarioGlobal, {}, JSON.stringify(actividad));
    };

    return {
        init: function (idHorario) {
            idHorarioGlobal = idHorario;
            //websocket connection
            connectAndSubscribe(idHorario);
        },
        cambiarHorarioConActividades: function (actividad) {
            enviarCambios(actividad);
        },
        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };
})();