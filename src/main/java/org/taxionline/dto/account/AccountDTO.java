package org.taxionline.dto.account;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AccountDTO {

    String identifier;

    String name;

    String email;

    String cpf;

    String carPlate;

    boolean idPassenger;

    boolean isDriver;

    @Builder
    public AccountDTO( String identifier, String name, String email, String cpf, String carPlate, boolean idPassenger, boolean isDriver ) {
        this.identifier = identifier;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.idPassenger = idPassenger;
        this.isDriver = isDriver;
    }
}
