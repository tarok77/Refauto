document.getElementById("MLA").style.display="none";
document.getElementById("Chicago").style.display="none";

let copyText =
    function(id) {
        const text = document.getElementById(id);

    }

let changeDisplay =
    function(id) {
        let APA = document.getElementById("APA");
        let MLA = document.getElementById("MLA");
        let chicago = document.getElementById("Chicago");

        let target = document.getElementById(id);

        APA.style.display="none";
        MLA.style.display="none";
        chicago.style.display="none";

        target.style.display="block";
    }
let insertHidden =
     function(id) {
         let els = document.getElementById(id);
          let fields = document.getElementById("hiddenField");

          for (let i = 0; i < els.children.length; i++) {
                fields.children[i].value = els.children[i].textContent;
          }
           document.formHidden.submit();
       }