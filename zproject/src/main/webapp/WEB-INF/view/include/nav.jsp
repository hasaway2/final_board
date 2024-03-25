<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#logout').click(function(e) {
			e.preventDefault();
			const html = `
				<form action="/member/logout" method="post">
					<input type="hidden" name="_csrf" value="${_csrf.token}">
				</form>
			`;
			$(html).appendTo($('body')).submit();
		});
	})
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="navbar navbar-expand-sm bg-dark navbar-dark">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">ICIA</a>
			</div>
			<ul class="navbar-nav" id="menu_parent">
			<sec:authorize access="isAnonymous()">
				<li class="nav-item">
					<a class="nav-link" href="/member/login">로그인</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/member/find">아이디/비밀번호 찾기</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/member/join">회원가입</a>
				</li>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_USER')">
				<li class="nav-item">
					<a class="nav-link" href='/member/read'>내정보</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href='/board/write'>글쓰기</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" id='logout' href='#'>로그아웃</a>
				</li>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li>
					<a class="dropdown-item" href="#">관리자 메뉴 </a>
				</li>
			</sec:authorize>
			</ul>
		</div>
	</div>
</body>
</html>



