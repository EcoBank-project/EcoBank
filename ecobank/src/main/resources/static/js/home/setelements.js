/**
 * 
 */

$(window).on('load', function() {

	$('img').each(function() {
		var img = $(this);

		// naturalWidth나 naturalHeight가 0이면 이미지가 제대로 로드되지 않은 것
		if (img[0].naturalWidth === 0 || img[0].naturalHeight === 0) {
/*			console.log('Image failed to load:', img.attr('src'));*/
			var defaultSrc = 'https://via.placeholder.com/250';
			if (img.closest('div.testimonial-img').length > 0) {
				defaultSrc = 'https://api.dicebear.com/9.x/bottts/svg?style=circle';
			}
			else if (img.closest('div.mySwiperCustom').length > 0) {
				defaultSrc = 'https://via.placeholder.com/250';
			}
			else if (img.parent().hasClass('d-flex')) {
				defaultSrc = 'https://via.placeholder.com/200';
			}
			img.attr('src', defaultSrc);
		}
	});
});