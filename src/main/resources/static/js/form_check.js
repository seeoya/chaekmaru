/**
 *
 */

/* Member */

function createAccountForm() {
    console.log("createAccountForm!!");

    let form = document.create_account_form;

    if (form.m_id.value === "") {
        alert("아이디를 입력해 주세요.");
        form.m_id.focus();
    } else if (form.m_pw.value === "") {
        alert("비밀번호를 입력해 주세요.");
        form.m_pw.focus();
    } else if (form.m_name.value === "") {
        alert("이름을 입력해 주세요.");
        form.m_name.focus();
    } else if (form.m_mail.value === "") {
        alert("메일을 입력해 주세요.");
        form.m_mail.focus();
    } else if (form.m_phone.value === "") {
        alert("연락처를 입력해 주세요.");
        form.m_phone.focus();
    } else if (form.m_addr.value === "") {
        alert("주소란이 비어있습니다..");
        document.getElementById("search_address_btn").focus();
    } else if (form.m_detail_addr.value === "") {
        alert("상세주소를 입력해 주세요.");
        form.m_detail_addr.focus();
    } else {
        form.submit();
    }
}

/* 다음 주소 */
function searchAddress() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ""; // 주소 변수
            var extraAddr = ""; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === "R") {
                // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else {
                // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === "R") {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== "" && data.apartment === "Y") {
                    extraAddr += extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== "") {
                    extraAddr = " (" + extraAddr + ")";
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("postcode").value = data.zonecode;
            document.getElementById("address").value = addr + " " + extraAddr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        },
    }).open();
}

/* 로그인 */
function loginForm() {
    console.log("loginForm!!");

    let form = document.login_form;

    if (form.m_id.value === "") {
        alert("아이디를 입력해 주세요.");
        form.m_id.focus();
    } else if (form.m_pw.value === "") {
        alert("비밀번호를 입력해 주세요.");
        form.m_pw.focus();
    } else {
		form.submit();
	}
    
}

/* 정보 수정 */

function modifyForm() {
    console.log("modifyForm!!");

    let form = document.modify_form;

    if (form.m_name.value === "") {
        alert("변경하실 이름을 입력해 주세요.");
        form.m_name.focus();
    } else if (form.m_mail.value === "") {
        alert("변경하실 메일을 입력해 주세요.");
        form.m_mail.focus();
    } else if (form.m_phone.value === "") {
        alert("변경하실 연락처를 입력해 주세요.");
        form.m_phone.focus();
    } else if (form.m_addr.value === "") {
        alert("변경하실 주소란이 비어있습니다..");
        document.getElementById("search_address_btn").focus();
    } else if (form.m_detail_addr.value === "") {
        alert("변경하실 상세주소를 입력해 주세요.");
        form.m_detail_addr.focus();
    } else {
        form.submit();
    }
}

/* ID 찾기 */
function findIdForm() {
    console.log("findIdForm!!");

    let form = document.find_id_form;

    if (form.m_name.value === "") {
        alert("이름을 입력해 주세요.");
        form.m_name.focus();
    } else if (form.m_mail.value === "") {
        alert("이메일을 입력해 주세요.");
        form.m_mail.focus();
    } else {
		form.submit();
	}
    
}

function findIdForm() {
	console.log("findIdForm!!");
	let form = document.find_id_form;

    if (form.m_name.value === "") {
        alert("이름을 입력해 주세요.");
        form.m_name.focus();
    } else if (form.m_mail.value === "") {
        alert("이메일을 입력해 주세요.");
        form.m_mail.focus();
    } else {
		form.submit();
	}
}

function findPwForm() {
	console.log("findPwForm!!");
	let form = document.find_pw_form;

    if (form.m_id.value === "") {
		alert("아이디를 입력해 주세요.");
        form.m_id.focus();
	} else if (form.m_name.value === "") {
        alert("이름을 입력해 주세요.");
        form.m_name.focus();
    } else if (form.m_mail.value === "") {
        alert("이메일을 입력해 주세요.");
        form.m_mail.focus();
    } else {
		form.submit();
	}
}


function pwModifyForm() {
	console.log("pwModifyForm!!");
	let form = document.pw_modify_form;

    if (form.m_pw.value === "") {
		alert("비밀번호를 입력해 주세요.");
        form.m_pw.focus();
	} else if (form.m_pw_again.value === "") {
        alert("비밀번호 재입력칸을 입력해 주세요.");
        form.m_pw_again.focus();
    } else {
		form.submit();
	}
}
