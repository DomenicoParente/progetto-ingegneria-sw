package it.polimi.ingsw.ParenteVenturini.Network.Server;

import it.polimi.ingsw.ParenteVenturini.Model.Player;
import it.polimi.ingsw.ParenteVenturini.Network.Exceptions.IllegalCardException;
import it.polimi.ingsw.ParenteVenturini.Network.Exceptions.InvalidNicknameException;
import it.polimi.ingsw.ParenteVenturini.Network.MessagesToClient.ErrorLoginNotification;
import it.polimi.ingsw.ParenteVenturini.Network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.ParenteVenturini.Network.MessagesToClient.SimplyNotification;
import it.polimi.ingsw.ParenteVenturini.Network.MessagesToServer.*;

public class ClientController implements ServerMessageHandler {

    private ClientThreadFromServer clientThread;
    private GameController gameController;
    private Player player;

    public ClientController(ClientThreadFromServer clientThread) {
        this.clientThread = clientThread;
        gameController = null;
    }

    public void sendMessage(MessageToClient msg){
        clientThread.sendMessage(msg);
    }


    private void insertPlayerInGame(String nickname, String numOfPlayers){
        GameDispatcher gameDispatcher = GameDispatcher.getInstance();
        if(Integer.parseInt(numOfPlayers) != 2 && Integer.parseInt(numOfPlayers) != 3) {
            sendMessage(new ErrorLoginNotification(nickname, "Sono possibili partite solo da 2 o 3 giocatori"));
            return;
        }
        try {
            gameController = gameDispatcher.getGameController(nickname, Integer.parseInt(numOfPlayers));
        } catch (InvalidNicknameException e) {
            sendMessage(new ErrorLoginNotification(nickname, "Nickname non disponibile"));
            return;
        }
        if(gameController.getNumOfPlayers() != 0)
            sendMessage(new SimplyNotification("Partita già inizializzata, sei stato aggiunto a quella"));
        player = gameController.addPlayer(this, nickname);
        gameController.startSetup();
        System.out.println("giocatore aggiunto: "+player.getNickname());
    }


    @Override
    public void visit(AccessGameMessageRequest msg) {
        System.out.println("Messaggio arrivato");
        insertPlayerInGame(msg.getNickname(), msg.getValues().get(0));
    }

    @Override
    public void visit(StoreSelectedCardsRequest msg) {
        try {
            gameController.addCardsToMatch(msg.getNickname(), msg.getValues());
        } catch (IllegalCardException e) {
            sendMessage(new SimplyNotification("Questo non dovrebbe succedere..."));
        }
    }

    @Override
    public void visit(AviableCardRequest msg) {
        gameController.sendPossibleCards(this);
    }

    @Override
    public void visit(SetPlayerCardRequest msg) {
        String card = msg.getValues().get(0);
        gameController.setPlayerCard(player, card);
    }

    @Override
    public void visit(AviablePlayerRequest msg) {
        gameController.sendPossiblePlayers(this);
    }

    @Override
    public void visit(SetStartingPlayerRequest msg) {
        gameController.setStartingPlayer(this.player.getNickname(), msg.getValues().get(0));
    }

    @Override
    public void visit(PlaceWorkerRequest msg) {

        //TO DO
        //gameController.placeWorker(msg.getNickname(), msg.getPoint(), msg.getValues().get(0))
    }


    public Player getPlayer() {
        return player;
    }
}