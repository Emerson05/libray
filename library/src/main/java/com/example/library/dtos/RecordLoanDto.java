package com.example.library.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RecordLoanDto(@NotNull UUID userId, @NotNull UUID bookId, @NotNull Integer daysToReturn ) {


}


