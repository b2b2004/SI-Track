package com.sitrack.sitrackbackend.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품을 찾을 수 없습니다."),
    CART_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 장바구니가 존재하지 않습니다."),
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 주문정보가 존재하지 않습니다."),
    SUPPLIER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 공급업체가 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 카테고리가 존재하지 않습니다."),

    /* 401 UNAUTHORIZED : 유효한 인증 자격 증명이 없음 */
    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "관리자엑 문의 해주세요."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 타입의 토큰 입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰 입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "RefreshToken이 존재하지 않습니다."),

    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
