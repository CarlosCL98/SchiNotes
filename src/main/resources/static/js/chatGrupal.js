var chatGrupal = (function () {

    var stompClient = null;

    var connectAndSubscribe = function (idGrupoChat) {
        console.info('Connecting to SchiNotes WebSocket...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idHorario} when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/grupo.' + idGrupoChat, function (eventbody) {
                mostrarMensaje(eventbody.body);
            });
        });
    };

    var mostrarMensaje = function (mensaje) {
        var mensajeTexto = JSON.parse(mensaje);
        $("#inputMensajeChat").val("");
        $("#chatSeccion").append(mensajeTexto+"<br>");
    };

    return {
        init: function () {
            //websocket connection
            connectAndSubscribe(1);
        },
        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        },
        enviarMensaje: function () {
            var mensaje = $("#inputMensajeChat").val();
            stompClient.send('/topic/grupo.' + 1, {}, JSON.stringify(mensaje));
        }
    };

})();