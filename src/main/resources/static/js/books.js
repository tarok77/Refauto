let insertHidden =
    function(id) {
        let els = document.getElementById(id);
        let fields = document.getElementById("hiddenField");

        for (let i = 0; i < els.children.length; i++) {
            fields.children[i].value = els.children[i].textContent;
        }
        document.formHidden.submit();
    }