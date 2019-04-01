var apiHorario = (function () {
    
    return {
        getHorario: function (usuario, nombreHorario, callback) {
            $.get("/schinotes/usuarios/"+usuario+"/horarios/"+nombreHorario, function (data) {
                callback(data);
            });
        }
    };
    
})();
