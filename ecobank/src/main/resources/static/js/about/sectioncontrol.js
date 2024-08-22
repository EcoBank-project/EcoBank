/**
 * sectioncontrol.js
 */
let userNo='-1';
let usableScore='-1';
$(document).ready(function() {
	userNo = $('#userNo').val();
	usableScore = $('#usableScore').val();
/*    console.log('회원번호: '+userNo);
    console.log('사용가능점수: '+usableScore);*/
	initializeSection();
});

function initializeSection() {
	const $container = $('#container');
	const width = $container.width();
	const height = 400;

	const stage = new Konva.Stage({
		container: 'container',
		width: width,
		height: height
	});

	const layer = new Konva.Layer();
	stage.add(layer);

	const amplitude = 20;
	const path = new Konva.Path({
		fill: '#00bfff',
		stroke: '',
		strokeWidth: 0
	});

	layer.add(path);

	const simplex = new SimplexNoise();
	let offset = 0;

	function getWavePath() {
		let wavePath = 'M 0 ' + (height / 2);

		for (let x = 0; x < width; x++) {
			const noiseValue = simplex.noise2D(x * 0.01, offset);
			const y = amplitude * noiseValue + (height / 2);
			wavePath += ' L ' + x + ' ' + y;
		}

		wavePath += ` L ${width} ${height} L 0 ${height} Z`;

		return wavePath;
	}

	function animate() {
		offset += 0.007; // 속도를 높입니다.
		path.data(getWavePath());
		layer.batchDraw();
		requestAnimationFrame(animate);
	}

	animate();
}

function OpenScoreModal() {
    let scoreData = {
        useScore: 0, // 입력된 값으로 대체
        userNo: Number(userNo) // userNo는 이미 숫자형으로 변환되어 있음
    };

    Swal.fire({
        title: "입금할 점수를 입력해주세요.",
        text: "입금 가능 점수 : " + usableScore,
        input: "number",
        inputAttributes: {
            min: 1, // 최소값 설정
            max: Number(usableScore), // 최대값 설정 
            step: 1 // 숫자 증가/감소 간격
        },
        showCancelButton: true,
        confirmButtonText: "입금",
        showLoaderOnConfirm: true, // 로딩 애니메이션을 표시
        preConfirm: (value) => {
            // 입력값이 없거나 형식에 맞지 않는 경우
            if (!value) {
                Swal.showValidationMessage('점수를 입력해주세요');
                return false;
            } else if (isNaN(value) || value <= 0 || !Number.isInteger(Number(value))) {
                Swal.showValidationMessage('형식에 맞지 않습니다. (0 이상의 자연수)');
                return false;
            } else if (value > Number(usableScore)) {
                Swal.showValidationMessage(`입력 값은 ${usableScore} 이하이어야 합니다.`);
                return false;
            }
            // 유효성 검사를 통과한 경우, 값을 scoreData에 저장
            scoreData.useScore = Number(value);

            // 여기서 Ajax 요청을 Promise로 반환
            return $.ajax({
                type: "POST",
                url: "/useScoreProcess",
                contentType: "application/json",
                data: JSON.stringify(scoreData)
            }).then(response => {
                if (response.result === "success") {
                    return response; // 성공 시 Promise를 해결합니다.
                } else {
                    return Promise.reject(new Error("점수 입금에 실패했습니다."));
                }
            }).catch(error => {
                Swal.showValidationMessage(`서버 요청 중 오류가 발생했습니다: ${error}`);
            });
        },
        allowOutsideClick: () => !Swal.isLoading() // 로딩 중에는 클릭으로 닫을 수 없도록 설정
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "성공",
                text: "점수가 성공적으로 입금되었습니다.",
                icon: "success"
            }).then(() => {
                // 확인 버튼을 클릭하면 페이지를 리로드
                location.reload();
            });
        }
    });
}
