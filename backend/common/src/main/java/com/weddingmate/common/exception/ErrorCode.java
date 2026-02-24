package com.weddingmate.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid input value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "Method not allowed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "Internal server error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "Invalid type value"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "Access denied"),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "Authentication required"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A002", "Token has expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "Invalid token"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A004", "Refresh token not found"),
    OAUTH_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "A005", "OAuth authentication failed"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User not found"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U002", "Email already exists"),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "U003", "Nickname already exists"),

    // Couple
    COUPLE_NOT_FOUND(HttpStatus.NOT_FOUND, "CP001", "Couple not found"),
    INVALID_INVITE_CODE(HttpStatus.BAD_REQUEST, "CP002", "Invalid invite code"),
    COUPLE_ALREADY_MATCHED(HttpStatus.CONFLICT, "CP003", "Already matched with a partner"),

    // Checklist
    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "CL001", "Checklist not found"),
    CHECKLIST_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CL002", "Checklist item not found"),

    // Budget
    BUDGET_NOT_FOUND(HttpStatus.NOT_FOUND, "BG001", "Budget not found"),
    BUDGET_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "BG002", "Budget category not found"),
    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "BG003", "Expense not found"),

    // Schedule
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SC001", "Schedule not found"),

    // Vendor
    VENDOR_NOT_FOUND(HttpStatus.NOT_FOUND, "VD001", "Vendor not found"),
    VENDOR_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "VD002", "Vendor category not found"),
    VENDOR_COMPARE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "VD003", "Compare limit exceeded"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "VD004", "Review not found"),

    // Community
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "CM001", "Post not found"),
    POST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CM002", "Post access denied"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CM003", "Comment not found"),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "CM004", "Follow not found"),
    FOLLOW_SELF_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "CM005", "Self follow not allowed"),
    DM_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CM006", "DM room not found"),
    DM_ROOM_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CM007", "DM room access denied"),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CM008", "Notification not found"),

    // File
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F001", "File upload failed"),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "F002", "File size exceeded"),
    UNSUPPORTED_FILE_TYPE(HttpStatus.BAD_REQUEST, "F003", "Unsupported file type"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F004", "File not found"),

    // Business
    BUSINESS_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "Business profile not found"),
    BUSINESS_ALREADY_REGISTERED(HttpStatus.CONFLICT, "B002", "Business already registered"),

    // Market
    MARKET_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "Market category not found"),
    MARKET_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "Market product not found"),
    SECONDHAND_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "M003", "Secondhand product not found"),
    SECONDHAND_PRODUCT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "M004", "Secondhand product access denied"),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "M005", "Payment not found"),
    ESCROW_NOT_FOUND(HttpStatus.NOT_FOUND, "M006", "Escrow transaction not found"),
    ESCROW_ACCESS_DENIED(HttpStatus.FORBIDDEN, "M007", "Escrow access denied"),
    OFFER_NOT_FOUND(HttpStatus.NOT_FOUND, "M008", "Price offer not found"),
    OFFER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "M009", "Price offer access denied"),
    SUBSCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "M010", "Subscription not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
