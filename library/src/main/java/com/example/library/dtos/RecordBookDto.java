package com.example.library.dtos;

import jakarta.validation.constraints.NotBlank;

public record RecordBookDto(@NotBlank String name, @NotBlank String author) {

}
