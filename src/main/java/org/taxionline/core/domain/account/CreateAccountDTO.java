package org.taxionline.core.domain.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CreateAccountDTO( @NotBlank( message = "Field name mandatory" ) String name,
                                @CPF( message = "Invalid CPF" ) @NotNull( message = "Field CPF mandatory" ) String cpf,
                                @Email( message = "Invalid email" ) @NotBlank( message = "Field email mandatory" ) String email,
                                String carPlate, boolean idPassenger, boolean isDriver,
                                String password, String password_algorithm ) {
}
