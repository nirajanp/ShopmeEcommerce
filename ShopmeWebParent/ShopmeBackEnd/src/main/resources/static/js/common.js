$(document).ready(function() {
		$("#logoutLink").on("click", function(e) {
			// prevents default behavior of the hyperlink
			e.preventDefault();
			document.logoutForm.submit();
		});
});
