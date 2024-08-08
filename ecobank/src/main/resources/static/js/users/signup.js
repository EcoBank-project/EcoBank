let isCodeVerified = false; // 인증번호 확인 상태 변수

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const submitButton = document.getElementById('submit-button');

    function validatePassword() {
        const password1 = document.getElementById("password1").value;
        const password2 = document.getElementById("password2").value;
        const passwordError = document.getElementById("password-error");
        const isValid = password1 === password2;

        passwordError.style.display = isValid ? "none" : "block";
        return isValid;
    }

    function validateNickname() {
        const nickname = document.getElementById("nickname").value.trim();
        const nicknameError = document.getElementById("nickname-error");
        const isValid = nickname.length >= 6 && nickname.length <= 20;

        nicknameError.style.display = isValid ? "none" : "block";
        return isValid;
    }

    function validateTell() {
        const tell = document.getElementById("tell").value.trim();
        const tellError = document.getElementById("tell-error");
        const isValid = tell !== "";

        tellError.style.display = isValid ? "none" : "block";
        return isValid;
    }

    function validateForm() {
        const isPasswordValid = validatePassword();
        const isNicknameValid = validateNickname();
        const isTellValid = validateTell();

        // 모든 유효성 검사 통과 시 회원가입 버튼 활성화
        submitButton.disabled = !(isPasswordValid && isNicknameValid && isTellValid && isCodeVerified);
    }

    // 폼 제출 전 유효성 검증
    form.addEventListener('submit', function(e) {
        if (!validatePassword() || !validateNickname() || !validateTell() || !isCodeVerified) {
            e.preventDefault(); // 폼 제출 방지
        }
    });

    // ID 중복 확인
    document.getElementById('sameCheckBtn').addEventListener('click', function() {
        const id = document.getElementById('useid').value;

        if (!id || id.length === 0) {
            document.getElementById('label1').style.color = 'red';
            document.getElementById('label1').textContent = '아이디를 입력해주세요.';
            return false;
        }

        $.ajax({
            url: '/user/api/check-duplicate',
            type: 'GET',
            data: { useId: id },
            dataType: 'json',
            success: function(result) {
                if (result.exists) {
                    document.getElementById('label1').style.color = 'red';
                    document.getElementById('label1').textContent = '이미 사용중인 ID입니다.';
                } else {
                    document.getElementById('label1').style.color = 'black';
                    document.getElementById('label1').textContent = '사용 가능한 ID 입니다.';
                    validateForm(); // 모든 유효성 검사를 다시 실행하여 버튼 활성화 여부를 확인
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                document.getElementById('label1').style.color = 'red';
                document.getElementById('label1').textContent = '서버 오류가 발생했습니다.';
            }
        });
    });

    window.sendNumber = function() {
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
                validateForm(); // 인증번호 발송 후 폼 유효성 검사
            },
            error: function() {
                alert("인증번호 발송에 실패했습니다.");
            }
        });
    }

    window.confirmNumber = function() {
        const number1 = $("#number").val();
        const number2 = $("#Confirm").val();
        console.log(number1);
        console.log(number2);

        if (number1 === number2) {
            alert("인증되었습니다.");
            isCodeVerified = true;
            validateForm(); // 폼 유효성 검사
        } else {
            alert("번호가 다릅니다.");
            isCodeVerified = false;
        }
    }
});