let isCodeVerified = false; // 인증번호 확인 상태 변수

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form'); // form 태그
    const submitButton = document.getElementById('submit-button'); // id가 'submit-button'인 버튼

    function validatePassword() { // 패스워드 유효성 체크
        const password1 = document.getElementById("password1").value; // id가 'password1'인 태그의 value
        const password2 = document.getElementById("password2").value; // id가 'password2'인 태그의 value
        const passwordError = document.getElementById("password-error");  // id가 'password-error'인 태그의 value
        const isValid = password1 === password2; // password1,2 값 비교 (boolean)

        passwordError.style.display = isValid ? "none" : "block"; // 패스워드가 같으면 display:none, 다르면 display:block
        return isValid; // 
    }

    function validateNickname() { // 닉네임 유효성 체크
        const nickname = document.getElementById("nickname").value.trim(); // id가 'nickname'인 태그의 value (양 옆 공백 제거)
        const nicknameError = document.getElementById("nickname-error");  // id가 'nickname-error'인 태그
        const isValid = nickname.length >= 6 && nickname.length <= 20; // nickname이 6자리 이상 20자리 이하면 true, 아니면 false

        nicknameError.style.display = isValid ? "none" : "block"; // 패스워드가 같으면 display:none, 다르면 display:block
        return isValid;
    }

    function validateTell() {
        const tell = document.getElementById("tell").value.trim(); // id가 'tell'인 태그의 value (양 옆 공백 제거) 
        const tellError = document.getElementById("tell-error"); // id가 'tell-error'인 태그
        const isValid = tell !== ""; // tell이 공백이면 false 아니면 true 

        tellError.style.display = isValid ? "none" : "block";
        return isValid;
    }

    function validateForm() {
        const isPasswordValid = validatePassword(); 
        const isNicknameValid = validateNickname();
        const isTellValid = validateTell();

        // 모든 유효성 검사 통과 시 회원가입 버튼 활성화
        submitButton.disabled = !(isPasswordValid && isNicknameValid && isTellValid && isCodeVerified); 
        // 비밀번호, 닉네임, 전화번호 유효성 체크 전부 true 나오면 버튼 활성화
    }

    // ID 중복 확인
    document.getElementById('sameCheckBtn').addEventListener('click', function() {
        const id = document.getElementById('useid').value;

        if (!id || id.length === 0) { // if, id value가 입력이 안 되어있으면
            document.getElementById('label1').style.color = 'red'; // id가 label1인 value인 값의 color : red
            document.getElementById('label1').textContent = '아이디를 입력해주세요.'; 
            // id가 value1인 태그의 text : '아이디를 입력해주세요' 
            return false;
        }

        $.ajax({
            url: '/user/api/check-duplicate', // /user/api/check-duplicate 에
            type: 'GET', // GET 요청을 보냄
            data: { useId: id }, // useId : '입력한 id 값' 
            dataType: 'json', // json 방식으로
            success: function(result) { // 성공하면
                if (result.exists) { // result.exists가 true면 db에 값이 있다는 뜻
                    document.getElementById('label1').style.color = 'red';
                    document.getElementById('label1').textContent = '이미 사용중인 ID입니다.';
                } else {
                    document.getElementById('label1').style.color = 'black';
                    document.getElementById('label1').textContent = '사용 가능한 ID 입니다.';
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