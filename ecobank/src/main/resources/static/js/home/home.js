/**
 * 
 */
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');



function getCountry(e){
	console.log(e);
}


async function getLocation() {
	//const access_key = '2602e3bc2e9211bd86fd5d25a426c36b';
	try {
		const response = await $.ajax({
			url: '/ip-info', // 서버 측에서 프록시된 API 엔드포인트
			type: 'GET',
			dataType: 'json',
			success: function(data) {
				console.log('IP Checked!');
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.error('Error fetching IP information:', textStatus, errorThrown);
			}
		});

		return response;
	} catch (error) {
		console.error('Error fetching location:', error);
	}
}

$().ready(async () => {
	const location = await getLocation();




	// 국가 코드
	let countryCode = ''+location['country_code'];
	// 로그인 아이디
	let userID = $("input[id='userID']").val();
	
	if (userID !== null && userID !== undefined && userID.trim() !== '') {
        $.ajax({
            url: '/set-country',
            type: 'POST',
            contentType: 'application/json', // Content-Type을 application/json으로 설정
            dataType: 'json',
            data: JSON.stringify({ // data를 JSON 문자열로 변환
                country_code: countryCode,
                user_id: userID
            }),
            success: function(data) {
                console.log('Update Success!' + data);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching set-country! Status: ' + status + ', Error: ' + error);
            }
        });
    } 
});