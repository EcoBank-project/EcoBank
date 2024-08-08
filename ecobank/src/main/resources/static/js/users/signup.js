document.addEventListener('DOMContentLoaded', function() {
    // 폼 제출 전 유효성 검증
    document.querySelector('form').addEventListener('submit', function(e) {
        if (!validateUseId() || !validatePassword() || !validateNickname() || !validateTell()) {
            e.preventDefault(); // 폼 제출 방지
        }
    });
});

function validateUseId() {
    var useId = document.getElementById("useid").value.trim();
    var useIdError = document.getElementById("useId-error");
    var useIdDuplicateError = document.getElementById("useId-duplicate-error");
    var isValid = true;

    // 기본 필수 필드 검증
    if (useId === "") {
        useIdError.style.display = "block";
        useIdDuplicateError.style.display = "none";
        isValid = false;
    } else {
        useIdError.style.display = "none";
        // 중복 체크
        checkDuplicateId(useId).then(isTaken => {
            if (isTaken) {
                useIdDuplicateError.style.display = "block";
                isValid = false;
            } else {
                useIdDuplicateError.style.display = "none";
            }
        });
    }

    return isValid;
}

function validatePassword() {
    var password1 = document.getElementById("password1").value;
    var password2 = document.getElementById("password2").value;
    var passwordError = document.getElementById("password-error");
    var isValid = password1 === password2;

    passwordError.style.display = isValid ? "none" : "block";
    return isValid;
}

function validateNickname() {
    var nickname = document.getElementById("nickname").value.trim();
    var nicknameError = document.getElementById("nickname-error");
    var isValid = nickname.length >= 6 && nickname.length <= 20;

    nicknameError.style.display = isValid ? "none" : "block";
    return isValid;
}

function validateTell() {
    var tell = document.getElementById("tell").value.trim();
    var tellError = document.getElementById("tell-error");
    var isValid = tell !== "";

    tellError.style.display = isValid ? "none" : "block";
    return isValid;
}

function checkDuplicateId(useId) {
    return new Promise((resolve) => {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/check-duplicate?useId=" + encodeURIComponent(useId), true);
        xhr.onload = function() {
            if (xhr.status === 200) {
                var isTaken = JSON.parse(xhr.responseText);
                resolve(isTaken);
            } else {
                resolve(false); // 오류 발생 시 기본적으로 중복이 아닌 것으로 간주
            }
        };
        xhr.send();
    });
}

function sendNumber() {
	const useidValue = $("#useid").val();
	console.log(useidValue); // useid 값을 콘솔에 출력

	$("#mail_number").css("display", "block");
	$.ajax({
		url: "/user/mail",
		type: "post",
		dataType: "json",
		data: { mail: useidValue },
		success: function(data) {
			alert("인증번호 발송");
			$("#Confirm").val(data); // 서버에서 받은 인증번호를 숨겨진 필드에 설정
			isCodeVerified = false; // 초기 상태로 설정
		},
		error: function() {
			alert("인증번호 발송에 실패했습니다.");
		}
	});
}

function confirmNumber() {
	const number1 = $("#number").val();
	const number2 = $("#Confirm").val();
	console.log(number1);
	console.log(number2);

	if (number1 == number2) {
		alert("인증되었습니다.");
		isCodeVerified = true;
		checkFormValidity(); // 폼 유효성 검사
	} else {
		alert("번호가 다릅니다.");
		isCodeVerified = false;
	}
}