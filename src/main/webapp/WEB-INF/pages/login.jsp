<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
  <meta charset="utf-8">
	<script src="/resource/js/lib/jquery-2.1.1.min.js"></script>
	<script src="/resource/js/lib/sha256.js"></script>
	<script src="/resource/js/lib/BigInt.js"></script>
	<script src="/resource/js/login.js"></script>
  <link href="/resource/css/style_login.css" rel="stylesheet">
  <title>SerChat</title>
</head>
<body onload='document.loginForm.username.focus();'>

<div class="loginBox">
	<img id='logo' src="/resource/image/logohome.png">
  <h3>Login</h3>
  <form autocomplete="off" class="loginForm" name='loginForm' action="j_spring_security_check" 	method='POST'>

	<div id ='div1' class="inputDiv"><input autocomplete="off" class='inp' type='text' name='username' placeholder ='Email'></div>

	<div id ='div2' class="inputDiv"><input autocomplete="off" class='inp' type='password' name='pas' placeholder ='Пароль'/></div>
			<br/>
			<input class="inputButton" name="submit" type="submit" value="ВОЙТИ"/>

						<p class="a"><a href='/rememberPas'>Забыли пароль?</a></p>

	   <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
	   <input type="hidden" name="password"  value=""/>

</form>
						<p class="p">У меня пока нет аккаунта.</p>
			<p class="link"> Хочу <a href='/registration'>зарегистрироваться!</a></p>
</div>

<c:if test="${not empty error}">
	<div class="error">${error}</div>
</c:if>
<c:if test="${not empty msg}">
	<div class="msg">${msg}</div>
</c:if>

</body>
</html>