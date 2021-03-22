package com.mgreis.socket.client;

import com.mgreis.socket.enums.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This program is a socket client application that connects to a server, sends a message and retrieve a response
 */
public class ClientSocketImpl implements ClientSocket<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSocketImpl.class);

    private String szHostname;

    private int nPort;

    private Socket mySocket;

    private PrintWriter out;

    private BufferedReader in;

    public ClientSocketImpl(String szHostname, int nPort) {
        this.szHostname = szHostname;
        this.nPort = nPort;
    }

    public int startConnection() {

        try {
            mySocket = new Socket(szHostname, nPort);

            out = new PrintWriter(mySocket.getOutputStream(), true);

            in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            return Const.NO_ERROR;

        } catch (IOException exc) {

            LOGGER.warn("An exception has occurred while connecting the client socket", exc);

            exc.printStackTrace();

            return Const.ERROR;

        }
    }

    public String sendMessage(String msg) {
        out.println(msg);
        String resp = null;
        try {
            resp = in.readLine();
        } catch (IOException exc) {
            LOGGER.warn("An exception has occurred while sending a message", exc);
        }
        return resp;
    }

    public int stopConnection() {
        try {
            in.close();
            out.close();
            mySocket.close();
            return Const.NO_ERROR;
        } catch (IOException exc) {
            LOGGER.warn("An exception has occurred while disconnecting the client socket", exc);
            return Const.ERROR;
        }
    }
}
