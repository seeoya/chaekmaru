/**
 * 
 */

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

function paymentFormSubmit() {
	console.log('paymentFormSubmit()');
	
	let form = document.payment_form;
	if (form.sb_name.value === '') {
		alert('받으실분의 이름을 입력해 주세요');
		form.sb_name.focus();
		
	} else if (form.sb_addr_code.value === '') {
		alert('받으실분의 주소를 입력해 주세요');
		form.sb_addr_code.focus();
		
	} else if (form.sb_addr.value === '') {
		alert('받으실분의 주소를 입력해 주세요');
		form.sb_addr.focus();
		
	} else if (form.sb_detail_addr.value === '') {
		alert('받으실분의 상세주소를 입력해 주세요');
		form.sb_detail_addr.focus();
		
	} else {
		
		if (form.b_count.value >= form.sb_book_count.value) {
			if (form.currentPoint.value >= form.sb_all_price.value) {
				form.submit();
			} else {
				alert('잔여 BP가 부족합니다. BP를 충전해 주세요');
			}
		} else {
			alert('도서 재고가 부족합니다. 수량을 조절하거나 관리자에게 문의하세요');
		}
		
	}
	
}
