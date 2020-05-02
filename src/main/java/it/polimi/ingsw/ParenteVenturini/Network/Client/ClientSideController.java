package it.polimi.ingsw.ParenteVenturini.Network.Client;

import it.polimi.ingsw.ParenteVenturini.Model.Block;
import it.polimi.ingsw.ParenteVenturini.Network.MessagesToClient.*;
import it.polimi.ingsw.ParenteVenturini.Network.MessagesToServer.*;
import it.polimi.ingsw.ParenteVenturini.View.CLI.ViewInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientSideController implements ClientMessageHandler {

    private ObjectInputStream readStream;
    private ObjectOutputStream writeStream;
    private Scanner stdIn;
    private ViewInterface client;

    public ClientSideController(Scanner stdIn, ObjectInputStream readStream, ObjectOutputStream writeStream) {
        this.writeStream = writeStream;
        this.readStream = readStream;
        this.stdIn = stdIn;
    }

    public void setView(ViewInterface client){
        this.client = client;
    }

    public void handleMessage(MessageToClient msg){
        msg.accept(this);
    }

    public void sendMessage(MessageToServer msg){
        try {
            writeStream.reset();
            writeStream.writeObject(msg);
            writeStream.flush();
        } catch (IOException e) {
            System.out.println("Errore invio messaggio - connessione chiusa");
            closeConnection();
            //e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            writeStream.close();
            readStream.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    @Override
    public void visit(ErrorLoginNotification msg) {
        client.displayMessage(msg.getNickname()+": "+msg.getValues().get(0));
        client.login();
    }

    @Override
    public void visit(SetUpNotification msg) {
        client.displayMessage("Fase di setUp iniziata");
    }

    @Override
    public void visit(SelectCardNotification msg) {
        client.chooseCards(msg.getValues(), msg.numberOfCardsRequired());
    }

    @Override
    public void visit(SimplyNotification msg) {
        client.displayMessage(msg.getValues().get(0));
    }

    @Override
    public void visit(StartGameNotification msg) {
        client.displayMenu();
    }

    @Override
    public void visit(ChooseCardNotification msg) {
        client.displayChooseCardMenu();
    }

    @Override
    public void visit(SetPlayerCardResponse msg) {
        client.displayMessage(msg.getValues().get(0));
        if(! msg.isSet())
            client.displayChooseCardMenu();
    }

    @Override
    public void visit(AvailableCardResponse msg) {
        for(String s: msg.getValues())
            client.displayMessage(s);
        client.displayChooseCardMenu();
    }

    @Override
    public void visit(ChooseStartingPlayerNotification msg) {
        client.displayChooseStartingPlayerMenu();
    }

    @Override
    public void visit(AvailablePlayersResponse msg) {
        for(String s: msg.getValues())
            client.displayMessage(s);
        client.displayChooseStartingPlayerMenu();
    }

    @Override
    public void visit(SetStartingPlayerResponse msg) {
        client.displayMessage(msg.getValues().get(0));
        if(! msg.isSet())
            client.displayChooseStartingPlayerMenu();
    }

    @Override
    public void visit(PlaceWorkersNotification msg) {
        client.displayPlaceWorkerMenu();
    }

    @Override
    public void visit(AvailablePlaceWorkerPointResponse msg) {
        client.displayMessage(msg.getPoints().toString());
        client.displayPlaceWorkerMenu();
    }

    @Override
    public void visit(BoardUpdateNotification msg) {
        Block [][] b = msg.getBlocks();
        /*
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                System.out.println("level: "+b[i][j].getLevel());
            }
        }

         */
        client.displayBoard(msg.getBlocks(),msg.getWorkerpositions(),msg.getColours(),msg.getIndex());
    }

    @Override
    public void visit(SelectWorkerResponse msg) {
        client.displayMessage(msg.getMessage());
        if(!msg.isSet())
            client.displaySelectWorker();
        else
            client.displayMoveMenu();
    }

    @Override
    public void visit(EndMoveResponse msg) {
        client.displayMessage(msg.getMessage());
        if(!msg.isDone())
            client.displayMoveMenu();
        else {
            client.displayMessage("Il tuo turno è finito. Attendi...");
        }
    }

    @Override
    public void visit(YourTurnNotification msg) {
        client.displayMessage("E' il tuo turno");
        client.displaySelectWorker();
    }

    @Override
    public void visit(WinNotification msg) {
        client.displayMessage("Il vincitore è: "+msg.getMessage());
    }

    @Override
    public void visit(ActionResponse msg) {
        client.displayMessage(msg.getPoints().toString());
        client.displaySelectPoint();
    }

    @Override
    public void visit(ActionPointResponse msg) {
        client.displayMessage(msg.getMessage());
        client.displayMoveMenu();
    }

    @Override
    public void visit(ActionNotification msg) {
        if(msg.getMessage().equals("Non è il tuo turno")){
            client.displayMessage(msg.getMessage());
        }
        else if(msg.getMessage().equals("Nessuna azione possibile. Seleziona un altro worker") ){
            client.displayMessage(msg.getMessage());
            client.displaySelectWorker();
        }
        else{
            client.displayMessage(msg.getMessage());
            client.displayMoveMenu();
        }
    }

    @Override
    public void visit(GameOverNotification msg) {
        client.displayMessage("Hai Perso ");
        client.displayEndGame();
    }

    @Override
    public void visit(PlaceWorkerResponse msg) {
        System.out.println(msg.getSettedPoint().toString());
        if(msg.isSet()) {
            //client.addLightWorker(new LightWorker(msg.getSettedPoint() ));
            client.addLightWorker(msg.getSettedPoint());
            System.out.println("fatto");
        }

        client.displayMessage(msg.getMessage());
        if(!msg.isHasFinished()) {
            client.displayPlaceWorkerMenu();
        }

    }
}
