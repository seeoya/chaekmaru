
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
