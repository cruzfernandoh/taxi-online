package org.taxionline.domain.entity.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.domain.entity.base.IdModelBase;
import org.taxionline.domain.entity.vo.CPF;
import org.taxionline.domain.entity.vo.Email;
import org.taxionline.domain.entity.vo.Name;
import org.taxionline.domain.entity.vo.Password;
import org.taxionline.domain.factory.password.PasswordFactory;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends IdModelBase {

    @Column(nullable = false)
    private Name name;

    @Column(unique = true, nullable = false)
    private Email email;

    @Column(unique = true, nullable = false)
    private CPF cpf;

    private String carPlate;

    private boolean isPassenger;

    private boolean isDriver;

    @Column(nullable = false)
    private Password password;

    @Column(nullable = false)
    private String password_algorithm;

    @Builder
    public Account(String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver, String password, String password_algorithm) {
        this.name = new Name(name);
        this.email = new Email(email);
        this.cpf = new CPF(cpf);
        this.carPlate = carPlate;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
        this.password = PasswordFactory.create(password, password_algorithm);
        this.password_algorithm = password_algorithm;
    }
}
