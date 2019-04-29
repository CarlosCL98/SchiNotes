var chatGrupal = (function () {

    var stompClient = null;
    var grupoId = null;
    var conectados = 0;

    var connectAndSubscribe = function (idGrupoChat) {
        console.info('Connecting to SchiNotes WebSocket...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idHorario} when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/chatGrupal.' + idGrupoChat, function (eventbody) {
                mostrarMensaje(eventbody.body);
            });
            stompClient.subscribe('/topic/conectado.' + idGrupoChat, function (eventbody) {
                aumentarConectados(eventbody.body);
            });
            actualizarConectados();
        });
    };

    var actualizarConectados = function () {
        stompClient.send('/app/conectado.' + grupoId, {}, JSON.stringify(1));
    };

    var aumentarConectados = function (param) {
        var conectado = JSON.parse(param);
        conectados = conectado;
        $("#conectadosChat").text("Conectados: " + conectados);
    };

    var mostrarMensaje = function (param) {
        var datos = JSON.parse(param);
        var usuario = datos.usuario;
        var mensaje = datos.mensaje;
        var fecha = new Date();
        var cadenaFecha = fecha.getDate() + "/" + (fecha.getMonth()+1) + "/" + fecha.getFullYear() + " - " + fecha.getHours() + ":" + fecha.getMinutes() + ":" + fecha.getSeconds();
        if (usuario === Cookies.get("username")) {
            $("#chatGrupal").append('<div class="balon1 p-2 m-0 position-relative" data-is="yo\n' + cadenaFecha + '"><a class="float-right"> ' + mensaje + ' </a></div>');
        } else {
            $("#chatGrupal").append('<div class="balon2 p-2 m-0 position-relative" data-is="' + usuario + '\n' + cadenaFecha + '"><a class="float-left sohbet2"> ' + mensaje + ' </a></div>');
        }
        $("#inputMensajeChat").val("");
    };

    return {
        init: function () {
            //websocket connection
            grupoId = Cookies.get("grupoId");
            connectAndSubscribe(grupoId);
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
            stompClient.send('/topic/chatGrupal.' + grupoId, {}, JSON.stringify({ mensaje: mensaje, usuario: Cookies.get("username") }));
        }
    };

})();