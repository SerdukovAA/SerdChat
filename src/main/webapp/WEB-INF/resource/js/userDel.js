var token = $('#token').val();
var name = $('#token').attr("name");
var user_id= $('.userInfo').attr("id");
$('.removeUser').click(function() {
    DelUser(user_id, $(this).attr('id'));
    return false;
});

function DelUser(user_id,friend_id){

    $.ajax({
        url: "/chatDelUserInFriends?"+name+"="+token,
        type: 'POST',
        data: {user_id:user_id,friend_id:friend_id},
        success: function(data) {

            var html ="<script src='/resource/js/userutil.js'/>";

            if(parseInt(data.status)>=3000){
                html +="<div class='messBack'></div><div class='pMess'><img class='krest' src='/resource/image/krest.png'><p>"+data.content+".</p></div>";

                $('.body').append(html);


            }
            if(parseInt(data.status)<3000){
                html +="<div class='messBack'></div><div class='nMess'><img class='krest' src='/resource/image/krest.png'><p>"+data.content+"</p></div>";

                $('.body').append(html);


            }

            FrendList();

        },
        error:function(data) {
            alert("error: "+data.content+" status: "+data.status+" er:");
            FrendList();
        }
    });


}
