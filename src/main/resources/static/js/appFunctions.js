var appFunctions = (function () {

    var cargarDrag = function () {
        // initialization
        REDIPS.drag.init();
        // set hover color
        REDIPS.drag.hover_color = '#E7C7B2';
        // set drop option to 'switching'
        REDIPS.drag.drop_option = 'switching';
    }

    return {
        subirAlTop: function () {
            $("body").scrollTop = 0;
        },
        cerrarSesion: function () {
            $(location).attr("href", "../index.html");
            Cookies.remove('username', { path: '/' });
        },
        hacerDraggable: function () {
            cargarDrag();
        }
    };

})();