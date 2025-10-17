package org.taxionline.core.business.account;

import jakarta.validation.ValidationException;
import org.taxionline.config.exception.DuplicateAttributeException;
import org.taxionline.config.exception.ResourceNotFoundException;
import org.taxionline.core.domain.account.Account;
import org.taxionline.core.domain.account.AccountDTO;
import org.taxionline.core.domain.account.CreateAccountDTO;
import org.taxionline.di.BeanInjection;
import org.taxionline.port.account.AccountRepository;

import java.util.regex.Pattern;

public class AccountBusiness {

    private static final String CAR_PLATE_PATTERN = "[A-Z]{3}[0-9]{4}";

    public static final String REMOVE_NOT_NUMBER_REGEX = "[^0-9]";

    public static final String EMPTY = "";

    @BeanInjection
    AccountRepository repository;

    public AccountDTO createAccount(CreateAccountDTO dto) {
        if (dto.isDriver() && isInValidCarPlate(dto.carPlate())) throw new ValidationException("Invalid car plate");
        if (alreadyExistsAccountWithCpf(dto.cpf())) throw new DuplicateAttributeException("Cpf already exists");
        if (alreadyExistsAccountWithEmail(dto.email())) throw new DuplicateAttributeException("Email already exists");
        Account account = buildAccountFromDTO(dto);
        repository.saveAccount(account);
        return buildAccountDTO(account);
    }

    public AccountDTO getAccountByIdentifier(String identifier) {
        return repository.findByIdentifier(identifier)
                .map(this::buildAccountDTO)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with id [%s] not found", identifier)));
    }

    private boolean alreadyExistsAccountWithCpf(String cpf) {
        return repository.existsAccountWithCpf(cpf);
    }

    private boolean alreadyExistsAccountWithEmail(String email) {
        return repository.existsAccountWithEmail(email);
    }

    private boolean isInValidCarPlate(String carPlate) {
        return null == carPlate || carPlate.isEmpty() || !Pattern.matches(CAR_PLATE_PATTERN, carPlate);
    }

    private AccountDTO buildAccountDTO(Account account) {
        return AccountDTO.builder()
                .identifier(account.getIdentifier())
                .name(account.getName())
                .cpf(account.getCpf().replaceAll(REMOVE_NOT_NUMBER_REGEX, EMPTY))
                .email(account.getEmail())
                .idPassenger(account.isIdPassenger())
                .isDriver(account.isDriver())
                .carPlate(account.getCarPlate())
                .build();
    }

    private Account buildAccountFromDTO(CreateAccountDTO dto) {
        return Account.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .carPlate(dto.carPlate())
                .idPassenger(dto.idPassenger())
                .isDriver(dto.isDriver())
                .password(dto.password())
                .password_algorithm("must filled")
                .build();
    }
}
