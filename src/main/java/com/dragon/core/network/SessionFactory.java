package com.dragon.core.network;

import java.net.Socket;

import com.dragon.core.interfaces.ISession;

public class SessionFactory {

    private static SessionFactory instance;

    public static SessionFactory gI() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }

    public ISession cloneSession(Class<?> clazz, Socket socket) throws Exception {
        return (ISession) clazz.getConstructor(Socket.class).newInstance(socket);
    }
}

