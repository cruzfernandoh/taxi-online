package org.taxionline.adapter.inboud.account;

import io.javalin.http.Context;
import jakarta.validation.executable.ValidateOnExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.domain.business.account.AccountBusiness;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.dto.account.CreateAccountDTO;
import org.taxionline.infra.di.BeanInjection;
import org.taxionline.port.inboud.account.AccountResource;

@ValidateOnExecution
public class AccountResourceAdapter implements AccountResource {

    private static final Logger logger = LoggerFactory.getLogger(AccountResourceAdapter.class);

    @BeanInjection
    AccountBusiness business;

    @Override
    public void createAccount(Context ctx) {
        CreateAccountDTO dto = ctx.bodyValidator(CreateAccountDTO.class).get();
        logger.info("Creating account for: {}", dto);
        AccountDTO response = business.createAccount(dto);
        ctx.json(response);
    }

    @Override
    public void getAccountByIdentifier(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        logger.info("Fetching account by identifier: {}", identifier);
        AccountDTO response = business.getAccountByIdentifier(identifier);
        ctx.json(response);
    }
}
