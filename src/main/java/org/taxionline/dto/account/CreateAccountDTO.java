package org.taxionline.dto.account;

public record CreateAccountDTO(String name, String cpf, String email,
                               String carPlate, boolean idPassenger, boolean isDriver,
                               String password, String password_algorithm) {
}
