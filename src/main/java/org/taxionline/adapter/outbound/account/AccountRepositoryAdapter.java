package org.taxionline.adapter.outbound.account;

import org.taxionline.infra.repository.BaseRepository;
import org.taxionline.domain.entity.account.Account;
import org.taxionline.domain.entity.vo.CPF;
import org.taxionline.domain.entity.vo.Email;
import org.taxionline.port.outbound.account.AccountRepository;

import java.util.Optional;

public class AccountRepositoryAdapter extends BaseRepository<Account> implements AccountRepository {

    @Override
    public Optional<Account> findByIdentifier(String identifier) {
        return find("identifier = ?1", identifier).singleResultOptional();
    }

    @Override
    public boolean existsAccountWithEmail(String email) {
        return count("email = ?1", new Email(email)) > 0;
    }

    @Override
    public boolean existsAccountWithCpf(String cpf) {
        return count("cpf = ?1", new CPF(cpf)) > 0;
    }

    @Override
    public void saveAccount(Account account) {
        persist(account);
    }

}
