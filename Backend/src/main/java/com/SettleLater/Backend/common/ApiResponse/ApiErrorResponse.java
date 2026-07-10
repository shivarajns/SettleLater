package com.SettleLater.Backend.common.ApiResponse;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        boolean success,
        String error,
        String message,
        LocalDateTime timestamp
) {}
