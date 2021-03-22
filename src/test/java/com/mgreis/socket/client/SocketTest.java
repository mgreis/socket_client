package com.mgreis.socket.client;

import com.mgreis.socket.enums.Const;
import com.mgreis.socket.server.MyServerSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SocketTest {

    private int nPort = 6666;

    MyServerSocket myServerSocket;

    Thread t1;

    private ClientSocket<String, String> myClientSocket;

    @BeforeEach
    public void setup() {
        myServerSocket = new MyServerSocket(nPort, "Hello World");
        Thread t1 = new Thread(myServerSocket);
        t1.start();
        System.out.println(Thread.currentThread().getName()
                + ", executing run() method!");
    }

    @Test
    public void testImpl() {
        myClientSocket = new ClientSocketImpl("localhost", nPort);
        int nResult = myClientSocket.startConnection();

        if (nResult == Const.NO_ERROR) {
            System.out.println(myClientSocket.sendMessage("HELLO"));
        } else {
            System.out.println("FAIL");
        }

    }

}
