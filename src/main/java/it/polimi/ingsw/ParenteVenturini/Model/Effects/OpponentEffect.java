package it.polimi.ingsw.ParenteVenturini.Model.Effects;

import it.polimi.ingsw.ParenteVenturini.Model.Board;
import it.polimi.ingsw.ParenteVenturini.Model.Point;
import it.polimi.ingsw.ParenteVenturini.Model.Worker;

import java.util.List;

public interface OpponentEffect {

    List<Point> removeMovementPoints(List<Point> movements, Point actualPoint, Board board);
    List<Point> removeConstructionPoints(List<Point> movements, Point actualPoint, Board board);
    boolean isMovementValid(Point nextPoint, Point actualPoint, Board board);
    boolean isConstructionValid(Point nextPoint, Point actualPoint, Board board);
    boolean isWinEffect();
    boolean isEffectEnabled(Point beforePoint, Point nextPoint, Board board);
    boolean isWinner(Board board, Worker worker);
}
