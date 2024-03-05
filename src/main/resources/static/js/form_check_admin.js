
/* Admin Member */

function createAccountForm() {
    console.log("createAccountForm!!");

    let form = document.create_account_form;

    if (form.a_id.value === "") {
        alert("아이디를 입력해 주세요.");
        form.a_id.focus();
    } else if (form.a_pw.value === "") {
        alert("비밀번호를 입력해 주세요.");
        form.a_pw.focus();
    } else if (form.a_name.value === "") {
        alert("이름을 입력해 주세요.");
        form.a_name.focus();    
    } else if (form.a_phone.value === "") {
        alert("연락처를 입력해 주세요.");
        form.a_phone.focus();    
    } else {
        form.submit();
    }
}


function loginForm() {
    console.log("loginForm!!");

    let form = document.login_form;

    if (form.a_id.value === "") {
        alert("아이디를 입력해 주세요.");
        form.a_id.focus();
    } else if (form.a_pw.value === "") {
        alert("비밀번호를 입력해 주세요.");
        form.a_pw.focus();        
    } else {
        form.submit();
    }
}


function modifyAccountForm() {
    console.log("modifyAccountForm!!");

    let form = document.modify_account_form;
	
	if (form.a_pw.value === "") {
        alert("비밀번호를 입력해 주세요.");
        form.a_pw.focus();        
    } else if (form.a_phone.value === "") {
        alert("연락처를 입력해 주세요.");
        form.a_phone.focus();    
    } else {
        form.submit();
    }
}

function bookRegistForm() {
    console.log("bookRegistForm!!");

    let form = document.book_regist_form;
	
	if (form.b_thumbnail.value === "") {
        alert("Thumbnail link를 입력해 주세요.");
        form.b_thumbnail.focus();        
    } else if (form.b_name.value === "") {
        alert("책 이름을 입력해 주세요.");
        form.b_name.focus();
    } else if (form.b_author.value === "") {
        alert("책 저자를 입력해 주세요.");
        form.b_author.focus();
    } else if (form.b_introduce.value === "") {
        alert("책 소개를 입력해 주세요.");
        form.b_introduce.focus();
    } else if (form.b_publisher.value === "") {
        alert("출판사명을 입력해 주세요.");
        form.b_publisher.focus();
    } else if (form.b_publish_date.value === "") {
        alert("출판일을 입력해 주세요.");
        form.b_publish_date.focus();
    } else if (form.b_kdc.value === "") {
        alert("책 분류기호를 입력해 주세요.");
        form.b_kdc.focus();
    } else if (form.b_isbn.value === "") {
        alert("ISBN을 입력해 주세요.");
        form.b_isbn.focus();
    } else if (form.b_price.value === "") {
        alert("책 가격을 입력해 주세요.");
        form.b_price.focus();
    } else if (form.b_count.value === "") {
        alert("입고 수량을 입력해 주세요.");
        form.b_count.focus();
    } else {
        form.submit();
    }
}
