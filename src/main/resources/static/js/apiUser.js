var apiUser = (function(){

    return{
        postUser: function(callback){
            
            var nombre = $('#nombreInput').val();
            var apellido= $('#apellidoInput').val();
            var email= $('#emailInput').val();
            var password= $('#password').val();
            $.post("/schinotes/register",{"nombre":nombre,"apellido":apellido,"cuenta":{"email":email,"contraseña":password,"nickname":email}},
            function(){
                console.log(nombre);
                callback(nombre,apellido,email,password)
            });
            
        }
    }

}
)();