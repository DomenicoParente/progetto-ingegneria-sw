package it.polimi.ingsw.ParenteVenturini.Network.MessagesToServer;

import it.polimi.ingsw.ParenteVenturini.Model.Point;
import it.polimi.ingsw.ParenteVenturini.Network.Server.ServerMessageHandler;

import java.util.Arrays;
import java.util.List;

public class PlaceWorkerRequest implements MessageToServer {
    private Point point;
    private String nickname;
    private String numWorkers;

    public PlaceWorkerRequest(Point point, String nickname, String numWorkers) {
        this.point = point;
        this.nickname = nickname;
        this.numWorkers = numWorkers;
    }

    @Override
    public MessageType getMessageType() {
        return null;
    }

    public Point getPoint() {
        return point;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public List<String> getValues() {
        return Arrays.asList(numWorkers);
    }

    @Override
    public void accept(ServerMessageHandler msgHandler) {
        msgHandler.visit(this);
    }
}
