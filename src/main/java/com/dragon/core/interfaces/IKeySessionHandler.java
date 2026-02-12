package com.dragon.core.interfaces;

import com.dragon.core.network.Message;

public interface IKeySessionHandler {

    void sendKey(final ISession p0);

    void setKey(ISession var1, Message var2) throws Exception;
}

