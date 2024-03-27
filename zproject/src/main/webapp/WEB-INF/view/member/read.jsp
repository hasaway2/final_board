<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
	const email = '${member.email}'
	$(document).ready(function() {
		$('#change-email').on('click', function() {
			const newEmail = $('#email').val();
			if(email===newEmail) {
				alert('이메일 변경사항이 없습니다');
				return false;
			}
			const params = {
				_csrf: '${_csrf.token}',
				email : newEmail
			};
			
			$.ajax({
				url: '/member/change-email',
				method: 'post',
				data: params,
				success:function() {
				}, error: function() {
					alert('비밀번호를 변경하지 못했습니다 ');
				}
			});
		});
		
		$('#quit').on('click', function() {
			const html = `
				<form action="/member/quit" method="post">
				</form>
			`;
			$(html).appendTo($('body')).submit();
		});
	});
</script>
<title>내정보</title>
</head>
<body>
	<div id="page">
		<header>
			<jsp:include page="/WEB-INF/view/include/header.jsp" />
		</header>
		<nav>
			<jsp:include page="/WEB-INF/view/include/nav.jsp" />
		</nav>
		<main>
			<aside>
				<jsp:include page="/WEB-INF/view/include/aside.jsp" />
			</aside>
			<section>
				<table class="table table-hover">
					<colgroup>
						<col width="40%">
						<col width="60%">
					</colgroup>
					<tbody>
						<tr>
							<td colspan="2">
								<img src='${member.profile}' height='200px'>
							</td>
						</tr>
						<tr>
							<td>아이디</td>
							<td>${member.username}</td>
						</tr>
						<tr>
							<td>이메일</td>
							<td>
								<input type="email" id="email" name="email" value="${member.email}"> 
								<button class="btn btn-outline-danger" id="change-email">수정하기</button>
							</td>
						</tr>
						<tr>
							<td>생일</td>
							<td>${member.birthday}</td>
						</tr>
						<tr>
							<td>가입일(가입기간)</td>
							<td>${member.joinday} (${member.days}일 경과)</td>
						</tr>
					</tbody>
				</table>
				<button class="btn btn-outline-danger" id="quit">탈퇴</button>
			</section>
			<aside>
				<jsp:include page="/WEB-INF/view/include/aside.jsp" />
			</aside>
		</main>
		<footer>
			<jsp:include page="/WEB-INF/view/include/footer.jsp" />
		</footer>
	</div>
</body>
</html>