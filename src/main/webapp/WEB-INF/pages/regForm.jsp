<%@ page  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<script src="/resource/js/lib/jquery-2.1.1.min.js"></script>
	<script src="/resource/js/lib/BigInt.js"></script>
	<script src="/resource/js/lib/sha256.js"></script>
	<link href="/resource/css/style_regform.css" rel="stylesheet">
	<title>Регистрация нового пользователя</title>
	<meta name="description" content="This is the description for this page" />
</head>


<body class="body">
<script type="text/javascript" >
 $(document).ready(function(){
	 var ok=1;
	 var regExEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	 var regExCyrillic = /^[а-яА-ЯЁьъ]{3,15}$/;
	 var regExPassword= /^[a-zA-Z0-9_-]{4,256}$/;
	 var $accept = 0;


	 $(':text:first').focus();
	 $("#chbxce").change(function(){
		 if($(this).prop("checked")){
			 $accept=1;
		}else{
			 $accept=0;
		}});


	 $("input[name='firstname']").blur(function () {
		 $("#div1").next(".notvalid").remove();
		 if (!regExCyrillic.test($.trim($("input[name='firstname']").val()))){
			 $("#div1").after("<label class='notvalid' for='email'>Имя введено некорректно.Используйте только кириллицу.</label>");

		 }
	 });
	 $("input[name='lastname']").blur(function () {
		 $("#div2").next(".notvalid").remove();
		 if (!regExCyrillic.test($.trim($("input[name='lastname']").val()))){
			 $("#div2").after("<label class='notvalid' for='email'>Фамилия введена некорректно.Используйте только кириллицу.</label>");

		 }
	 });
	 $("input[name='password']").blur(function () {
		 $("#div3").next(".notvalid").remove();
		 if (!regExPassword.test($.trim($("input[name='password']").val()))){
			 $("#div3").after("<label class='notvalid' for='email'>Пароль введен некорректно.Используйте только латиницу, <br/>цифры, нижнее подчеркивание и тире.</label>");

		 }
	 });

	 $("input[name='password2']").blur(function () {
		 $("#div4").next("label.notvalid").remove();
		 $("#div4").next("label.valid").remove();
		 if ($("input[name='password']").val()!=$("input[name='password2']").val()){
			 $("#div4").after("<label class='notvalid' for='email'>Пароли не совпадают...</label>");
		 }
		 if ($.trim($("input[name='password']").val())==$.trim($("input[name='password2']").val())&&regExPassword.test($.trim($("input[name='password']").val()))){
			 $("#div4").after("<label class='valid' for='email'>Пароли совпадают.</label>");
		 }
	 });

	 $("input[name='email']").blur(function () {
		 $("#div5").next(".notvalid").remove();
		 if (!regExEmail.test($.trim($("input[name='email']").val()))){
			 $("#div5").after("<label class='notvalid' for='email'>Email введен некорректно...</label>");
			 }
	 });



		$('.inputButton').click(function(){
			ok=1;
			event.preventDefault();
			$(".notvalid").remove();



			if (!regExCyrillic.test($.trim($("input[name='firstname']").val()))){
				$("#div1").next
				$("#div1").after("<label class='notvalid' for='email'>Имя введено некорректно.Используйте только кириллицу.</label>");
				ok=0;
			}

			if (!regExCyrillic.test($.trim($("input[name='lastname']").val()))){
				$("#div2").after("<label class='notvalid' for='email'>Фамилия введена некорректно.Используйте только кириллицу.</label>");
				ok=0;
			}
			if (!regExPassword.test($.trim($("input[name='password']").val()))){
				$("#div3").after("<label class='notvalid' for='email'>Пароль введен некорректно.Используйте только латиницу, <br/>цифры, нижнее подчеркивание и тире.</label>");
				ok=0;
			}
			if (!$.trim($("input[name='password']").val())==$.trim($("input[name='password2']").val())){
				$("#div4").after("<label class='notvalid' for='email'>Пароли не совпадают...</label>");
				ok=0;
			}
			if (!regExEmail.test($.trim($("input[name='email']").val()))){
				$("#div5").after("<label class='notvalid' for='email'>Email введен некорректно...</label>");
				ok=0;
			}
			if(!$('#chbxce').prop("checked")) {
				$(".check_label").after("<label class='notvalid' for='email'>Ознакомтесь с соглашением</label>");
				ok=0;
				}

			if($('#chbxce').prop("checked")) {
				$accept=1;
			}
			if(ok==0)return false;

			if(ok==1)sendAjax($accept);
		})
	});

	function sendAjax(accept) {
        var firstname = $.trim($("input[name='firstname']").val());
		var lastname =$.trim( $("input[name='lastname']").val());
		var password = CryptoJS.SHA256($.trim($("input[name='password']").val())).toString();
		var pas2 = CryptoJS.SHA256($.trim($("input[name='password2']").val())).toString();
		var email =$.trim($("input[name='email']").val());
        var myPersonalKey = getKey(pas2);

        var user_mix_key = bigInt2str(powMod(str2bigInt("12345678912345678912",10,0),str2bigInt(myPersonalKey,10,0),str2bigInt("4561236325411634521425",10,0)),10);

		$.ajax({
			url: "/newUser?${_csrf.parameterName}=${_csrf.token}&accept="+accept+"&pas2="+pas2,
			type: 'POST',
			dataType: 'json',
			data:JSON.stringify({firstName:firstname,lastName:lastname,password:password, email:email,user_mix_key:user_mix_key}),
			contentType: 'application/json',
			mimeType: 'application/json',
			success: function(data) {
				var html ="<script src='/resource/js/regutil.js'/>";
				if(parseInt(data.status)==3001){
				html +="<div class='messBack'></div><div class='pMess'><img class='krest' src='/resource/image/krest.png'><p>"+data.content+".<br> Ожидайте подтверждения Вашей учетной записи администратором.</p></div>";
			$('.body').append(html);

				localStorage["myPersonalKey"]=myPersonalKey;
			 	}
				if(parseInt(data.status)<3000){
					html +="<div class='messBack'></div><div class='nMess'><img class='krest' src='/resource/image/krest.png'><p>"+data.content+"</p></div>";
					$('.body').append(html);
					$(".messBack").height($(document).height());


				}
			},
			error:function(data) {
				html +="<div class='messBack'></div><div class='nMess'><img class='krest' src='/resource/image/krest.png'><p>"+data.content+". Код ошибки - "+data.status+"</p></div>";
				$('body .body').append(html);

		    	}
		});
	}
	function getKey(pas) {
        var key= "3141592653589793238462643383";
		for (var i = 0; i < pas.length-1; i++) {
			key = mult(str2bigInt(key, 10, 0), str2bigInt(pas.charCodeAt(i).toString(), 10, 0));
			key = bigInt2str(key, 10);
		}
		return bigInt2str(key,10);
	}
</script>
<div class="formBox">
 <h3>Регистрация</h3>
 		<form  class="regForm"  action="#">
					<div class="">
						<h2 class ="form_title">Заполните информацию о себе</h2></div>
								
			<div id ='div1' class='inputDiv'><input class='inp' type="text" name="firstname" placeholder ='Имя' /></div>
								
			<div id ='div2'class='inputDiv'><input class='inp' type="text" name="lastname" placeholder ='Фамилия'/></div>
								
			<div id ='div3' class='inputDiv'><input class='inp' type="password" name="password" placeholder ='Ваш новый пароль'/></div>
							
			<div id ='div4' class='inputDiv'><input class='inp' type="password" name="password2" placeholder ='Повторите пароль'/></div>
				  		
			<div id ='div5' class='inputDiv'><input class='inp' type="email" id ='email' name="email" placeholder ='Email'/></div>
				<label class ="email_label" for="email">*Адрес электронной почты будет использоваться в качестве логина</label>

			<input class="inputButton"  type="submit" value="Отправить" />
			<p class ="check_label"><input  type="checkbox"  id="chbxce" id='accept' name="accept" value="ok"> <label for="accept">Я согласен с
				<span id ="a">соглашением</span> использования данного веб приложения</label>. Его пока нет просто тапни галку.</p>
		</form>

   </div>

</body>
</html>