package org.taxionline.adapter.inboud;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.core.domain.account.AccountDTO;
import org.taxionline.core.domain.account.CreateAccountDTO;
import org.taxionline.di.BeanInjection;

public class AccountResource {

    private static final Logger logger = LoggerFactory.getLogger(AccountResource.class);

    @BeanInjection
    AccountBusiness business;

    public void createAccount(Context ctx) {
        CreateAccountDTO dto = ctx.bodyAsClass(CreateAccountDTO.class);
        logger.info("Creating account for: {}", dto);
        AccountDTO response = business.createAccount(dto);
        ctx.json(response);
    }

    public void getAccountByIdentifier(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        logger.info("Fetching account by identifier: {}", identifier);
        AccountDTO response = business.getAccountByIdentifier(identifier);
        ctx.json(response);
    }
}
