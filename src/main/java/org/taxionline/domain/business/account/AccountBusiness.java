package org.taxionline.domain.business.account;

import jakarta.validation.ValidationException;
import org.taxionline.infra.di.BeanInjection;
import org.taxionline.infra.exception.DuplicateAttributeException;
import org.taxionline.infra.exception.ResourceNotFoundException;
import org.taxionline.domain.entity.account.Account;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.dto.account.CreateAccountDTO;
import org.taxionline.mapper.account.AccountMapper;
import org.taxionline.port.outbound.account.AccountRepository;

import java.util.regex.Pattern;

public class AccountBusiness {

    private static final String CAR_PLATE_PATTERN = "[A-Z]{3}[0-9]{4}";

    @BeanInjection
    AccountRepository repository;

    @BeanInjection
    AccountMapper mapper;

    public AccountDTO createAccount(CreateAccountDTO dto) {
        if (dto.isDriver() && isInValidCarPlate(dto.carPlate())) throw new ValidationException("Invalid car plate");
        if (alreadyExistsAccountWithCpf(dto.cpf())) throw new DuplicateAttributeException("Cpf already exists");
        if (alreadyExistsAccountWithEmail(dto.email())) throw new DuplicateAttributeException("Email already exists");
        Account account = mapper.buildAccountFromCreateDTO(dto);
        repository.saveAccount(account);
        return mapper.toDTO(account);
    }

    public AccountDTO getAccountByIdentifier(String identifier) {
        return repository.findByIdentifier(identifier)
                .map(mapper::toDTO)
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
}
