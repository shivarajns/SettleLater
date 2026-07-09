package com.SettleLater.Backend.common.ApiErrorResponse;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        boolean success,
        String error,
        String message,
        LocalDateTime timestamp
) {}
