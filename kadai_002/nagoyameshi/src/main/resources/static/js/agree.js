$('#agree').change(function() {
	if ($(this).is(":checked")) {
		$('#btn_agree').prop('disabled', false);
	} else {
		$('#btn_agree').prop('disabled', true);
	}
});