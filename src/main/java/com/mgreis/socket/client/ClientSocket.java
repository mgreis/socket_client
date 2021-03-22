package com.mgreis.socket.client;

public interface ClientSocket<I,O> {

    int startConnection();

    O sendMessage(I msg);

    int stopConnection();

}
