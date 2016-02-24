$('.chatBegin').click(function(){
    var friend_id = $(this).attr("id");
    var user_id = $('.userInfo').attr("id");
    chatBegin(user_id, friend_id);
});

function chatBegin(user_id, friend_id, user_mix_key) {
    var token = $('#token').val();
    var name = $('#token').attr("name");
    $.post("/chatBegin?" + name + "=" + token, {user_id: user_id, friend_id: friend_id},function(data){

     if(parseInt(data)==0) alert( "Ошибка добавления" );
     else location.href = '/chat_messager?r='+data;
   }).fail(function() {
     alert( "Ошибка добавления" );
 });
}



