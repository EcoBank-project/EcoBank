var stompClientAlarm = null;
var socketAlarm = null;

function showAlert() {
    Toast.fire({
        icon: "success",
        title: "새 알림 도착!",
        showCloseButton: true,  // 닫기 버튼 추가
    });
}

function connectAlarm() {
	if (stompClientAlarm !== null && stompClientAlarm.connected) {
		console.log("Already connected to Alarm Socket");
		return;
	}

	socketAlarm = new SockJS('/ws-alarm');
	stompClientAlarm = Stomp.over(socketAlarm);

	stompClientAlarm.connect({}, function(frame) {
		console.log('Connected to Alarm Socket: ' + frame);

		stompClientAlarm.subscribe('/user/queue/alarms', function(alarmMessage) {
			const alarm = JSON.parse(alarmMessage.body);
			showAlert();

			// 알림을 받으면 빨간 점을 표시
			$('.notification-dot').css('display', 'inline');
		});
	});

	socketAlarm.onclose = function() {
		console.log("WebSocket connection closed");
		stompClientAlarm = null; // 연결이 닫힌 경우 null로 설정
	};
}

function disconnectAlarm() {
	if (stompClientAlarm !== null && stompClientAlarm.connected) {
		stompClientAlarm.disconnect();
		console.log("Disconnected from Alarm Socket");
		stompClientAlarm = null; // 연결이 끊어진 경우 null로 설정
	}
}

window.addEventListener('beforeunload', function() {
	if (stompClientAlarm !== null && stompClientAlarm.connected) {
		stompClientAlarm.disconnect();
		console.log("WebSocket connection closed due to page unload");
	}
});

window.onload = function() {
	if (typeof userNumber === 'undefined' || userNumber === null) {
		console.log("userNumber is not defined or is null");
		$('.notification-dot').closest('a').attr('href', '/');
	} else {
		console.log("userNumber is defined and its value is:", userNumber);
		$('.notification-dot').closest('a').attr('href', `/alarms?receiverNo=${userNumber}`);
	}
	var isAuthenticatedMeta = document.querySelector('meta[name="isAuthenticated"]');
	if (isAuthenticatedMeta !== null) {
		connectAlarm();
	} else {
		disconnectAlarm();
	}
};

function sendAlarm(alarmCode, alarmRefNo) { // userId : 받는사람
	// ALARM_CONTENT, ALARM_CODE, ALARM_CREATEAT, ALARM_STATE, ALARM_REF_NO, USER_NO
	if (stompClientAlarm && stompClientAlarm.connected) {


		let alarmContent = "none"; // 알림 내용
		if (alarmCode == 'H3') {
			alarmContent = 'feed like!';
		}
		else if (alarmCode == 'H2') {
			alarmContent = '인증 좋아요!';
		}

		const alarmMessage = {
			userNo: userNumber,
			alarmContent: alarmContent,
			alarmCode: alarmCode, // 예: 알림 코드
			alarmCreateAt: new Date(),
			alarmRefNo: alarmRefNo,
		};
		stompClientAlarm.send("/app/alarm.send/" + alarmRefNo, {}, JSON.stringify(alarmMessage));
	} else {
		alert("웹소켓 연결이 필요합니다.");
	}
}