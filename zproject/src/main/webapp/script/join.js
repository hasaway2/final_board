	function showProfile() {
		const profile = $('#profile')[0].files[0];
		// $('#profile')[0] -> <input type='file' id='profile'>
		// file 속성을 가진 input 요소는 사용자가 선택한 파일을 files라는 배열 속성으로 저장한다
		// 파일을 하나 선택했다면 files[0]
	
		const maxSize = 1024 * 1024;
		if (profile.size > maxSize) {
			alert('사진 크기는 1MB이하여야 합니다');
			$('#profile')[0].files[0] = '';
		}
	
		let reader = new FileReader();
		// 파일을 로딩했을 때 동작할 콜백함수 지정
		reader.onload = function() {
			$('#show-profile').css('display', 'inline').attr('src', reader.result);
		}
	
		// 파일을 로딩한다
		reader.readAsDataURL(profile);
	}

	const patterns = {
    username: [/^[A-Za-z0-9]{6,10}$/, '아이디는 영숫자 6~10자입니다'],
    password: [/^[A-Za-z0-9]{8,10}$/, '비밀번호는 영숫자 8~10자입니다'],
    email:[/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i, '잘못된 이메일입니다'],
		birthday: [/.+/, '필수입력입니다']
  }

	function usernameCheck() {
		const $element = $('#username');
		const value = $element.val();
		$element.next().text('');
		if(patterns.username[0].test(value)==false) {
			$element.next().text(patterns.username[1]).addClass('error');
			return false;
		}
		
		$.ajax({
			url: '/member/id-check',
			data: 'username=' + value,
			success:function() {
				$element.next().text('사용할 수 있습니다');
				return true;
			}, error:function() {
				$element.next().text('사용중인 아이디입니다').addClass('error');
				return false;
			}
		});			
	}

	function passwordCheck() {
		const $element = $('#password');
		const value = $element.val();
		$element.next().text('');

		if(patterns.password[0].test(value)==false) {
			$element.next().text(patterns.password[1]).addClass('error');
			return false;
		}
		return true;
	}

	function password2Check() {
		const $password = $('#password');
		const $element = $('#password2');
		$element.next().text('');

		if($element.val()==='') {
		  $element.next().text('확인을 위해 새비밀번호를 다시 입력해주세요').addClass('error');
		  return false;
	  }
		if($password.val()!==$element.val()) {
			$element.next().text('새비밀번호가 일치하지 않습니다').addClass('error');
			return false;
		}
		return true;
	}

	function emailCheck() {
		const $element = $('#email');
		const value = $element.val();
		$element.next().text('');

		if(patterns.email[0].test(value)==false) {
			$element.next().text(patterns.email[1]).addClass('error');
			return false;
		}
		return true;
	}

	function birthdayCheck() {
		const $element = $('#birthday');
		const value = $element.val();
		$element.next().text('');

		if(patterns.birthday[0].test(value)==false) {
			$element.next().text(patterns.birthday[1]).addClass('error');
			return false;
		}
		return true;
	}

	$(document).ready(function() {
		$('#profile').on('change', showProfile);
		
		$('#username').on('blur', usernameCheck);
		$('#password').on('blur', passwordCheck);
		$('#password2').on('blur', password2Check);
		$('#email').on('blur', emailCheck);
		$('#birthday').on('blur', birthdayCheck);

		$('#join').on('click', function() {
			const result = usernameCheck() && passwordCheck() && password2Check() && emailCheck() && birthdayCheck();
			if(result==false)
				return;
			$('#join-form').submit();
		})
	})