package com.dragon.core.interfaces;

import com.dragon.core.network.Message;

public interface IMessageHandler {

    void onMessage(final ISession p0, final Message p1) throws Exception;
}

