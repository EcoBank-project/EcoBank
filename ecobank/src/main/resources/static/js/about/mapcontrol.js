/**
 * 
 */
//$(document).ready(getAllPath);

window.setTimeout(getAllPath,500);
var count = 0;
function getAllPath() {
	count+=1;
    console.log( "ready!"+count );
    
    if(count == 2){
		let test = $("path");
		
		/*let test2 = */
		//test.each((idx, item)=>console.log(idx));
//		console.log(test[0]);
		$(test).each((idx,item)=>console.log($(item).attr('class')));
		//console.log(test);
	}
}
/* document.querySelector("#map_inner > svg > path.sm_state.sm_state_MZ")
 document.querySelector("#map_inner")*/
 
 
/*sm_state.sm_state_AW*/