var apiHorario = (function () {

    return {
        getHorarios: function (usuario, callback) {
            $.get("/schinotes/usuarios/" + usuario + "/horarios", function (data) {
                callback(data);
            });
        },
        getHorario: function (usuario, nombreHorario, callback) {
            $.get("/schinotes/usuarios/" + usuario + "/horarios/" + nombreHorario, function (data) {
                callback(data);
            });
        },
        postHorario: function (data,correo) {
            console.log(data)
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/"+correo+"/horarios",
                data: JSON.stringify(data)
            }).done(function () {
                alert("El horario se creo exitosamente");
            }).fail(function () {
                alert("El horario no se pudo crear. Intentelo nuevamente.");
            });
            //$(location).attr("href", "../login.html");
        },
postActividad: function (data,correo,nombreHorario){
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/schinotes/usuarios/"+correo+"/horarios/"+nombreHorario+"/actividades",
                data: JSON.stringify(data)
            }).done(function () {
                alert("La Actividad se creo exitosamente");
            }).fail(function () {
                alert("La Actividad no se pudo crear. Intentelo nuevamente.");
            });
            
        }
    };

})();
