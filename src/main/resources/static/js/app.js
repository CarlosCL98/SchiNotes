var app = (function(){

    var registerUsuario = (function(name,apellido,email,password){
        console.log("name");
    })();

    return{
        agragarUsuario:function(){
            console.log("ayudaaaa");
            apiUser.postUser(registerUsuario)
        }
    }
}       
)();