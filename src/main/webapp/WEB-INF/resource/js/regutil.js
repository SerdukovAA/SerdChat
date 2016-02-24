/**
 * Created by а д м и н on 04.01.2015.
 */
$('.nMess > .krest').click(function(){
    $('.messBack').remove();
    $('.nMess').remove();
    });

$('.pMess > .krest').click(function(){
    $('.messBack').remove();
    $('.pMess').remove();
    location.href = '/login';
});
