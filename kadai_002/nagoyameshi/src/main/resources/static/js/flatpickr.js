let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 1);

flatpickr('#reservationDateAndTime', {
	enableTime:true,
	dateFormat:'Y-m-d H:i',
	locale:'ja',
	minDate:'today',
	maxDate:maxDate
});