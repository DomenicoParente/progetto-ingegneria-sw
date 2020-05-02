package it.polimi.ingsw.ParenteVenturini.View.CLI;

import it.polimi.ingsw.ParenteVenturini.Model.Block;
import it.polimi.ingsw.ParenteVenturini.Model.Point;

import java.util.List;

public interface ViewInterface {
    String login();
    void chooseCards(List<String> cardsName, int numberOfCardsRequired);
    void displayChooseCardMenu();
    void displayBoard(Block[][] blocks, List<Point>workers, List<String>colours, List<String> index);
    void displayMenu();
    void displayMoveMenu();
    void displaySelectWorker();
    void displayMessage(String s);
    void displayChooseStartingPlayerMenu();
    void displayPlaceWorkerMenu();
    void addLightWorker(Point point);
    void displaySelectPoint();
    void displayEndGame();
}