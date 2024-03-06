/**
 * 
 */

 function bookCountSubmit() {
	console.log('bookCountSubmit()');
	 
	let bookCount = parseInt(document.getElementsByName("c_book_count")[0].value);
	if (bookCount <= 0) {
		alert('올바른 수량을 입력해주세요');
		
	} else {
		form.submit();
	}
	 
 }