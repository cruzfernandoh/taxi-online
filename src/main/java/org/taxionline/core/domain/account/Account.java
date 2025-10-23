package org.taxionline.core.domain.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.core.domain.base.IdModelBase;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends IdModelBase {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String carPlate;

    private boolean idPassenger;

    private boolean isDriver;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String password_algorithm;

    @Builder
    public Account(String name, String email, String cpf, String carPlate, boolean idPassenger, boolean isDriver, String password, String password_algorithm) {
        this.name = new Name(name).getValue();
        this.email = new Email(email).getValue();
        this.cpf = new CPF(cpf).getValue();
        this.carPlate = carPlate;
        this.idPassenger = idPassenger;
        this.isDriver = isDriver;
        this.password = password;
        this.password_algorithm = password_algorithm;
    }
}
