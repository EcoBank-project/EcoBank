/*$(document).ready(function() {
	const ctx = $('#reductionChart').get(0).getContext('2d');
	createChart(ctx, labels, actualEmissions, reducedEmissions);
});

let carbDataAry = JSON.parse($("input[id='carbList']").val());

// 실제 당일 탄소 배출량 (킬로톤으로 변환)
const actualDailyEmission = carbDataAry.reduce((accumulator, currentValue) =>
	(accumulator + currentValue.carbonEmissions)
	, 0) / 1000;

// 당일 점수는 추후 반영(데이터가 너무 적음)
const dailyUserScore = $("input[id='totalEmissionPoint']").val(); // 사용자들이 당일 적립한 점수 (예: 20점)
const reductionFactor = 0.5; // 점수 당 저감되는 탄소 배출량 (예: 0.5톤/점)

function calculateReduction(score, factor) {
	return score * factor / 1000; // 킬로톤으로 변환
}

function predictReduction(dailyReduction, days) {
	return dailyReduction * days;
}

const dailyReduction = calculateReduction(dailyUserScore, reductionFactor);
const weeklyReduction = predictReduction(dailyReduction, 7);
const monthlyReduction = predictReduction(dailyReduction, 30);
const yearlyReduction = predictReduction(dailyReduction, 365);

const labels = ['Today', 'In 1 Week', 'In 1 Month', 'In 1 Year'];
const actualEmissions = [actualDailyEmission, actualDailyEmission, actualDailyEmission, actualDailyEmission];
const reducedEmissions = [
	actualDailyEmission - dailyReduction,
	actualDailyEmission - weeklyReduction,
	actualDailyEmission - monthlyReduction,
	actualDailyEmission - yearlyReduction
];

function createChart(ctx, labels, actualData, reducedData) {
	return new Chart(ctx, {
		type: 'line',
		data: {
			labels: labels,
			datasets: [
				{
					label: 'Actual Carbon Emission',
					data: actualData,
					borderColor: 'rgb(255, 99, 132)',
					fill: false
				},
				{
					label: 'Predicted Reduced Emission',
					data: reducedData,
					borderColor: 'rgb(54, 162, 235)',
					fill: false
				}
			]
		},
		options: {
			responsive: true,
			plugins: {
				legend: {
					labels: {
						font: {
							size: 14 // 범례 글자 크기
						}
					}
				},
				title: {
					display: true,
					text: 'Carbon Emission and Reduction',
					font: {
						size: 40 // 제목 글자 크기
					}
				}
			},
			scales: {
				x: {
					ticks: {
						font: {
							size: 40 // X축 글자 크기
						}
					},
					title: {
						display: true,
						text: 'Time',
						font: {
							size: 40 // X축 제목 글자 크기
						}
					}
				},
				y: {
					ticks: {
						font: {
							size: 40 // Y축 글자 크기
						}
					},
					title: {
						display: true,
						text: 'Carbon Emission (kilotons)',
						font: {
							size: 40 // Y축 제목 글자 크기
						}
					}
				}
			}
		}
	});
}*/
$().ready(function() {
	google.charts.load('current', { 'packages': ['corechart', 'line'] });
	google.charts.setOnLoadCallback(drawChart);
})

function drawChart() {
	const carbDataAry = JSON.parse(document.getElementById('carbList').value);

	// 실제 당일 탄소 배출량 (킬로톤으로 변환)
	const actualDailyEmission = carbDataAry.reduce((accumulator, currentValue) =>
		(accumulator + currentValue.carbonEmissions)
		, 0) / 1000;

	// 당일 점수는 추후 반영(데이터가 너무 적음)
	const dailyUserScore = parseFloat(document.getElementById('totalEmissionPoint').value); // 사용자들이 당일 적립한 점수 (예: 20점)
	const reductionFactor = 1; // 점수 당 저감되는 탄소 배출량 (예: 0.5톤/점)

	function calculateReduction(score, factor) {
		return score * factor / 1000; // 킬로톤으로 변환
	}

	function predictReduction(dailyReduction, days) {
		return dailyReduction * days;
	}

	const dailyReduction = calculateReduction(dailyUserScore, reductionFactor);
	const weeklyReduction = predictReduction(dailyReduction, 7);
	const monthlyReduction = predictReduction(dailyReduction, 30);
	const yearlyReduction = predictReduction(dailyReduction, 365);

	const data = new google.visualization.DataTable();
	data.addColumn('string', 'Time');
	data.addColumn('number', 'Actual Carbon Emission (kilotons)');
	data.addColumn('number', 'Predicted Reduced Emission (kilotons)');

	data.addRows([
		['Today', actualDailyEmission, actualDailyEmission - dailyReduction],
		['In 1 Week', actualDailyEmission, (actualDailyEmission*7) - weeklyReduction],
		['In 1 Month', actualDailyEmission, (actualDailyEmission*30) - monthlyReduction],
		['In 1 Year', actualDailyEmission, (actualDailyEmission*365) - yearlyReduction]
	]);

	const options = {
		title: 'Carbon Emission and Reduction',
		curveType: 'function',
		legend: { position: 'bottom' },
		hAxis: {
			title: 'Time',
			textStyle: { fontSize: 20 },
			titleTextStyle: { fontSize: 22 }
		},
		vAxis: {
			title: 'Carbon Emission (kilotons)',
			textStyle: { fontSize: 20 },
			titleTextStyle: { fontSize: 22 }
		},
		tooltip: { isHtml: true },
		pointSize: 7 // 데이터 포인트 크기 증가 (클릭 가능)
	};

	const chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
	google.visualization.events.addListener(chart, 'onmouseover', function(e) {
		const row = e.row;
		const tooltipElement = document.getElementById('tooltip');

		if (row !== null && tooltipElement) {  // row와 tooltipElement가 유효한지 확인
			const tooltipContent = 'Time: ' + data.getValue(row, 0) + '<br>' +
				'Actual Emission: ' + data.getValue(row, 1).toFixed(0) + ' kt<br>' +
				'Reduced Emission: ' + data.getValue(row, 2).toFixed(0) + ' kt';
			tooltipElement.innerHTML = tooltipContent;
			tooltipElement.style.display = 'block';
		}
	});

	google.visualization.events.addListener(chart, 'onmouseout', function() {
		const tooltipElement = document.getElementById('tooltip');
		if (tooltipElement) {
			tooltipElement.style.display = 'none';
		} else {
			console.error('Tooltip element not found');
		}
	});
	chart.draw(data, options);
}