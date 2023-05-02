package com.purple.core.model

enum class SettingItemTextType(
    val mainText: String,
    val subText: String,
    val isWarning: Boolean,
    val isButtonIcon: Boolean,
) {
    CHANGE_ROOM_NAME(
        "방 이름 변경",
        "나에게 보이는 방 이름을 변경할 수 있어요",
        false,
        true,
    ),
    CHANGE_NAME(
        "나의 이름 변경",
        "방에서 사용할 나의 이름을 변경할 수 있어요",
        false,
        true,
    ),
    CHANGE_PASSWORD(
        "방 비밀번호 변경",
        "방 관리자에게만 가능한 기능이에요",
        false,
        true,
    ),
    EXIT_GROUP(
        "방에서 나가기",
        "나가면 다시 초대를 받아야 들어올 수 있어요",
        true,
        false,
    ),
    DELETE_GROUP(
        "방 삭제",
        "방을 삭제하면 아무것도 남지않아요....ㅠ",
        true,
        false,
    ),
    POLICY(
        "개인 정보 처리 방침",
        "",
        false,
        true,
    ),
    APP_INFO(
        "시스템(앱) 정보",
        "",
        false,
        true,
    ),
    LOGOUT(
        "로그아웃",
        "다시 로그인하면 모든 정보를 다시 보실 수 있어요",
        true,
        false,
    ),
    DELETE_USER(
        "회원 탈퇴",
        "탈퇴하시면 지금까지의 모든 정보가 사라져요!",
        true,
        false,
    ),
}
