// when cancel button is pressed it will redirect to users page
$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		window.location = moduleURL;
	});
	
	// this is the handeler method for change event of file input
	$("#fileImage").change(function() {
	// to check the image size
	fileSize = this.files[0].size;
	if(fileSize > 102400) {
		this.setCustomValidity("You must choose an image less than 100KB!");
		this.reportValidity();
	} else {
		this.setCustomValidity("");
		showImageThumbnail(this);
	}
	});
});

// JS code to display image thumbnail
function showImageThumbnail(fileInput) {
	// first file object
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	
	reader.readAsDataURL(file);
}
