document.addEventListener("DOMContentLoaded", function () {


	function bookCountSubmit() {
		console.log('bookCountSubmit()');

		let bookCount = parseInt(document.getElementsByName("c_book_count").value);
		if (bookCount <= 0) {
			alert('올바른 수량을 입력해주세요');

		} else {
			form.submit();
		}
	}


	document.querySelectorAll(".modify-count").forEach((e) => {

		e.addEventListener("click", function () {
			let count = this.previousElementSibling.value;
			
			location.href = this.dataset.link + "&c_book_count=" + count;
		})
	})
})




