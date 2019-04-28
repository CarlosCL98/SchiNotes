var apiGrupo = (function () {
    

    return {
        postGrupo: function (nombreUsuario, datosGrupo) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/" + nombreUsuario + "/grupos",
                data: JSON.stringify(datosGrupo)
            }).done(function () {
                alert("El grupo se creo exitosamente");
            }).fail(function () {
                alert("El grupo no se pudo crear. Intentelo nuevamente.");
            });
        },
        getGrupos: function (correoUsuario, callback) {
            $.get("/schinotes/usuarios/" + correoUsuario + "/grupos", function(data) {
                callback(data);
            });
        },
        getHorarioGrupo: function (idGrupo,callback){
            $.get("/schinotes/grupos/"+idGrupo,function(data){
                callback(data)
            });
        }
    };

})();