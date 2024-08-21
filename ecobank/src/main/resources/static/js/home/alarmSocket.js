var stompClientAlarm = null;
var socketAlarm = null;

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
            alert("새 알림: " + alarm.alarmContent);
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
    var isAuthenticatedMeta = document.querySelector('meta[name="isAuthenticated"]');
    if (isAuthenticatedMeta !== null) {
        connectAlarm();
    } else {
        disconnectAlarm();
    }
};

function sendAlarm(userNo) {
    if (stompClient && stompClient.connected) {
        let alarmContent = "새로운 알림 내용"; // 알림 내용
        const alarmMessage = {
            userNo: userNo,
            alarmContent: alarmContent,
            alarmCode: 'A1', // 예: 알림 코드
            alarmCreateAt: new Date(),
        };
        stompClient.send("/app/alarm.send/" + userNo, {}, JSON.stringify(alarmMessage));
    } else {
        alert("웹소켓 연결이 필요합니다.");
    }
}