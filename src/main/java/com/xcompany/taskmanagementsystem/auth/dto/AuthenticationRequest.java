package com.xcompany.taskmanagementsystem.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Information about authentication request")
public class AuthenticationRequest {

    @NotBlank
    @Email(message = "Username should be correct Email address.")
    @Schema(description = "Email")
    private String username;

    @NotBlank
    @Schema(description = "Password")
    private String password;
}
