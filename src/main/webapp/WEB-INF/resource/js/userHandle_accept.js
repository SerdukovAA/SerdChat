var token = $('#token').val();
var name = $('#token').attr("name");

/////////////////////////////////////////////////////////////////////////////////////
$('.accept').click(function() {
    AcceptUser($(this).attr('id'));
    return false;
});
$('.denied').click(function() {
    DeniedUser($(this).attr('id'));
    return false;
});
///////////////////////////////////////////////////////////////////////////////////////
function AcceptUser(id){
   $.post("/chatAcceptUser?"+name+"="+token,{id:id}, function() {
   alert( "Пользователь одобрен" );
        UsersAccepted();
    }).fail(function() {
        alert( "Ошибка одобрения" );
    });

}
///////////////////////////////////////////////////////////////////////////////////////
function DeniedUser(id){

    $.post("/chatDeniedUser?"+name+"="+token,{id:id}, function() {

        alert( "Пользователь отклонен" );
        UsersAccepted();
    }).fail(function() {
        alert( "Ошибка отклонения" );
    });

}
