<%@ page  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8">
    <script src="/resource/js/lib/jquery-2.1.1.min.js"></script>
    <script src="/resource/js/admin.js"></script>
    <link href="/resource/css/style_admin.css" rel="stylesheet">
    <title>Админ</title>
    <script>

        $(document).ready(function() {


            UsersQueu();
            $('#link_1').click(function(){
                UsersQueu();
            });
            $('#link_2').click(function(){
                UsersDenied();
            });
            $('#link_3').click(function(){
                UsersAccepted();
            });

        });



</script>

</head>
<body class="body">
<input type="hidden" id="token" name="${_csrf.parameterName}"
       value="${_csrf.token}" />

                                        <div class="left_bar">
                                            <h2 class ="bar_title">Панель администратора</h2>
                                            <ul class = "bar_list">
                                                <a id="link_1" href="#"> <li class ="list_li">Ожидающие подтверждения регистрации</li></a>
                                                <a id="link_2" href="#"> <li class ="list_li">Список отклоненных пользователей</li></a>
                                                <a id="link_3" href="#"> <li class ="list_li">Список действующих пользователей</li></a>
                                            </ul>
                                        </div>
                <div id="user_list_1" class="user_list_1">
                    <h2 class ='accept' >Список пользователей ожидающих подтверждение регистрации</h2>
                    <div id="user_list" class="user_list">
                    </div>
                </div>



</body>
</html>