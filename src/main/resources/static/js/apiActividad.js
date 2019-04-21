var apiActividad = (function () {

    return {
        getActividadById: function (idActividad, callback) {
            $.get("/schinotes/actividades/"+idActividad, function(data){
                callback(data);
            });
        }
    };

})();