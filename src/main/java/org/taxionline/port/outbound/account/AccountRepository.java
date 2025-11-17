package org.taxionline.port.outbound.account;

import org.taxionline.domain.entity.account.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByIdentifier(String identifier);

    boolean existsAccountWithEmail(String email);

    boolean existsAccountWithCpf(String cpf);

    void saveAccount(Account account);
}
