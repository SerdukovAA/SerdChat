function UsersQueu() {
    $.getJSON("/chatUsersQueue", null, function(data) {

        var html = '';

        $.each(data, function (key, val) {


            html += "<div class='user_box' id='"+val.user_id+"' >" +
                                                      "<img src='/resource/image/bear.jpg'>" +
                   "<p class='fio'>Пользователь: "+val.firstName+" "+val.lastName+"</p>" +
            "<p class='dateCreate'>Дата регистрации: "+val.dateCreate+"</p>" +
            "<a href='#' class='accept' id='"+val.user_id+"'>Одобрить</a>" +
            "<a href='#' class='denied' id='"+val.user_id+"'>Отклонить</a>" +
            "</div>";


        });

        html+="<script src='/resource/js/userHandle_queu.js'></script>"

        $('#user_list').html(html);
    });
}
function UsersDenied() {
    $.getJSON("/chatUsersDenied", null, function(data) {

        var html = '';

        $.each(data, function (key, val) {

            html += "<div class='user_box' id='"+val.user_id+"' >" +
            "<img src='/resource/image/bear.jpg'>" +
            "<p class='fio'>Пользователь: "+val.firstName+" "+val.lastName+"</p>" +
            "<p class='dateCreate'>Дата регистрации: "+val.dateCreate+"</p>" +
            "<a href='#' class='accept' id='"+val.user_id+"'>Одобрить</a>" +
            "<a href='#' class='denied' id='"+val.user_id+"'>Отклонить</a>" +
            "</div>";


        });

        html+="<script src='/resource/js/userHandle_denied.js'></script>"

        $('#user_list').html(html);
    });
}

function UsersAccepted() {
    $.getJSON("/chatUsersAccepted", null, function(data) {

        var html = '';

        $.each(data, function (key, val) {

            html += "<div class='user_box' id='"+val.user_id+"' >" +
            "<img src='/resource/image/bear.jpg'>" +
            "<p class='fio'>Пользователь: "+val.firstName+" "+val.lastName+"</p>" +
            "<p class='dateCreate'>Дата регистрации: "+val.dateCreate+"</p>" +
            "<a href='#' class='accept' id='"+val.user_id+"'>Одобрить</a>" +
            "<a href='#' class='denied' id='"+val.user_id+"'>Отклонить</a>" +
            "</div>";


        });

        html+="<script src='/resource/js/userHandle_accept.js'></script>"

        $('#user_list').html(html);
    });
}
