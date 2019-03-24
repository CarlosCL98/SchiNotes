var apiUser = (function(){

    return{
        postUser: function(){
            var data = {
                nombre:$('#nombreInput').val(),
                apellido:$('#apellidoInput').val(),
                cuentaCorreo:{
                    correo:$('#emailInput').val(),
                    contrasena:$('#passwordInput').val(),
                    nickname:$('#emailInput').val()
                }
            };
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "/schinotes/register",
                data : JSON.stringify(data),
                dataType : 'json',
                success : function(result) {
                    if (result.status == "success") {
                        alert("El registro fue exitoso.");
                    } else {
                        alert(result.status);
                        alert("El registro no pudo ser completado. Inténtelo nuevamente.");
                    }
            }});
        },
        getUser: function(correo,callback){
            $.get("/schinotes/"+correo,function(data){
                callback(data);
            });
        }
    }

}
)();