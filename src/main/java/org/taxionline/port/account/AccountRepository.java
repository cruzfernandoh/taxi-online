package org.taxionline.port.account;

import org.taxionline.core.domain.account.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByIdentifier(String identifier);

    boolean existsAccountWithEmail(String email);

    boolean existsAccountWithCpf(String cpf);

    void saveAccount(Account account);
}
