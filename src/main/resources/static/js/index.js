document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("to_top").addEventListener("click", function () {
        window.scrollTo(0, 0);
    });

    document.querySelectorAll("form.member").forEach((el) => {
        el.addEventListener("submit", function (e) {});
    });

    document.querySelectorAll("form.member input").forEach((el) => {
        el.addEventListener("keyup", function (e) {
            console.log(e);
            if (e.keyCode == 13) {
                document.getElementById("submit_btn").click();
            }
        });
    });

    document.querySelectorAll("button.submit").forEach((el) => {
        el.addEventListener("click", function (e) {
            let form = e.target.closest("form");

            if (form.r_stars.value > 5 || form.r_stars.value < 1) {
                alert("1~5 사이의 별점을 선택해 주세요.");
                form.r_stars.value = 1;
                form.r_stars.focus();
            } else if (form.r_text.value == "") {
                alert("리뷰를 입력해 주세요.");
                form.r_text.focus();
            } else {
                form.submit();
            }
        });
    });
});
