var appFunctions = (function () {

    var permitirDrag = function () {
        function handleDragOver(e) {
            if (e.preventDefault) {
                e.preventDefault(); // Necessary. Allows us to drop.
            }
            e.dataTransfer.dropEffect = 'move';  // See the section on the DataTransfer object.
            return false;
        }
        function handleDragEnter(e) {
            // this / e.target is the current hover target.
            this.classList.add('over');
        }
        function handleDragLeave(e) {
            this.classList.remove('over');  // this / e.target is previous target element.
        }
        function handleDrop(e) {
            // this / e.target is current target element.
            if (e.stopPropagation) {
                e.stopPropagation(); // stops the browser from redirecting.
            }
            // See the section on the DataTransfer object.
            return false;
        }
        function handleDragEnd(e) {
            // this/e.target is the source node.
            var cols = document.querySelectorAll('.drag');
            [].forEach.call(cols, function (col) {
                col.classList.remove('over');
            });
        }
        var acts = document.querySelectorAll('.drag');
        [].forEach.call(acts, function (act) {
            act.addEventListener('dragstart', handleDragStart, false);
            act.addEventListener('dragenter', handleDragEnter, false);
            act.addEventListener('dragover', handleDragOver, false);
            act.addEventListener('dragleave', handleDragLeave, false);
            act.addEventListener('drop', handleDrop, false);
            act.addEventListener('dragend', handleDragEnd, false);
        });
        // Objeto datatranfer
        var dragSrcEl = null;
        function handleDragStart(e) {
            // Target (this) element is the source node.
            this.style.opacity = '1.0';

            dragSrcEl = this;

            e.dataTransfer.effectAllowed = 'move';
            e.dataTransfer.setData('text/html', this.innerHTML);
        }
        function handleDrop(e) {
            // this/e.target is current target element.
            if (e.stopPropagation) {
                e.stopPropagation(); // Stops some browsers from redirecting.
            }
            // Don't do anything if dropping the same column we're dragging.
            if (dragSrcEl != this) {
                // Set the source column's HTML to the HTML of the columnwe dropped on.
                dragSrcEl.innerHTML = this.innerHTML;
                this.innerHTML = e.dataTransfer.getData('text/html');
            }
            return false;
        }
    };

    return {
        subirAlTop: function () {
            $("body").scrollTop = 0;
        },
        cerrarSesion: function () {
            $(location).attr("href", "../index.html");
            Cookies.remove('username', { path: '/' });
        },
        draggable: function() {
            permitirDrag();
        }
    };

})();