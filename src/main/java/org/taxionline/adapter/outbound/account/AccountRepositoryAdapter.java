package org.taxionline.adapter.outbound.account;

import org.taxionline.config.repository.BaseRepository;
import org.taxionline.core.domain.account.Account;
import org.taxionline.port.outbound.account.AccountRepository;

import java.util.Optional;

public class AccountRepositoryAdapter extends BaseRepository<Account> implements AccountRepository {

    @Override
    public Optional<Account> findByIdentifier(String identifier) {
        return find("identifier = ?1", identifier).singleResultOptional();
    }

    @Override
    public boolean existsAccountWithEmail(String email) {
        return count("email = ?1", email) > 0;
    }

    @Override
    public boolean existsAccountWithCpf(String cpf) {
        return count("cpf = ?1", cpf) > 0;
    }

    @Override
    public void saveAccount(Account account) {
        persist(account);
    }

}
