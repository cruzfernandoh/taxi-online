package org.taxionline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.model.base.IdModelBase;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends IdModelBase {

    @Column( nullable = false )
    String name;

    @Column( unique = true, nullable = false )
    String email;

    @Column( unique = true, nullable = false )
    String cpf;

    String carPlate;

    boolean idPassenger;

    boolean isDriver;

    @Column( nullable = false )
    String password;

    @Column( nullable = false )
    String password_algorithm;

    @Builder
    public Account( String name, String email, String cpf, String carPlate, boolean idPassenger, boolean isDriver, String password, String password_algorithm ) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.idPassenger = idPassenger;
        this.isDriver = isDriver;
        this.password = password;
        this.password_algorithm = password_algorithm;
    }
}
