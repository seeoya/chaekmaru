
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

function registBookForm() {
    console.log("registBookForm!!");

    let form = document.regist_book_form;
	
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

function modifyBookForm() {
    console.log("modifyBookForm!!");

    let form = document.modify_book_form;
	
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

function deleteAdminConfirm(a_no) {
	console.log("deleteAdminConfirm()");
	
	let result = confirm("정말 삭제하시겠습니까?");
	if(result){
		
		location.href= "/admin/member/delete_account_confirm" + "?a_no=" + a_no;
	}
	
}

function deleteBookConfirm(b_no) {
	console.log("deleteBookConfirm()");
	
	let result = confirm("정말 삭제하시겠습니까?");
	if(result){
		
		location.href= "/admin/book/delete_book_confirm" + "?b_no=" + b_no;
	}
	
}

function modifyPointForm() {
    console.log("modifyPointForm!!");

    let form = document.modify_point_form;
	
	if (form.pl_payment_book_point.value === "") {
        alert("적립하거나 차감할 BP를 입력해 주세요.");
        form.pl_payment_book_point.focus();        
    
    } else {
        form.submit();
    }  
    
}


function userAccountActiveConfirm(m_id) {
	console.log("userAccountActiveConfirm()");
	
	let result = confirm("정말 비활성화하시겠습니까?");
	if(result){
		
		location.href= "/admin/shop/user_account_active_confirm" + "?m_id=" + m_id;
	}
	
}



function returnNotApprovedConfirm(sb_no) {
	console.log("returnNotApprovedConfirm()");
	
	let result = confirm("정말 반품 승인불가 처리를 하시겠습니까?");
	if(result){
		
		location.href= "/admin/shop/return_notapproved_confirm" + "?sb_no=" + sb_no;
	}
	
}

function modifyBookInventoryForm() {
    console.log("modifyBookInventoryForm!!");

    let form = document.modify_book_inventory_form;
	
	if (form.b_count.value === "") {
        alert("입출고 수량을 입력해 주세요.");
        form.b_count.focus();
    } else {
        form.submit();
    }  
    
}

function saledListByDate(){
	console.log("saledListByDate!!");
	
    let form = document.saled_book_by_date;
	
	console.log(form.saled_start.value);
	if (form.saled_start.value === "") {
        alert("시작일을 입력해 주세요.");
        form.saled_start.focus();        
    } else if (form.saled_end.value === "") {
        alert("종료일을 입력해 주세요.");
        form.saled_end.focus(); 
    } else {
        form.submit();
    }  
    
	
}

function searchBookForm() {
	console.log("searchBookForm!!");

    let form = document.search_book_form;
	
	if (form.search.value === "") {
        alert("검색어를 입력해 주세요.");
        form.search.focus();        
    } else {
        form.submit();
    }  
    
	
}