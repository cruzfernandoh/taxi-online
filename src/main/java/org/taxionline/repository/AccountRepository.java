package org.taxionline.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.taxionline.model.Account;

import java.util.Optional;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<Account, Long> {

    public Optional<Account> findByIdentifier( String identifier ) {
        return find( "identifier", identifier ).singleResultOptional( );
    }

    public boolean existsAccountWithEmail( String email ) {
        return count( "email", email ) > 0;
    }

    public boolean existsAccountWithCpf( String cpf ) {
        return count( "cpf", cpf ) > 0;
    }
}
