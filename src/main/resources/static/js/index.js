document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("to_top").addEventListener("click", function () {
        window.scrollTo(0, 0);
    });

	document.querySelectorAll("form.member").forEach((el) => {
		el.addEventListener("submit", function (e) {
		})
	});

    document.querySelectorAll("form.member input").forEach((el) => {
        el.addEventListener("keyup", function (e) {
			console.log(e);
            if (e.keyCode == 13) {

                document.getElementById("submit_btn").click();
            }
        });
    });
});
