<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
	const msg = '${msg}';
	console.log(msg);
	if(msg!=='')
		alert(msg);
	
	$(document).ready(function() {
		$('#logout').click(function(e) {
			e.preventDefault();
			const html = `
				<form action="/member/logout" method="post">
				</form>
			`;
			$(html).appendTo($('body')).submit();
		});
	})
</script>
</head>
<body>
	<p>루트 페이지입니다</p>
	<sec:authorize access="hasRole('ROLE_USER')">
		<button id="logout">로그아웃</button>
	</sec:authorize>
</body>
</html>