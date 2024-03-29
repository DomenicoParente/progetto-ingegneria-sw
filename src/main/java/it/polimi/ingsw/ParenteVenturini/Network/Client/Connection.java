package it.polimi.ingsw.ParenteVenturini.Network.Client;

import it.polimi.ingsw.ParenteVenturini.Network.Client.ClientSideController;
import it.polimi.ingsw.ParenteVenturini.Network.Client.MessageListener;
import it.polimi.ingsw.ParenteVenturini.View.ViewType;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class create a connection between the client and the server
 */
public class Connection {
    private int port = 1337;
    private String ip = "127.0.0.1";
    private ObjectInputStream readStream;
    private ObjectOutputStream writeStream;
    private Thread messageListener;
    private Socket socket;
    private ClientSideController clientSideController;

    public void connect(ViewType viewType) {
        socket = null;
        try {
            socket = new Socket(ip, port);
            writeStream = new ObjectOutputStream(socket.getOutputStream());
            readStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server not ready");
            Platform.exit();
            System.exit(0);
        }

        Scanner stdIn = new Scanner(System.in);

        clientSideController = new ClientSideController(stdIn, readStream, writeStream);
        clientSideController.setConnection(this);

        messageListener = new Thread(new MessageListener(clientSideController, readStream));
        if(viewType.equals(ViewType.GUI))
            messageListener.setDaemon(true);
        messageListener.start();
    }

    public ClientSideController getClientSideController() {
        return clientSideController;
    }

    public void quitConnection() {
        System.out.println("disconnecting");
        try {
            socket.close();
            System.out.println("socket closed ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
