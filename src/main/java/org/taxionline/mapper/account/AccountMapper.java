package org.taxionline.mapper.account;

import org.taxionline.domain.entity.account.Account;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.dto.account.CreateAccountDTO;
import org.taxionline.mapper.BaseMapper;

import java.util.Optional;

public class AccountMapper implements BaseMapper<Account, AccountDTO> {

    public static final String REMOVE_NOT_NUMBER_REGEX = "[^0-9]";

    public static final String EMPTY = "";

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO toDTO(Account account) {
        return Optional.ofNullable(account)
                .map(t -> AccountDTO.builder()
                        .identifier(t.getIdentifier())
                        .name(t.getName().getValue())
                        .cpf(t.getCpf().getValue().replaceAll(REMOVE_NOT_NUMBER_REGEX, EMPTY))
                        .email(t.getEmail().getValue())
                        .idPassenger(t.isPassenger())
                        .isDriver(t.isDriver())
                        .carPlate(t.getCarPlate())
                        .build())
                .orElse(null);
    }

    public Account buildAccountFromCreateDTO(CreateAccountDTO dto) {
        return Optional.ofNullable(dto)
                .map(t -> Account.builder()
                        .name(t.name())
                        .cpf(t.cpf())
                        .email(t.email())
                        .carPlate(t.carPlate())
                        .isPassenger(t.idPassenger())
                        .isDriver(t.isDriver())
                        .password(t.password())
                        .password_algorithm(t.password_algorithm())
                        .build())
                .orElse(null);
    }
}
