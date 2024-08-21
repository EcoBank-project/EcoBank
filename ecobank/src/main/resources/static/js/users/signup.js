document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form'); // form 태그
    const submitButton = document.getElementById('submit-button'); // 회원가입 버튼

    let isCodeVerified = false; // 이메일 인증 여부

    // 이메일 유효성 검사 함수
    function emailCheck(email_address) {
        const email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        return email_regex.test(email_address);
    }

    function validatePassword() {
        const password1 = document.getElementById("password1").value;
        const password2 = document.getElementById("password2").value;
        const isValid = password1 === password2;
        console.log("Password1:", password1);
        console.log("Password2:", password2);
        console.log("Password Match:", isValid);
        return isValid;
    }

    function validateNickname() {
        const nickname = document.getElementById("nickname").value;
        const nicknameError = document.getElementById("nickname-error");
        const isValid = nickname.length >= 6 && nickname.length <= 20;
        console.log("Nickname:", nickname);
        console.log("Nickname Valid:", isValid);
        nicknameError.style.display = isValid ? "none" : "block";
        return isValid;
    }

    function validateTell() {
        console.log("validateTell function called"); // 함수 호출 확인

        const tell = document.getElementById("tell").value.trim();
        const tellError = document.getElementById("tell-error");
        const phoneRegex = /^[0-9]{9,11}$/;
        const isValid = phoneRegex.test(tell);
        console.log("Tell:", tell);
        console.log("Tell Valid:", isValid);
        tellError.style.display = isValid ? "none" : "block";
        return isValid;
    }

    function validateForm() {
        const isPasswordValid = validatePassword();
        const isNicknameValid = validateNickname();
        const isTellValid = validateTell();
        console.log("Password Valid:", isPasswordValid);
        console.log("Nickname Valid:", isNicknameValid);
        console.log("Tell Valid:", isTellValid);
        console.log("Code Verified:", isCodeVerified);
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
        console.log(id);
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