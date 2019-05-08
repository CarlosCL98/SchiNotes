var appStomp = (function () {

    var stompClient = null;
    var idGrupoGlobal = null;

    var connectAndSubscribe = function (idGrupo) {
        console.info('Connecting to SchiNotes WebSocket...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idGrupo} when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/horario.' + idGrupo, function (eventbody) {
                
                apiGrupo.getHorarioGrupo(idGrupo, homeGrupo.recargarHorario);
            });
        });
    };

    var enviarCambios = function (actividad) {
        stompClient.send('/app/horario.' + idGrupoGlobal, {}, JSON.stringify(actividad));
    };

    return {
        init: function (idGrupo) {
            idGrupoGlobal = idGrupo;
            //websocket connection
            connectAndSubscribe(idGrupo);
        },
        cambiarHorarioConActividades: function (actividad) {
            enviarCambios(actividad);
        },
        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            
        }
    };
})();