/**
 * 
 */
//$(document).ready(getAllPath);

window.setTimeout(getAllPath,500);
var count = 0;

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

function getAllPath() {
	count+=1;
    console.log( "ready!"+count );
    
    if(count == 2){
		let carbDataAry = JSON.parse($("input[id='carbList']").val());
		let test = $("path");		
		let className='';
		
		let colorsets = ['#ff0303','#ff3903','#ff8103','#ffe203','#9aff03','#35ff03','#03ff70','#0385ff'];
		
		let dataAry=[];
		$(test).each((idx,item)=>{
			className =($(item).attr('class')+'');
			carbDataAry.forEach(ditem=>{
				let colorOption = -1;
				if(className.substring(className.indexOf('state_')+6) == ditem['nationCode']){
					if(ditem['carbonEmissions'] < 50000000 && ditem['carbonEmissions'] >= 5000000){
						colorOption = 0;
					}
					else if(ditem['carbonEmissions'] < 5000000 && ditem['carbonEmissions'] >= 1000000){
						colorOption = 1;
					}
					else if(ditem['carbonEmissions'] < 1000000 && ditem['carbonEmissions'] >= 500000){
						colorOption = 2;
					}
					else if(ditem['carbonEmissions'] < 500000 && ditem['carbonEmissions'] >= 100000){
						colorOption = 3;
					}
					else if(ditem['carbonEmissions'] < 100000 && ditem['carbonEmissions'] >= 50000){
						colorOption = 4;
					}
					else if(ditem['carbonEmissions'] < 50000 && ditem['carbonEmissions'] >= 10000){
						colorOption = 5;
					}
					else if(ditem['carbonEmissions'] < 10000 && ditem['carbonEmissions'] >= 5000){
						colorOption = 6;
					}
					else if(ditem['carbonEmissions'] < 5000 && ditem['carbonEmissions'] >= 0){
						colorOption = 7;
					}
					simplemaps_worldmap_mapdata.state_specific[ditem['nationCode']].color=colorsets[colorOption];
					simplemaps_worldmap_mapdata.data.data[ditem['nationCode']] = ditem['carbonEmissions'];
					$(item).attr('fill',colorsets[colorOption]);
					dataAry.push({code:ditem['nationCode'], tag:item, emission:ditem['carbonEmissions'], colorset:colorsets[colorOption]});
				}
			});
		});
		
		console.log(dataAry);
		console.log(simplemaps_worldmap_mapdata.data);
		simplemaps_worldmap.load();
	}
		simplemaps_worldmap.hooks.zoomable_click_region=function(id){alert(simplemaps_worldmap_mapdata.regions[id].name);}
		simplemaps_worldmap.hooks.click_region = function(id){console.log(id);}

}
 
/*sm_state.sm_state_AW*/



