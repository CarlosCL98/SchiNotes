var apiUsuario = (function() {

    return{
        postUsuario: function(){
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
                url : "/schinotes/usuarios/registrar",
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
        getUsuario: function(correo,callback){
            $.get("/schinotes/usuarios/"+correo,function(data){
                callback(data);
            });
        },
        getCuenta: function(correo,callback){
            $.get("/schinotes/cuentas/"+correo,function(data){
                callback(data);
            });
        }
    };

})();