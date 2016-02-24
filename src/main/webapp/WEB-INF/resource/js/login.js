$(document).ready(function() {

    $(".loginForm").submit(function() {
        history.pushState(1, "login", "/login");
        $("input[name='username']").val( $("input[name='username']").val().toLowerCase());
        var pas = $("input[name='password']").val(CryptoJS.SHA256($.trim($("input[name='pas']").val())).toString());
        var key= "314159265358979323846264338323133134412313212313132213";
        for (var i = 0; i < pas.length-1; i++) {
            key = mult(str2bigInt(key, 10, 0), str2bigInt(pas.charCodeAt(i).toString(), 10, 0));
            key = bigInt2str(key, 10);
        }
        localStorage["myPersonalKey"]=bigInt2str(key,10);

        return true;
    });
});
