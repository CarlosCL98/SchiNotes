var appFunctions = (function () {

    return {
        subirAlTop: function () {
            $("body").scrollTop = 0;
        },
        cerrarSesion: function () {
            $(location).attr("href", "../index.html");
            Cookies.remove('username', { path: '/'});
        }
    };

})();