<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录</title>
<script type="text/javascript" src="assets/jquery/jquery-1.12.4.min.js"></script>
</head>
<body>
	<div class="content">
		<div>
			<label for="userName">用户名：</label><input type="text" name="userName" id="userName" placeholder="请输入用户名" value="admin"
				autocomplete="off" />
		</div>
		<div>
			<label for="password">密码：</label><input type="password" name="password" id="password" placeholder="请输入密码" value="123"
				autocomplete="off" />
		</div>
		<div>
			<button type="submit" id="login">登陆</button>
		</div>
		<div>
			<a href="hello">hello</a>
		</div>
	</div>
	<script>
		$('#login').on('click', function(e) {
			var userName = $.trim($('#userName').val());
			var password = $.trim($('#password').val());
			$.ajax({
				type : "post",
				url : "login",
				data : {
					userName : userName,
					password : password
				},
				dataType : 'json',
				success : function(resp) {
					alert(resp.msg);
					if (resp.success) {
						location.href = "hello";
					}
				}
			});

		})
	</script>
</body>
</html>
