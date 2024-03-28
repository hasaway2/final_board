<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>${msg }</p>
	비밀번호 확인
	<form action="/member/check-password" method="post">
		<input type="password" name="password">
		<button>확인</button>
	</form>
</body>
</html>