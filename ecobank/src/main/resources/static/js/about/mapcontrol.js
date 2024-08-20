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

// 라디오 버튼별 액션코드 정의 
const AIR_POLLUTION='air';
const SEA_LEVEL='sea';

let dataAry = []; // 맵 태그, 테이블 정보 함께 담을 종합 배열

// 화면에서 사용될 표현 데이터 정의(탄소배출량 단계, 단계별 색상표)
const carbonlabels = [50000000, 5000000, 1000000, 500000, 100000, 50000, 10000, 5000];
let colorsets = ['#ff0303', '#ff3903', '#ff8103', '#ffe203', '#9aff03', '#35ff03', '#03ff70', '#0385ff'];

// 해수면 컬러셋(왼쪽부터 짙은색)
const sealabels = [3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5, 0.1];
let seacolorset=['#00008B','#0000CD','#4682B4','#1E90FF','#87CEEB','#008B8B','#20B2AA','#40E0D0'];

// 대기오염도(왼쪽부터 옅은색)
const airlabels = ['0 - 5 μg/m³', '5.1 - 10 μg/m³', '10.1 - 15 μg/m³', '15.1 - 25 μg/m³', '25.1 - 35 μg/m³', '35.1 - 50 μg/m³', '> 50.1 μg/m³'];
let aircolorset=['#60ACCB','#9CD84E','#FACF39','#F99049','#F65E5F','#A070B6','#A06A7B'];

let latestClickedRegion;

// 탄소배출량 -> 해수면(m) 변환 함수
function calculateSeaLevel(emissions) {
	if (emissions < MIN_EMISSIONS) emissions = MIN_EMISSIONS;
	if (emissions > MAX_EMISSIONS) emissions = MAX_EMISSIONS;

	const seaLevel = (emissions - MIN_EMISSIONS) / (MAX_EMISSIONS - MIN_EMISSIONS) * MAX_SEA_LEVEL;
    
    return parseFloat(seaLevel.toFixed(2));
}

// 해수면 변환 데이터를 8단계로 변환(색상표)
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

// 탄소배출량 -> 대기오염도(μg/m³) 변환 함수
function calculateAirPollution(emissions) {
    if (emissions < MIN_EMISSIONS) emissions = MIN_EMISSIONS;
    if (emissions > MAX_EMISSIONS) emissions = MAX_EMISSIONS;

    const pollutionLevel = (emissions - MIN_EMISSIONS) / (MAX_EMISSIONS - MIN_EMISSIONS) * 50;
    
    return parseFloat(pollutionLevel.toFixed(1));
}

// 대기 오염도 변환 데이터를 7단계로 변환(색상표)
function determinePollutionCategory(pollutionLevel) {
    if (pollutionLevel >= 0 && pollutionLevel <= 5) {
        return 1; // 0 - 5 μg/m³
    } else if (pollutionLevel > 5 && pollutionLevel <= 10) {
        return 2; // 5.1 - 10 μg/m³
    } else if (pollutionLevel > 10 && pollutionLevel <= 15) {
        return 3; // 10.1 - 15 μg/m³
    } else if (pollutionLevel > 15 && pollutionLevel <= 25) {
        return 4; // 15.1 - 25 μg/m³
    } else if (pollutionLevel > 25 && pollutionLevel <= 35) {
        return 5; // 25.1 - 35 μg/m³
    } else if (pollutionLevel > 35 && pollutionLevel <= 50) {
        return 6; // 35.1 - 50 μg/m³
    } else {
        return 7; // > 50.1 μg/m³
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
							   colorset: colorsets[colorOption] ,
							   sealevel:  calculateSeaLevel(ditem['carbonEmissions']),
							   seacolorset : seacolorset[determineColorCategory(calculateSeaLevel(ditem['carbonEmissions']))],
							   airlevel:calculateAirPollution(ditem['carbonEmissions']),
							   aircolorset: aircolorset[determinePollutionCategory(calculateAirPollution(ditem['carbonEmissions']))]
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
	simplemaps_worldmap.hooks.zoomable_click_region = function(id){
		focusToRegion(simplemaps_worldmap_mapdata.regions[id].name,SEA_LEVEL)
		$('#flexRadioDefault1').prop('checked', true);
		};
	
	// 뒤로가기 버튼 선택했을때 
	simplemaps_worldmap.hooks.back = function(){drawWorldMap()};
	
	// 나라를 선택했을때
	simplemaps_worldmap.hooks.click_state = function(id) {
		Swal.fire({
                title: "Coming soon!",
                icon: "success",
                confirmButtonColor: "#32C36C", 
            });
		
	}
	
	
	// 라디오 버튼 체크 상태가 변경될 때 실행되는 핸들러 등록
    $('input[name="flexRadioDefault"]').change(function() {
        if (this.id === 'flexRadioDefault1') {
            // Sea Level ratio가 선택되었을 때 실행할 코드
            focusToRegion(latestClickedRegion,SEA_LEVEL);
        } else if (this.id === 'flexRadioDefault2') {
            // Air Pollution ratio가 선택되었을 때 실행할 코드
            focusToRegion(latestClickedRegion,AIR_POLLUTION);
        }
    });
}


     
function focusToRegion(regionName,actionCode){
	// regionCode로부터 region 이름 추출
	latestClickedRegion = regionName;
	
	// dataAry 배열에서 region 필드가 id와 일치하는 객체들만 필터링
    let filteredData = dataAry.filter(item => item.region === latestClickedRegion);
    
    // 나라별 해수면 색상정보 mapdata에 저장
    // 해수면에 해당하는 legend label 세팅
    if(actionCode === SEA_LEVEL){
		filteredData.forEach(item=>simplemaps_worldmap_mapdata.state_specific[item.code].color = item.seacolorset);
		setMapLabel(sealabels,seacolorset);
    	$("h6[id='datatype']").text('data: sea level(m)');
	}
	
    // 나라별 대기오염도 색상정보 mapdata에 저장
    // 대기오염도에 해당하는 legend label 세팅
    else{
		filteredData.forEach(item=>simplemaps_worldmap_mapdata.state_specific[item.code].color = item.aircolorset);
		setMapLabel(airlabels,aircolorset);
    	$("h6[id='datatype']").text('data: PM2.5(μg/m³)');
	}
	
	// mapdata 정보 갱신 후 맵 리로드
    simplemaps_worldmap.refresh();
    
    // 1. 해당 region 총 탄소배출량(공통)
    let totalEmissions = filteredData.reduce((accumulator, currentValue) => 
    										(accumulator + currentValue.emission)
    										, 0);
    							
	// 지도위에 표시해줄 label 설정(탄소배출량 레이블)
	$("span[id='regionName']").text(latestClickedRegion);
	$("span[id='totalEmission']").text((totalEmissions+'').replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
	
	// 해당 region 총 탄소배출량 레이블 활성화
	$("div[id='regionDataDiv']").css('display','block');
	
	
	// 사용자 선택 메뉴 활성화
	$("div[id='dataSelectTab']").css('display','block');
}

// 세계 지도 그리는 함수
function drawWorldMap(){
	dataAry.forEach(item=>simplemaps_worldmap_mapdata.state_specific[item.code].color = item.colorset);
	simplemaps_worldmap.refresh();
	console.log("worldmap Called! " + latestClickedRegion);
	setMapLabel(carbonlabels,colorsets);
	
	
	$("h6[id='datatype']").text('data: carbon emission(TonCO2/kWh)');
	
	// 지역별 탄소배출량 레이블 비활성화
	$("div[id='regionDataDiv']").css('display','none');
	
	// 사용자 선택 메뉴 비활성화
	$("div[id='dataSelectTab']").css('display','none');
}



