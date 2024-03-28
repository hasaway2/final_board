<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>Insert title here</title>
<script>
	$(function() {
		// 자바스크립트에서 el에 접근하는 방법
		const 원래이메일 = '${member.email}';
		
		$('#resign').on('click', function() {
			const html = `
				<form action="/member/delete" method="post">
				</form>
			`;
			$(html).appendTo($('body')).submit();
		});
		
		$('#change-email').on('click', function() {
			const 새이메일 = $('#email').val();
			if(새이메일==원래이메일) {
				alert("이메일 변경사항이 없습니다");
				return false;
			}
			
			// 서버로 넘기는 값들로 js객체를 만든다 
			const params = {
					email : 새이메일
			}
			$.ajax({
				url: '/member/change-email',
				data: params,
				method: 'post',
				success:function() {
					alert("이메일을 변경했습니다");
				}, error:function() {
					alert("이메일을 변경하지 못했습니다");
				}
			})
		});
	})
</script>
</head>
<body>
	<img src="${member.profile }" height="240px">
	<ul>
		<li>
			<input type="text" id="email" name="email" value="${member.email }">
			<button id="change-email">변경</button>
		</li>
		<li>가입일 : ${member.joinday}</li>
		<li>가입기간 : ${member.days }</li>
	</ul>
	<button id="resign">탈퇴</button>
</body>
</html>