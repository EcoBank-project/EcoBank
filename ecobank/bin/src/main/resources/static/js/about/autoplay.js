// 전역 스코프에 onYouTubeIframeAPIReady 함수 정의
function onYouTubeIframeAPIReady() {
    player = new YT.Player('widget2', {
        playerVars: {
            'autoplay': 1,          // 자동 재생
            'mute': 1,              // 음소거
            'controls': 0,          // 플레이어 컨트롤 숨김
            'modestbranding': 1,    // YouTube 로고 숨김
            'showinfo': 0,          // 동영상 제목 숨김 (이 설정은 현재 더 이상 지원되지 않지만, 유지해도 무방합니다)
            'rel': 0,               // 관련 동영상 추천 비활성화
            'loop': 1,              // 동영상 루프 재생
            'playlist': 'myZAvqqp9Jc' // 루프 재생을 위해 동영상 ID를 playlist 파라미터로 전달
        },
        events: {
            'onReady': onPlayerReady
        }
    });
}

function onPlayerReady(event) {
	console.log("Player is ready");
	event.target.mute(); // Mute the video
	event.target.playVideo(); // Play the video
}

document.addEventListener("DOMContentLoaded", function() {
	console.log("DOMContentLoaded event triggered");
	// Load the IFrame Player API code asynchronously.
	var tag = document.createElement('script');
	tag.src = "https://www.youtube.com/iframe_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
});