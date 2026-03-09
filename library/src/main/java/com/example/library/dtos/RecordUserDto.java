package com.example.library.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecordUserDto(@NotBlank String name,
                            @NotBlank @Email(message = "The email format is not valid.") String email,
                            @NotBlank @Size(min = 6, message =
                                    "The password must be at least 6 characters.") String password) {

}
