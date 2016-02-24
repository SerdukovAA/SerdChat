///////////////////////////////////////////////////////////////////////////////////////
function SearchUsersFriendsAndNot() {
    var token = $('#token').val();
    var name = $('#token').attr("name");
    var string = "%"+$("input[name='search']").val()+"%";
    var user_id= $('.userInfo').attr("id");
    $.getJSON("/chatUsersFriendsAndNot?"+name+"="+token, {string:string, user_id:user_id}, function(data) {

        var html = '';
        html+="	<h2 class ='resultTitle'>Найдены контакты</h2>";
        $.each(data, function (key, val) {

            html += "<div class='listUsers' id='"+val.user_id+"'>" +
            "<div class='avatarFriend'><img src='"+val.avatar+"'></div>";
            if(val.userRoleId == 8) html +="<p class='friend'>Уже в контактах</p>";
            if(val.userRoleId != 8) html += "<a class='addContact' href='#' id='"+val.user_id+"' class='addUser'>Добавить в контакты</a>";

            html +="<div class='infoFriend'><p class='login'>Пользователь:<span> "+val.email+"</span></p>" +
            "<p class='fio'>"+val.firstName+" "+val.lastName+"</p>";
            html += "</div></div>";

        });

        html+="<script src='/resource/js/userAdd.js'></script>";


        $('.resultBox').html(html);
    });
}
///////////////////////////////////////////////////////////////////////////////////////
function FrendList() {
    var token = $('#token').val();
    var name = $('#token').attr("name");

    var user_id= $('.userInfo').attr("id");


    $.getJSON("/chatUsersFriends?"+name+"="+token, {user_id:user_id}, function(data) {

        var html = '';
        html+="	<h2 class ='resultTitle'>Спикок контактов</h2>";
        $.each(data, function (key, val) {

            html += "<div class='listUsers' id='"+val.user_id+"' >" +
            "<div class='avatarFriend'><img src='"+val.avatar+"'></div>"+
            "<div class='utilFriend'><a href='#' class='chatBegin' id='"+val.user_id+"' class='write'>Переписка</a></div>" +
            "<div class='infoFriend'><p class='login'>Пользователь:<span> "+val.email+"</span></p>" +
            "<p class='fio'>"+val.firstName+" "+val.lastName+"</p>" +
            "<a href='#' class='removeUser' id='"+val.user_id+"' class='write'>Удалить контакт</a></div>" +
            "</div>";

        });

        html+="<script src='/resource/js/userHandle_accept.js'></script>";
        html+="<script src='/resource/js/chatBegin.js'></script>";
        html+="<script src='/resource/js/userDel.js'/>";
        $('.resultBox').html(html);
    });
}
///////////////////////////////////////////////////////////////////////////////////////
