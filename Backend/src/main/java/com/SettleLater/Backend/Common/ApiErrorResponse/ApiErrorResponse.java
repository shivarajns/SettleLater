package com.SettleLater.Backend.Common.ApiErrorResponse;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        boolean success,
        String error,
        String message,
        LocalDateTime timestamp
) {}
