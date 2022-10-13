let insertHidden =
    function(id) {
        let els = document.getElementById(id);
        let fields = document.getElementById("hiddenField");

        for (let i = 0; i < els.children.length; i++) {
        //CSRF対策のhiddenfieldが生成され一つずれるため +1 が必要
            fields.children[i+1].value = els.children[i].textContent;
        }
        document.formHidden.submit();
    }