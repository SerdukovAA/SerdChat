<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE HTML>
<html>
<head>

   <meta charset="utf-8">
	<script src="/resource/js/lib/jquery-2.1.1.min.js"></script>
	<script src="/resource/js/lib/BigInt.js"></script>
	<script src="/resource/js/user.js"></script>
	<link href="/resource/css/style_userPage.css" rel="stylesheet">
   <title>Личная страница пользователя</title>
	<meta name="description" content="This is user page" />

</head>
<script type="text/javascript" >
	$(document).ready(function(){


		FrendList();
		$('#returnUserContacts').click(function(){
			FrendList();
		});
		$('.searchButton').click(function(){
			$('.resultBox').html(" ");
			SearchUsersFriendsAndNot();
		});

		$('#change_avatar').click(function(){
			$('.body').append("<div class='messBack'></div><script src='/resource/js/userutil.js'/>");
			$('.avForm').css("visibility","visible");
		});

		$("input[name='search']").focus(function() {
			document.onkeyup = function (e) {
				e = e || window.event;
				if (e.keyCode === 13) {
					send();

					//alert("Вы нажали Enter!В фокусе");
				}

				// Отменяем действие браузера

				return false;

			}
		});



	});


</script>

<body class="body">
<input type="hidden" id="token" name="${_csrf.parameterName}"  value="${_csrf.token}" />

<img id='logo' src="/resource/image/logohome.png">
<div class="home_ref"><a href="/login?logout">Выйти</a></div>

<div class="wraper">




	<div class="userBox">
		<div class='divAva'>
			<input type="hidden" id="${user_id}" name="userInfo" class="userInfo" value="${username}"/>
			<img  src='${avatar}'>
		</div>

		<div class="searchBox">

			<div class ="poisk">
				<input class="searchButton"  type="submit" value="" />
				<input type="text" name='search' placeholder ='logi45@gmail.com'>

			</div>
			<h3>Найти контакт:</h3>
		</div>

		<div class='resultBox'>


		</div>
		<div class="change_avatar"><p id="change_avatar">Сменить аватар</p></div>
		<div class='divMenu'>
			<h2>Меню</h2>
			<ul>
				<li id="returnUserContacts">Мои контакты</li>

			</ul>

		</div>

		<form class='avForm'  method="POST" action="/upload?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data"><img class='krest' src='/resource/image/krest.png'>
			<p>Загрузить аватар</p>
			<input type="file" name="file" value="Выберите изоражение"><br/>
			<input type="hidden" name="user_id" value="${user_id}"><br />
			<input type="submit" value="Загрузить">

		</form>


	</div>



</div>



</body>
</html>