	let isCodeVerified = false;

	function validateForm() {
		const password1 = document.getElementById('password1').value;
		const password2 = document.getElementById('password2').value;
		const errorMessage = document.getElementById('error-message');
		
		if (password1 !== password2) {
			errorMessage.style.display = 'block';
			return false;
		}
		errorMessage.style.display = 'none';

		if (!isCodeVerified) {
			alert("이메일 인증을 완료해 주세요.");
			return false;
		}

		return true;
	}

	function checkFormValidity() {
		const useId = document.getElementById('useid').value;
		const password1 = document.getElementById('password1').value;
		const password2 = document.getElementById('password2').value;
		const nickname = document.getElementById('nickname').value;
		const tell = document.getElementById('tell').value;
		const number = document.getElementById('number').value;

		const isFormValid = useId && password1 && password2 && nickname && tell && number;
		document.getElementById('submit-button').disabled = !isFormValid || !isCodeVerified;
	}

	document.addEventListener("DOMContentLoaded", function() {
		const formElements = document.querySelectorAll(".form-control");
		formElements.forEach(element => {
			element.addEventListener("input", checkFormValidity);
		});
	});

	function sendNumber(){
		const useidValue = $("#useid").val();
		console.log(useidValue); // useid 값을 콘솔에 출력
		
		$("#mail_number").css("display","block");
		$.ajax({
			url: "/user/mail",
			type: "post",
			dataType: "json",
			data: { mail: useidValue },
			success: function(data){
				alert("인증번호 발송");
				$("#Confirm").val(data); // 서버에서 받은 인증번호를 숨겨진 필드에 설정
				isCodeVerified = false; // 초기 상태로 설정
			},
			error: function() {
				alert("인증번호 발송에 실패했습니다.");
			}
		});
	}

	function confirmNumber(){
		const number1 = $("#number").val();
		const number2 = $("#Confirm").val();
		console.log(number1);
		console.log(number2);

		if(number1 == number2){
			alert("인증되었습니다.");
			isCodeVerified = true;
			checkFormValidity(); // 폼 유효성 검사
		}else{
			alert("번호가 다릅니다.");
			isCodeVerified = false;
		}
	}