package com.mgreis.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerSocket.class);

    public static final String STOP = "STOP";

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int nPort;
    private String szResponse;
    private boolean bRunning = true;

    public MyServerSocket(int nPort, String szResponse) {
        this.nPort = nPort;
        this.szResponse = szResponse;
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            LOGGER.debug("Stopping server");
        }
    }

    public void run() {
        try {
            System.out.println("piu!");
            serverSocket = new ServerSocket(nPort);

            while (bRunning) {

                System.out.println("piu!");

                clientSocket = serverSocket.accept();

                out = new PrintWriter(clientSocket.getOutputStream(), true);

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String szRequest = in.readLine();

                if (STOP.equals(szRequest)) {

                    LOGGER.debug("Stopping server");

                    this.stop();

                    bRunning = false;
                } else {

                    LOGGER.debug(szRequest);

                    out.println(szResponse);
                }
            }

        } catch (IOException exc) {
            LOGGER.warn("An exception was  thrown while running the socket server", exc);

        }
    }
}
