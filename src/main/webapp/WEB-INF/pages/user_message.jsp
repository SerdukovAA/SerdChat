<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE HTML>
<html>
<head>

   <meta charset="utf-8">
	<script src="/resource/js/lib/jquery-2.1.1.min.js"></script>
	<script src="/resource/js/lib/rabbit.js"></script>
	<script src="/resource/js/lib/sha256.js"></script>
	<script src="/resource/js/lib/BigInt.js"></script>
	<link href="/resource/css/style_messager.css" rel="stylesheet">
   <title>Переписка</title>
	<meta name="description" content="This is messager" />


</head>

<body class="body">

<div class="header">
	<img id='logo' src="/resource/image/logohome.png">
	<div class="home_ref"><a href="/login?logout">Выйти</a></div>
</div>
<div class="wraper">

	<div class="messagePanel" id="messagePanel">

	</div>
	<div class="messageinputer">
	<textarea spellcheck="true" name="inputPanel" id="messageinput" class="messageinput" placeholder="Введите Ваше сообщение..." rows="5" cols="100" autofocus></textarea>
	<input class="submitButton" onclick="send();"  type="submit" value="Отправить">
    </div>
</div>

<input type="hidden" id="token" name="${_csrf.parameterName}"  value="${_csrf.token}" />
 						<input type="hidden"  id="avatar" class="userManeInput" value="${avatar}"/>
						<input type="hidden" id="room_uniq_id" class="userManeInput" value="${room_uniq_id}"/>
						<input type="hidden" id="user_id" class="userManeInput" value="${user_id}"/>
						<input type="hidden"  id="firstname" class="userManeInput" value="${firstname}"/>
						<input type="hidden" id="lastname" class="userManeInput" value="${lastname}"/>

<script type="text/javascript">

	var webSocket;
	var kd=0;
	var f=0;



	$(document).ready(function() {

		getKey();

		getMessageFromDate(0);


		openSocket();


		$("#messagePanel").scroll(function(){
			if(document.getElementById("messagePanel").scrollTop ==0 && f==0)
			{
				f=1;
				kd+= 1;
				getMessageFromDate(kd);

			}

		});

		$('#logo').click(function(){
			location.href = '/chat_user_page';
		});



		$(window).unload(function () {
			closeSocket();

		});


		$( "#messageinput" ).focus(function() {
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

	function openSocket(){
		// Ensures only one connection is open at a time
		if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
			writeResponse("WebSocket is already opened.");
			return;
		}
		// Create a new instance of the websocket
		webSocket = new WebSocket("ws://"+location.host+"/echo/${room_uniq_id}");

		/**
		 * Binds functions to the listeners for the websocket.
		 */
		webSocket.onopen = function(event){

			if(event.data === undefined)
				return;

			writeResponse(event);
			var block = document.getElementById("messagePanel");
			block.scrollTop = block.scrollHeight;
		};

		webSocket.onmessage = function(event){
			writeResponse(event);
			var block = document.getElementById("messagePanel");
			block.scrollTop = block.scrollHeight;
		};

		webSocket.onclose = function(event){
			writeResponse("Connection closed");
			var block = document.getElementById("messagePanel");
			block.scrollTop = block.scrollHeight;
		};
	}



	function send(){
		var user_id = document.getElementById('user_id').value;
	    var content = document.getElementById('messageinput').value;
		var attachment = document.getElementById('messageinput').value;



if(content.length>0&&content.trim()!="") {
	var encrypted = CryptoJS.Rabbit.encrypt(content, localStorage["RoomMixCryptoKey"]).toString();
   	var json = {
		content: encrypted,
		attachment: attachment,
		user_id: user_id

	};


	webSocket.send(JSON.stringify(json));
	document.getElementById("messageinput").value = '';
	return false;
}else {

	return false;
}


	}



	function closeSocket(){
		webSocket.close();
	}





	function writeResponse(event){
		var json = JSON.parse(event.data);
		var decrypted = CryptoJS.Rabbit.decrypt(json.content, localStorage["RoomMixCryptoKey"]).toString(CryptoJS.enc.Utf8);
		var parts = String(json.dateTime).split(/[- :]/);
		var time = parts[3]+":"+parts[4]+":"+parts[5];
		mess ="<div class='message'>" +
		"<input type='hidden' class='messageSendlerInfo' value='"+json.user_id+"'/>"+
		"<div class='imgDiv'>"+
		"<img src='"+json.avatar+"'></div>" +
		"<div class='messageContent'><h2>"+json.firstname+" "+json.lastname+"</h2><p class='content'>"+decrypted+"</p><p class='mesdate'>"+time+"</p></div>" +
		" </div>";
		$('.messagePanel').append(mess);

	}



	function get_date(string) {
		var date = new Date(string);
		var now = new Date();
		var parts = String(date).split(/[- :]/);
		if(date.getDate()==now.getDate() &&date.getMonth()==now.getMonth()&&date.getYear()==now.getYear()){

			var t = parts[4]+":"+parts[5]+":"+parts[6];
			return t;
		}else {
			var d = parts[2]+" "+parts[1]+" "+parts[3];
			return d;
		}


	}


	function   getMessageFromDate(kd) {
		var room_id = $('#room_uniq_id').val();
		$.getJSON("/chatMessageFromDate?${_csrf.parameterName}=${_csrf.token}",{room_uniq_id:room_id, k:kd}, function(data) {
			var messageList="";

			$.each(data, function (key, val) {

				//	var text = val.content;
					var text= CryptoJS.Rabbit.decrypt(val.content, localStorage["RoomMixCryptoKey"]).toString(CryptoJS.enc.Utf8);
					var date = get_date(val.dateTime);
					messageList += mess = "<div class='message'>" +
					"<input type='hidden' class='messageSendlerInfo' value='" + val.subject.user_id + "'/>" +
					"<div class='imgDiv'>" +
					"<img src='" + val.subject.avatar + "'></div>" +
					"<div class='messageContent'><h2>" + val.subject.firstName + " " + val.subject.lastName + "</h2><p class='content'>"+text+"</p><p class='mesdate'>" + date + "</p></div>" +
					" </div>";

			});


			$('.messagePanel').prepend(messageList);




			f=0;

			if(k==0){
				var block = document.getElementById("messagePanel");
				block.scrollTop = block.scrollHeight;
			}else{
				var block = document.getElementById("messagePanel");
				block.scrollTop = 1;
			}

		});
	}

	function   getKey() {
		var room_id = $('#room_uniq_id').val();
		var user_id = document.getElementById('user_id').value;
		$.getJSON("/chatRoomKey?${_csrf.parameterName}=${_csrf.token}",{room_uniq_id:room_id,user_id:user_id}, function(data) {

		localStorage["MixCryptoKey"] = data.user_mix_key;
//
		localStorage["RoomMixCryptoKey"]= bigInt2str(powMod(str2bigInt(localStorage["MixCryptoKey"],10,0),str2bigInt(localStorage["myPersonalKey"],10,0),str2bigInt("45612363254116345214253697854",10,0)),10);
			//alert("Ключ Юзера"+localStorage["RoomMixCryptoKey"]);

		});

	}




</script>
</body>

</html>