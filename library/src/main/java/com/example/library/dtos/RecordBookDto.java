package com.example.library.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecordBookDto(@NotBlank String name, @NotBlank String author,@NotBlank String isbn,@NotNull @Positive Integer pages) {

}
