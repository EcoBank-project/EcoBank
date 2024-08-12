/**
 * 
 */
//$(document).ready(getAllPath);

window.setTimeout(getAllPath, 500);


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
	// 데이터 배열 정의
    var labels = [50000000, 5000000, 1000000, 500000, 100000, 50000, 10000, 5000];
	let colorsets = ['#ff0303', '#ff3903', '#ff8103', '#ffe203', '#9aff03', '#35ff03', '#03ff70', '#0385ff'];

    // legend_label에 li 요소 추가
    for(var i = 0; i < labels.length; i++) {
        $('.legend_label').append('<li>' + (labels[i]+"").replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</li>');
    }

    // legend_color에 li 요소 추가 및 배경색 지정
    for(var j = 0; j < colorsets.length; j++) {
        $('.legend_color').append('<li style="background-color: ' + colorsets[j] + '"></li>');
    }
function getAllPath() {

	console.log("ready!");


	let carbDataAry = JSON.parse($("input[id='carbList']").val());
	let test = $("path");
	let className = '';

	

	let dataAry = [];
	$(test).each((idx, item) => {
		className = ($(item).attr('class') + '');
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
				simplemaps_worldmap_mapdata.state_specific[ditem['nationCode']].color = colorsets[colorOption];
				simplemaps_worldmap_mapdata.data.data[ditem['nationCode']] = ditem['carbonEmissions'];
				$(item).attr('fill', colorsets[colorOption]);
				dataAry.push({ region: ditem['regionCode'], code: ditem['nationCode'], tag: item, emission: ditem['carbonEmissions'], colorset: colorsets[colorOption] });
			}
		});
	});

	console.log(dataAry);
	console.log(simplemaps_worldmap_mapdata.data);
	simplemaps_worldmap.load();

	simplemaps_worldmap.hooks.zoomable_click_region = function(id) { alert(simplemaps_worldmap_mapdata.regions[id].name); }
	simplemaps_worldmap.hooks.click_region = function(id) { console.log(id); }
	simplemaps_worldmap.hooks.back = function() { alert('Back button clicked!'); }
	simplemaps_worldmap.hooks.click_state = function(id){
     alert(id);
   }
}



/*sm_state.sm_state_AW*/



