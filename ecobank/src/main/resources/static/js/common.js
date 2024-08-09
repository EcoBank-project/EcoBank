/**
 * 날짜 계산 js
 */
//시작날짜, 종료날짜 - 관리자 챌린지 등록때 사용함
function dateDiff(startDate, endDate){
	if(startDate == '' || endDate == '')
		return 0;
	const start = new Date(startDate);
    const end = new Date(endDate);
    const diffTime = Math.abs(end - start);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
}

