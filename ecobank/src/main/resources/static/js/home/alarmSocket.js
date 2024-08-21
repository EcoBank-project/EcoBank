var stompClientAlarm = null;

function connectAlarm() {
    if (sessionStorage.getItem('alarmSocketConnected') === 'true') {
        console.log("Already connected to Alarm Socket");
        return;
    }

    var socketAlarm = new SockJS('/ws-alarm');
    stompClientAlarm = Stomp.over(socketAlarm);
    stompClientAlarm.connect({}, function(frame) {
        console.log('Connected to Alarm Socket: ' + frame);
        
        // 사용자 알림 구독
        stompClientAlarm.subscribe('/user/queue/alarms', function(alarmMessage) {
            const alarm = JSON.parse(alarmMessage.body);
            alert("새 알림: " + alarm.alarmContent);
        });
        
        sessionStorage.setItem('alarmSocketConnected', 'true'); // 연결 상태 저장
    });
}

function disconnectAlarm() {
    if (stompClientAlarm !== null) {
        stompClientAlarm.disconnect();
        console.log("Disconnected from Alarm Socket");
        sessionStorage.setItem('alarmSocketConnected', 'false'); // 연결 해제 상태 저장
    }
}

// 로그인 여부에 따른 소켓 연결 처리
function initializeSocketConnection() {
    // 로그인 상태 확인
    var isAuthenticatedMeta = document.querySelector('meta[name="isAuthenticated"]');
    
    if (isAuthenticatedMeta !== null) {
        connectAlarm();
    } else {
        disconnectAlarm();
    }
}

window.onload = initializeSocketConnection;