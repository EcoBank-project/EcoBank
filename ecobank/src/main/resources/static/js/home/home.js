/**
 * 
 */
// CSRF 토큰을 meta 태그에서 읽어옵니다.

async function getIP() {
	let csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
	let csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
	try {
		const response = await fetch('/get-ip', {
			method: 'GET',
			headers: {
				[csrfHeader]: csrfToken // CSRF 토큰을 헤더에 포함시킵니다.
			}
		});
		const ip = await response.text();
		console.log(ip);
		return ip;
	} catch (error) {
		console.error('Error fetching IP:', error);
	}
}

async function getLocation(ip) {
	try {
		const response = await fetch(`https://ipapi.co/${ip}/json/`);
		const data = await response.json();
		return data;
	} catch (error) {
		console.error('Error fetching location:', error);
	}
}
let count = 0;
document.addEventListener('DOMContentLoaded', async () => {
	count++;
	if (count == 2) {
		const ip = await getIP();
		if (ip) {
			const location = await getLocation(ip);
			console.log('Location data:', location);
			/*document.getElementById('result').textContent = JSON.stringify(location, null, 2);*/
		}
	}
});
