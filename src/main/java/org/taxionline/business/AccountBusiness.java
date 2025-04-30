package org.taxionline.business;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.taxionline.core.exception.DuplicateAttributeException;
import org.taxionline.core.exception.ResourceNotFoundException;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.dto.account.CreateAccountDTO;
import org.taxionline.model.Account;
import org.taxionline.repository.AccountRepository;

import java.util.regex.Pattern;

@ApplicationScoped
@AllArgsConstructor
public class AccountBusiness {

    private static final String CAR_PLATE_PATTERN = "[A-Z]{3}[0-9]{4}";

    public static final String REMOVE_NOT_NUMBER_REGEX = "[^0-9]";

    public static final String EMPTY = "";

    private final AccountRepository repository;

    @Transactional
    public AccountDTO createAccount( CreateAccountDTO dto ) {
        if ( dto.isDriver( ) ) validateCarPlate( dto.carPlate( ) );
        validateUniqueCpfAndEmail( dto.cpf( ), dto.email( ) );
        Account account = buildAccountFromDTO( dto );
        repository.persist( account );
        return buildAccountDTO( account );
    }

    public AccountDTO getAccountByIdentifier( String identifier ) {
        return repository.findByIdentifier( identifier )
                .map( this::buildAccountDTO )
                .orElseThrow( ( ) -> new ResourceNotFoundException( String.format( "Account with id [%s] not found", identifier ) ) );
    }

    private void validateUniqueCpfAndEmail( String cpf, String email ) {
        if ( repository.existsAccountWithCpf( cpf ) ) throw new DuplicateAttributeException( "Cpf already exists" );

        if ( repository.existsAccountWithEmail( email ) ) throw new DuplicateAttributeException( "Email already exists" );
    }

    private void validateCarPlate( String carPlate ) {
        if ( null == carPlate || carPlate.isEmpty( ) || !Pattern.matches( CAR_PLATE_PATTERN, carPlate ) )
            throw new ValidationException( "Invalid car plate" );
    }

    private AccountDTO buildAccountDTO( Account account ) {
        return AccountDTO.builder( )
                .identifier( account.getIdentifier( ) )
                .name( account.getName( ) )
                .cpf( account.getCpf( ).replaceAll( REMOVE_NOT_NUMBER_REGEX, EMPTY ) )
                .email( account.getEmail( ) )
                .idPassenger( account.isIdPassenger( ) )
                .isDriver( account.isDriver( ) )
                .carPlate( account.getCarPlate( ) )
                .build( );
    }

    private Account buildAccountFromDTO( CreateAccountDTO dto ) {
        return Account.builder( )
                .name( dto.name( ) )
                .cpf( dto.cpf( ) )
                .email( dto.email( ) )
                .carPlate( dto.carPlate( ) )
                .idPassenger( dto.idPassenger( ) )
                .isDriver( dto.isDriver( ) )
                .password( dto.password( ) )
                .password_algorithm( "must filled" )
                .build( );
    }
}
