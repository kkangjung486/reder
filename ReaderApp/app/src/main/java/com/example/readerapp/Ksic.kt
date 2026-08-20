package com.example.readerapp

/**
 * 한국표준산업분류(KSIC) 대분류 목록입니다.
 *
 * 통계청이 정한 공식 분류로, 정책자금 신청서에서 쓰는 "업종"이 바로 이 분류입니다.
 * 인터넷 연결 없이 앱 안에 들어있는 값이라 항상 빠르게 뜹니다.
 *
 * 나중에 더 자세한 분류(중분류: 제조업 -> 식료품 제조업 ...)가 필요하면
 * 이 파일에 목록만 추가하면 됩니다.
 *
 * @param code 분류 기호 (A ~ U)
 * @param name 업종 이름
 */
data class Industry(val code: String, val name: String) {
    /** 화면에 보여줄 글자입니다. 예) "C. 제조업" */
    val label: String get() = "$code. $name"
}

/** 업종 대분류 21개 (A ~ U) */
val KSIC_INDUSTRIES: List<Industry> = listOf(
    Industry("A", "농업, 임업 및 어업"),
    Industry("B", "광업"),
    Industry("C", "제조업"),
    Industry("D", "전기, 가스, 증기 및 공기 조절 공급업"),
    Industry("E", "수도, 하수 및 폐기물 처리, 원료 재생업"),
    Industry("F", "건설업"),
    Industry("G", "도매 및 소매업"),
    Industry("H", "운수 및 창고업"),
    Industry("I", "숙박 및 음식점업"),
    Industry("J", "정보통신업"),
    Industry("K", "금융 및 보험업"),
    Industry("L", "부동산업"),
    Industry("M", "전문, 과학 및 기술 서비스업"),
    Industry("N", "사업시설 관리, 사업 지원 및 임대 서비스업"),
    Industry("O", "공공행정, 국방 및 사회보장 행정"),
    Industry("P", "교육 서비스업"),
    Industry("Q", "보건업 및 사회복지 서비스업"),
    Industry("R", "예술, 스포츠 및 여가관련 서비스업"),
    Industry("S", "협회 및 단체, 수리 및 기타 개인 서비스업"),
    Industry("T", "가구 내 고용활동 및 달리 분류되지 않은 자가 소비 생산활동"),
    Industry("U", "국제 및 외국기관")
)
