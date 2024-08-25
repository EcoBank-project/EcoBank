document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form'); // form 태그
    let elInputPassword = document.querySelector('#password1');
    let elInputPasswordRetype = document.querySelector('#password2');
    let elStrongPasswordMessage = document.querySelector('.strongPassword-message');

    // 비밀번호 불일치 메시지 추가
    let elPasswordMismatchMessage = document.createElement('div');
    elPasswordMismatchMessage.classList.add('password-mismatch');
    elPasswordMismatchMessage.style.display = 'none';
    elPasswordMismatchMessage.style.color = 'red';
    elPasswordMismatchMessage.textContent = '비밀번호가 일치하지 않습니다.';

    // 비밀번호 강도 메시지 추가 (강도가 약할 때 보여줌)
    let elPasswordWeakMessage = document.createElement('div');
    elPasswordWeakMessage.classList.add('password-weak');
    elPasswordWeakMessage.style.display = 'none';
    elPasswordWeakMessage.style.color = 'red';
    elPasswordWeakMessage.textContent = '비밀번호는 8자리 이상이어야 하며, 영문, 숫자, 특수문자를 포함해야 합니다.';

    elInputPassword.parentNode.appendChild(elPasswordWeakMessage); // 비밀번호 필드 아래에 추가
    elInputPasswordRetype.parentNode.appendChild(elPasswordMismatchMessage); // 비밀번호 확인 필드 아래에 추가

    let isCodeVerified = false; // 이메일 인증 여부

    // 비밀번호 강도 체크 함수
    function strongPassword(str) {
        return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
    }

    // 비밀번호 유효성 및 일치 실시간 확인
    function validatePasswordFields() {
        const password1 = elInputPassword.value;
        const password2 = elInputPasswordRetype.value;

        // 1. 비밀번호 일치 여부 체크
        if (password2.length !== 0) {
            if (password1 !== password2) {
                elPasswordMismatchMessage.style.display = 'block'; // 불일치 메시지 표시
                elStrongPasswordMessage.classList.add('hide'); // 비밀번호가 일치하지 않으면 강도 메시지 숨김
                elPasswordWeakMessage.style.display = 'none'; // 강도 메시지 숨김
                return; // 비밀번호가 일치하지 않으면 강도 체크를 하지 않음
            } else {
                elPasswordMismatchMessage.style.display = 'none'; // 일치 시 메시지 숨김
            }
        } else {
            elPasswordMismatchMessage.style.display = 'none'; // 비밀번호 확인 입력이 없을 시 메시지 숨김
            elPasswordWeakMessage.style.display = 'none'; // 강도 메시지도 숨김
            return; // 비밀번호 확인이 비어있으면 강도 체크를 하지 않음
        }

        // 2. 비밀번호 강도 체크
        if (password1.length !== 0) {
            if (strongPassword(password1)) {
                elStrongPasswordMessage.classList.add('hide'); // 강도 메시지 숨김
                elPasswordWeakMessage.style.display = 'none'; // 강도 약한 메시지 숨김
            } else {
                elStrongPasswordMessage.classList.remove('hide'); // 강도 메시지 표시
                elPasswordWeakMessage.style.display = 'block'; // 강도가 약할 때 메시지 표시
            }
        } else {
            elStrongPasswordMessage.classList.add('hide'); // 빈 입력 시 강도 메시지 숨김
            elPasswordWeakMessage.style.display = 'none'; // 빈 입력 시 강도 약한 메시지 숨김
        }
    }

    elInputPassword.addEventListener('input', validatePasswordFields);
    elInputPasswordRetype.addEventListener('input', validatePasswordFields);

    // 이메일 유효성 검사 함수
    function emailCheck(email_address) {
        const email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        return email_regex.test(email_address);
    }

	// 이메일 유효성 검사 함수
	function emailCheck(email_address) {
		const email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		return email_regex.test(email_address);
	}

	function validatePassword() {
		const password1 = document.getElementById("password1").value;
		const password2 = document.getElementById("password2").value;
		const isValid = password1 === password2;
		return isValid;
	}

	function validateNickname() {
		const nickname = document.getElementById("nickname").value;
		const nicknameError = document.getElementById("nickname-error");
		const isValid = nickname.length >= 6 && nickname.length <= 20;
		nicknameError.style.display = isValid ? "none" : "block";
		return isValid;
	}

	function validateTell() {
		console.log("validateTell function called"); // 함수 호출 확인

		const tell = document.getElementById("tell").value.trim();
		const tellError = document.getElementById("tell-error");
		const phoneRegex = /^[0-9]{9,11}$/;
		const isValid = phoneRegex.test(tell);
		tellError.style.display = isValid ? "none" : "block";
		return isValid;
	}

	function validateForm() {
		const isPasswordValid = validatePassword();
		const isNicknameValid = validateNickname();
		const isTellValid = validateTell();
		return isPasswordValid && isNicknameValid && isTellValid && isCodeVerified;
	}

	form.addEventListener('submit', function(event) {
		if (!validateForm()) {
			event.preventDefault(); // 폼이 유효하지 않으면 제출을 막음
			alert('폼을 올바르게 입력해주세요.');
		}
	});

	// 이메일 중복 확인
	document.getElementById('sameIdCheckBtn').addEventListener('click', function() {
		const id = document.getElementById('useid').value;
		const resultDiv = document.getElementById('label1');
		if (!emailCheck(id)) {
			resultDiv.style.color = 'red';
			resultDiv.innerHTML = '유효하지 않은 이메일 주소입니다.';
			return;
		}

		if (id.length === 0) {
			resultDiv.style.color = 'red';
			resultDiv.textContent = '아이디를 입력해주세요.';
			return;
		}

		$.ajax({
			url: '/user/api/check-duplicate',
			type: 'GET',
			data: { useId: id },
			dataType: 'json',
			success: function(result) {
				if (result.exists) {
					resultDiv.style.color = 'red';
					resultDiv.textContent = '이미 사용중인 ID입니다.';
				} else {
					resultDiv.style.color = 'black';
					resultDiv.textContent = '사용 가능한 ID 입니다.';
					document.getElementById('sendBtn').disabled = false;
				}
			},
			error: function() {
				resultDiv.style.color = 'red';
				resultDiv.textContent = '서버 오류가 발생했습니다.';
			}
		});
	});

	// 닉네임 중복 확인
	document.getElementById('sameNameCheckBtn').addEventListener('click', function() {
		const nickname = document.getElementById('nickname').value;
		const resultDiv = document.getElementById('label2');

		if (nickname.length < 6 || nickname.length > 20) {
			resultDiv.style.color = 'red';
			resultDiv.textContent = '닉네임은 6자 이상 20자 이하이어야 합니다.';
			return;
		}

		$.ajax({
			url: '/user/api/check-nickname',
			type: 'GET',
			data: { nickname: nickname },
			dataType: 'json',
			success: function(result) {
				if (result.exists) {
					resultDiv.style.color = 'red';
					resultDiv.textContent = '이미 사용중인 닉네임입니다.';
				} else {
					resultDiv.style.color = 'black';
					resultDiv.textContent = '사용 가능한 닉네임입니다.';
				}
			},
			error: function() {
				resultDiv.style.color = 'red';
				resultDiv.textContent = '서버 오류가 발생했습니다.';
			}
		});
	});

	// 인증번호 발송
	window.sendNumber = function() {
		const useidValue = $("#useid").val();

		$("#mail_number").css("display", "block");
		$.ajax({
			url: "/user/mail",
			type: "post",
			dataType: "json",
			data: { mail: useidValue },
			success: function(data) {
				Swal.fire({
					icon: 'success',
					title: '인증번호 발송',
					text: '인증번호가 발송되었습니다.',
					confirmButtonColor: "#32C36C",
				});
				$("#Confirm").val(data);
			},
			error: function() {
				Swal.fire({
					icon: 'error',
					title: '인증번호 발송 실패',
					text: '인증번호 발송에 실패했습니다.',
					confirmButtonColor: "#32C36C",
				});
			}
		});
	}

	// 인증번호 확인
	window.confirmNumber = function() {
		const number1 = $("#number").val();
		const number2 = $("#Confirm").val();

		if (number1 === number2) {
			Swal.fire({
				icon: 'success',
				title: '인증 성공',
				text: '이메일 인증에 성공했습니다.',
				confirmButtonColor: "#32C36C",
			});
			isCodeVerified = true;
			console.log("isCodeVerified : " + isCodeVerified);
			$("#confirmBtn").css({
				"background-color": "#32C36C",
				"color": "#fff",
				"border": "none"
			}).text("인증 성공").prop("disabled", true);
			$("#sendBtn").prop("disabled", true); // 인증번호 재발송 비활성화
		} else {
			Swal.fire({
				icon: 'error',
				title: '인증 실패',
				text: '인증번호가 일치하지 않습니다.',
				confirmButtonColor: "#32C36C",
			});
			isCodeVerified = false;
		}
	}
});