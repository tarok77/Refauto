document.getElementById("APA").style.display="block";
document.getElementById("MLA").style.display="none";
document.getElementById("Chicago").style.display="none";

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

//ユーザーに可視状態であるテクストを判別しコピー
let copyText =
    function() {
        let APA = document.getElementById("APA");
        let MLA = document.getElementById("MLA");
        let chicago = document.getElementById("Chicago");

        if(APA.style.display==="block") {
            copy(APA.textContent);
            return;
        }
        if(MLA.style.display==="block") {
            copy(MLA.textContent);
            return;
        }
        copy(Chicago.textContent);
    }

let copy =
    function(text) {
        navigator.clipboard.writeText(text)
        .then(
        (success) => alert("コピー完了。"),
        (error) => alert("コピーに失敗しました。")
        );}