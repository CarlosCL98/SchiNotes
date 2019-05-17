var chatGrupal = (function () {

    var stompClient = null;
    var grupoId = null;
    var conectados = 0;

    var connectAndSubscribe = function (idGrupoChat) {
        console.info("Connecting to SchiNotes WebSocket...");
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);
        //subscribe to /topic/horario.{idHorario} when connections succeed
        stompClient.connect("cayumjwz", "GBsaLlE828vd2w8LruiQ7IzSMbnlZwBO", function (frame) {
            console.log("Connected: " + frame);
            stompClient.subscribe("/topic/chatGrupal." + idGrupoChat, function (eventbody) {
                mostrarMensaje(eventbody.body);
            });
            stompClient.subscribe("/topic/conectado." + idGrupoChat, function (eventbody) {
                modificarConectados(eventbody.body);
            });
            stompClient.subscribe("/topic/desconectar." + idGrupoChat, function (eventbody) {
                modificarConectados(eventbody.body);
            });
            actualizarConectados();
        }, function (error) {
            console.info("error" + error);
        }, "cayumjwz");
    };

    var actualizarConectados = function () {
        stompClient.send("/app/conectado." + grupoId, {}, JSON.stringify(Cookies.get("username")));
    };

    var modificarConectados = function (param) {
        var conectado = JSON.parse(param);
        conectados = conectado;
        console.log("ME CONECTÉ Y AHORA HAY: "+conectados);
        $("#conectadosChat").text("Conectados: " + conectados);
    };

    var desconectarChat = function () {
        stompClient.send("/app/desconectar." + grupoId, {}, JSON.stringify(Cookies.get("username")));
    };

    var mostrarMensaje = function (param) {
        var datos = JSON.parse(param);
        var usuario = datos.usuario;
        var mensaje = datos.mensaje;
        var fecha = new Date();
        var cadenaFecha = fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear() + " - " + fecha.getHours() + ":" + fecha.getMinutes() + ":" + fecha.getSeconds();
        if (usuario === Cookies.get("username")) {
            $("#chatGrupal").append("<div class='balon1 p-2 m-0 position-relative' data-is='yo\n" + cadenaFecha + "'><a class='float-right'> " + mensaje + " </a></div>");
        } else {
            $("#chatGrupal").append("<div class='balon2 p-2 m-0 position-relative' data-is='" + usuario + "\n" + cadenaFecha + "'><a class='float-left sohbet2'> " + mensaje + " </a></div>");
        }
        $("#inputMensajeChat").val("");
        var element = $("#inputMensajeChat").emojioneArea();
        element[0].emojioneArea.setText("");
    };

    return {
        init: function () {
            //websocket connection
            grupoId = Cookies.get("grupoId");
            connectAndSubscribe(grupoId);
        },
        disconnect: function () {
            desconectarChat();
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Grupo Desconectado");
        },
        enviarMensaje: function () {
            var mensaje = $("#inputMensajeChat").val();
            stompClient.send("/topic/chatGrupal." + grupoId, {}, JSON.stringify({ mensaje: mensaje, usuario: Cookies.get("username") }));
        }
    };

})();