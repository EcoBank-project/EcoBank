/**
 * 
 */
//$(document).ready(getAllPath);

window.setTimeout(setAllDataPath, 500);


/*데이터 별 색 정의

50000000 > data && data >= 5000000

5000000 > data && data >= 1000000

1000000 > data && data >= 500000

500000 > data && data >= 100000

100000 > data && data >= 50000

50000 > data && data >= 10000

10000 > data && data >= 5000

5000 > data && data >= 0

*/
const MAX_EMISSIONS = 50000000.0; // 최대 탄소배출량 (톤 CO2)
const MIN_EMISSIONS = 0.0;        // 최소 탄소배출량 (톤 CO2)
const MAX_SEA_LEVEL = 5.0;        // 최대 해수면 높이 (미터)

let dataAry = []; // 맵 태그, 테이블 정보 함께 담을 종합 배열

// 화면에서 사용될 표현 데이터 정의(탄소배출량 단계, 단계별 색상표)
const carbonlabels = [50000000, 5000000, 1000000, 500000, 100000, 50000, 10000, 5000];
let colorsets = ['#ff0303', '#ff3903', '#ff8103', '#ffe203', '#9aff03', '#35ff03', '#03ff70', '#0385ff'];

// 해수면 컬러셋(왼쪽부터 짙은색)
const sealabels = [3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5, 0.1];
let seacolorset=['#00008B','#0000CD','#4682B4','#1E90FF','#87CEEB','#008B8B','#20B2AA','#40E0D0'];

let latestClickedRegion;
function calculateSeaLevel(emissions) {
	if (emissions < MIN_EMISSIONS) emissions = MIN_EMISSIONS;
	if (emissions > MAX_EMISSIONS) emissions = MAX_EMISSIONS;

	const seaLevel = (emissions - MIN_EMISSIONS) / (MAX_EMISSIONS - MIN_EMISSIONS) * MAX_SEA_LEVEL;
    
    return parseFloat(seaLevel.toFixed(2));
}

// 변환 데이터를 8단계로 변환(색상표)
function determineColorCategory(seaLevel) {
   if (seaLevel >= 0 && seaLevel < 0.1) {
        return 7; // 0 ~ 0.09
    } else if (seaLevel >= 0.1 && seaLevel < 0.5) {
        return 6; // 0.1 ~ 0.49
    } else if (seaLevel >= 0.5 && seaLevel < 1.0) {
        return 5; // 0.5 ~ 0.99
    } else if (seaLevel >= 1.0 && seaLevel < 1.5) {
        return 4; // 1.0 ~ 1.49
    } else if (seaLevel >= 1.5 && seaLevel < 2.0) {
        return 3; // 1.5 ~ 1.99
    } else if (seaLevel >= 2.0 && seaLevel < 2.5) {
        return 2; // 2.0 ~ 2.49
    } else if (seaLevel >= 2.5 && seaLevel < 3.0) {
        return 1; // 2.5 ~ 2.99
    } else if (seaLevel >= 3.0) {
        return 0; // 3.0 ~ 3.49
    }
}


function setMapLabel(labels, colorsets) {
	
	$('.legend_label').html('');
	$('.legend_color').html('');
	
	// legend_label에 li 요소 추가
	for (var i = 0; i < labels.length; i++) {
		$('.legend_label').append('<li>' + (labels[i] + "").replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</li>');
	}

	// legend_color에 li 요소 추가 및 배경색 지정
	for (var j = 0; j < colorsets.length; j++) {
		$('.legend_color').append('<li style="background-color: ' + colorsets[j] + '"></li>');
	}
}
function setAllDataPath() {

	let carbDataAry = JSON.parse($("input[id='carbList']").val());
	let test = $("path");
	let className = '';
	
	// 지도의 모든 국가 path 가져옴
	$(test).each((idx, item) => {
		
		// path로부터 국가코드 가져옴
		className = ($(item).attr('class') + '');
		
		// 실제 carbon 테이블의 데이터
		carbDataAry.forEach(ditem => {
			let colorOption = -1;
			if (className.substring(className.indexOf('state_') + 6) == ditem['nationCode']) {
				if (ditem['carbonEmissions'] < 50000000 && ditem['carbonEmissions'] >= 5000000) {
					colorOption = 0;
				}
				else if (ditem['carbonEmissions'] < 5000000 && ditem['carbonEmissions'] >= 1000000) {
					colorOption = 1;
				}
				else if (ditem['carbonEmissions'] < 1000000 && ditem['carbonEmissions'] >= 500000) {
					colorOption = 2;
				}
				else if (ditem['carbonEmissions'] < 500000 && ditem['carbonEmissions'] >= 100000) {
					colorOption = 3;
				}
				else if (ditem['carbonEmissions'] < 100000 && ditem['carbonEmissions'] >= 50000) {
					colorOption = 4;
				}
				else if (ditem['carbonEmissions'] < 50000 && ditem['carbonEmissions'] >= 10000) {
					colorOption = 5;
				}
				else if (ditem['carbonEmissions'] < 10000 && ditem['carbonEmissions'] >= 5000) {
					colorOption = 6;
				}
				else if (ditem['carbonEmissions'] < 5000 && ditem['carbonEmissions'] >= 0) {
					colorOption = 7;
				}
				
				// simplemap의 국가 코드별 mapdata(색,데이터)를 가져온 데이터로 업데이트
				simplemaps_worldmap_mapdata.state_specific[ditem['nationCode']].color = colorsets[colorOption];
				simplemaps_worldmap_mapdata.data.data[ditem['nationCode']] = ditem['carbonEmissions'];
				
				/*$(item).attr('fill', colorsets[colorOption]);*/
				dataAry.push({ region: ditem['regionCode'], 
							   code: ditem['nationCode'], 
							   tag: item, 
							   emission: ditem['carbonEmissions'],
							   sealevel:  calculateSeaLevel(ditem['carbonEmissions']),
							   colorset: colorsets[colorOption] ,
							   seacolorset : seacolorset[determineColorCategory(calculateSeaLevel(ditem['carbonEmissions']))]
							   });
			}
		});
	});

	console.log(dataAry);
	console.log(simplemaps_worldmap_mapdata.data);
	setMapLabel(carbonlabels,colorsets);
	//dataAry.forEach((item,idx)=>(console.log(item.sealevel)));
	simplemaps_worldmap.load();
	
	// simplemaps api에서 제공하는 이벤트 핸들러에 각각 필요한 함수 등록
	
	// 대륙을 선택했을때
	simplemaps_worldmap.hooks.zoomable_click_region = function(id){focusToRegion(id)};
	
	// 뒤로가기 버튼 선택했을때 
	simplemaps_worldmap.hooks.back = function(){drawWorldMap()};
	
	// 나라를 선택했을때
	simplemaps_worldmap.hooks.click_state = function(id) {
		alert(id);
	}
}


function focusToRegion(id){
	latestClickedRegion = simplemaps_worldmap_mapdata.regions[id].name;
	// dataAry 배열에서 region 필드가 id와 일치하는 객체들만 필터링
    let filteredData = dataAry.filter(item => item.region === latestClickedRegion);
    
    
    filteredData.forEach(item=>simplemaps_worldmap_mapdata.state_specific[item.code].color = item.seacolorset);
    simplemaps_worldmap.refresh();
    //;
    // 화면 
    // 탄소배출량 레이블
    // 라디오박스 div 표시
    // 라디오박스가 변경될때 각각 표현해줘야할 데이터가 다르기 때문에 기능 분리해야함.
    
    // 해당 리전에 대해 표현해줘야할 데이터
    // 1. 탄소배출량(공통)
    let totalEmissions = filteredData.reduce((accumulator, currentValue) => 
    										(accumulator + currentValue.emission)
    										, 0);

	// 지도위에 표시해줄 label 설정
	$("span[id='regionName']").text(latestClickedRegion);
	$("span[id='totalEmission']").text((totalEmissions+'').replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
	$("div[id='regionDataDiv']").css('display','block');
	
    // 2. 해수면이면 해수면
    // 3. 대기오염도면 대기오염도
    // 2,3번에 해당하는 legend label
    
    setMapLabel(sealabels,seacolorset);
    $("h6[id='datatype']").text('data: sea level(m)');
}

// 세계 지도 그리는 함수
function drawWorldMap(){
	dataAry.forEach(item=>simplemaps_worldmap_mapdata.state_specific[item.code].color = item.colorset);
	simplemaps_worldmap.refresh();
	console.log("worldmap Called! " + latestClickedRegion);
	setMapLabel(carbonlabels,colorsets);
	$("h6[id='datatype']").text('data: carbon emission(TonCO2/kWh)');
	$("div[id='regionDataDiv']").css('display','none');
}
