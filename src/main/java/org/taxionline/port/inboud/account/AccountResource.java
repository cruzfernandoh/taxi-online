package org.taxionline.port.inboud.account;

import io.javalin.http.Context;

public interface AccountResource {

    void createAccount(Context ctx);

    void getAccountByIdentifier(Context ctx);
}
