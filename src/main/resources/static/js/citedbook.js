document.getElementById("APA").style.display="block";
document.getElementById("standard").style.display="none";
document.getElementById("SIST02").style.display="none";

let changeDisplay =
    function(id) {
        let APA = document.getElementById("APA");
        let standard = document.getElementById("standard");
        let SIST02 = document.getElementById("SIST02");

        let target = document.getElementById(id);

        APA.style.display="none";
        standard.style.display="none";
        SIST02.style.display="none";

        target.style.display="block";
    }

//ユーザーに可視状態であるテクストを判別しコピー
let copyText =
    function() {
        let APA = document.getElementById("APA");
        let MLA = document.getElementById("standard");
        let chicago = document.getElementById("SIST02");

        if(APA.style.display==="block") {
            copy(APA.textContent);
            return;
        }
        if(standard.style.display==="block") {
            copy(standard.textContent);
            return;
        }
        copy(SIST02.textContent);
    }

let copy =
    function(text) {
        navigator.clipboard.writeText(text)
        .then(
        (success) => alert("コピー完了。"),
        (error) => alert("コピーに失敗しました。")
        );}