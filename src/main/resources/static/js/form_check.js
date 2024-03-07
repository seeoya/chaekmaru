function createAccountForm() {

	// 입력값 가져오기
	// var id = document.getElementsByName('m_id')[0].value;
	// var pw = document.getElementsByName('m_pw')[0].value;
	var name = document.getElementsByName('m_name')[0].value;
	// var email = document.getElementsByName('m_mail')[0].value;
	var phone = document.getElementsByName('m_phone')[0].value;
	var postcode = document.getElementsByName('m_addr_code')[0].value;
	var address = document.getElementsByName('m_addr')[0].value;
	var detailAddress = document.getElementsByName('m_detail_addr')[0].value;


	// 아이디 검증: 영어 소문자와 숫자로만 구성되어야 함

	switch (idCheck()) {
		case 1:
			break;
		case 2:
			alert('아이디는 최소 5자 이상이어야 합니다.');
			document.getElementsByName('m_id')[0].focus();
			return false;
			break;
		case 3:
			alert('아이디는 영어 소문자와 숫자로만 구성되어야 합니다.');
			document.getElementsByName('m_id')[0].focus();
			return false;
			break;
	}

	// 비밀번호 검증: 최소 8자 이상 및 특수문자 1개 이상 포함
	switch (pwCheck()) {
		case 1:
			break;
		case 2:
			alert('비밀번호는 최소 8자 이상이어야 하며, 특수문자를 최소 1개 이상 포함해야 합니다.');
			document.getElementsByName('m_pw')[0].focus();
			break;
	}

	// 이름 검증: 빈 값인지 확인
	if (name.trim() === '') {
		alert('이름을 입력해 주세요.');
		document.getElementsByName('m_name')[0].focus();
		return;
	}

	// 이메일 주소 검증: 정규식 사용
	switch (mailCheck()) {
		case 1:
			break;
		case 2:
			alert('유효한 이메일 주소를 입력해 주세요.');
			document.getElementsByName('m_mail')[0].focus();
			break;
	}

	// 전화번호 검증: 숫자로 이루어진 10자리 또는 11자리
	var phoneRegex = /^\d{10,11}$/;
	if (!phoneRegex.test(phone)) {
		alert('유효한 전화번호를 입력해 주세요.');
		document.getElementsByName('m_phone')[0].focus();
		return;
	}

	// 우편번호 검증: 숫자로 이루어진 5자리
	var postcodeRegex = /^\d{5}$/;
	if (!postcodeRegex.test(postcode)) {
		alert('유효한 우편번호를 입력해 주세요.');
		document.getElementById('search_address_btn').focus();
		return;
	}

	// 주소 검증: 빈 값인지 확인
	if (address.trim() === '') {
		alert('주소를 입력해 주세요.');
		document.getElementById('search_address_btn').focus();
		return;
	}

	// 상세주소 검증: 빈 값인지 확인
	if (detailAddress.trim() === '') {
		alert('상세주소를 입력해 주세요.');
		document.getElementsByName('m_detail_addr')[0].focus();
		return;
	}

	// 유효한 경우 폼 제출
	document.create_account_form.submit();
}

function idCheck() {
	let id = document.getElementsByName('m_id')[0].value;

	// 아이디 검증: 영어 소문자와 숫자로만 구성되어야 함
	if (id.length < 5) {
		return 2;
	}

	var idRegex = /^[a-z0-9]+$/;
	if (!idRegex.test(id)) {
		return 3;
	}

	return 1;
}

function pwCheck() {
	let pw = document.getElementsByName('m_pw')[0].value;
	let pwRegex = /^(?=.*[a-zA-Z0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}$/;

	if (!pwRegex.test(pw)) {
		return 2;
	}

	return 1;
}

function mailCheck() {
	var email = document.getElementsByName('m_mail')[0].value;
	var emailRegex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;

	if (!emailRegex.test(email)) {
		return 2;
	}

	return 1;
}

/* 전화번호에 숫자만 들어올 수 있게 */
function extractNumbers(input) {
	// 입력된 값에서 숫자만 추출하여 새로운 값으로 설정
	var cleanedValue = input.value.replace(/\D/g, '');

	// 추출된 숫자를 입력 필드의 값으로 설정
	input.value = cleanedValue;
}

function showMessage(input) {
	let messageEl = document.getElementById("message_" + input.name);
	let iconEl = document.getElementById("icon_" + input.name);

	if (messageEl && iconEl) {
		messageEl.style.color = "#ff0000";
		iconEl.style.color = "#ff0000";

		iconEl.innerHTML = "<i class='fa-solid fa-circle-xmark'></i>";
		messageEl.style.display = 'block';
	}
}

function hideMessage(input) {
	let messageEl = document.getElementById("message_" + input.name);
	let iconEl = document.getElementById("icon_" + input.name);

	if (messageEl && iconEl) {
		messageEl.style.color = "var(--main-light-color)";
		iconEl.style.color = "var(--main-light-color)";

		iconEl.innerHTML = "<i class='fa-solid fa-circle-check'></i>";
		messageEl.style.display = "none";
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

document.addEventListener("DOMContentLoaded", function () {

	document.querySelectorAll("form input").forEach(el => {
		el.addEventListener("change", function (e) {
			inputTextCheck(e);
		})
		el.addEventListener("focus", function (e) {
			inputTextCheck(e);
		})
		el.addEventListener("focusout", function (e) {
			inputTextCheck(e);
		})
	});


	function inputTextCheck(e) {
		let inputEl = e.target;
		let inputName = inputEl.name;

		let result = 0;

		switch (inputName) {
			case "m_id":
				result = idCheck();
				break;

			case "m_pw":
				result = pwCheck();
				break;

			case "m_mail":
				result = mailCheck();
				break;
			default:
				break;
		}

		console.log(result);
		
		if (result == 1) {
			hideMessage(inputEl);
		} else {
			showMessage(inputEl);
		}
	}
})
