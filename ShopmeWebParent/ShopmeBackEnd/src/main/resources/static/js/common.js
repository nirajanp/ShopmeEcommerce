$(document).ready(function() {
		$("#logoutLink").on("click", function(e) {
			// prevents default behavior of the hyperlink
			e.preventDefault();
			document.logoutForm.submit();
		});
		
		customizeDropDownMenu()
});

function customizeDropDownMenu() {
	$(".navbar .dropdown").hover(
		function() {
			$(this).find('.dropdown-menu').first().stop(true,true).delay(250).slideDown();
		}, 
		function() {
			$(this).find('.dropdown-menu').first().stop(true,true).delay(100).slideUp();
		}
	);
	// select tag dropdown and 'a' tag in dropdown
	$(".dropdown > a").click(function() {
		// we set the href attribute of the browser to the 
		// href attribute of itself.
		location.href = this.href;
	});
}