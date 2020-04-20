package it.polimi.ingsw.ParenteVenturini.Model.Effects;

import it.polimi.ingsw.ParenteVenturini.Model.Board;
import it.polimi.ingsw.ParenteVenturini.Model.Point;
import it.polimi.ingsw.ParenteVenturini.Model.Worker;

import java.util.ArrayList;
import java.util.List;

public class AthenaEffect implements OpponentEffect {

    //todo correct removeMovementPoints

    @Override
    public List<Point> removeMovementPoints(List<Point> movements, Point actualPoint, Board board) {
        List<Point> futureMovements = new ArrayList<>(movements);
        int level = board.blockLevel(actualPoint);
        for(Point p: futureMovements){
            if(board.blockLevel(p)>level)
                futureMovements.remove(p);
        }
        return futureMovements;
    }

    @Override
    public List<Point> removeConstructionPoints(List<Point> movements, Point actualPoint, Board board) {
        return movements;
    }

    @Override
    public boolean isMovementValid(Point nextPoint, Point beforePoint, Board board) {
        return board.blockLevel(beforePoint) >= board.blockLevel(nextPoint);
        //return board.blockLevel(beforePoint) < board.blockLevel(nextPoint);
    }

    @Override
    public boolean isConstructionValid(Point nextPoint, Point actualPoint, Board board) {
        return false; //it was true
    }

    @Override
    public boolean isWinEffect() {
        return false;
    }

    @Override
    public boolean isEffectEnabled(Point beforePoint, Point nextPoint, Board board) {
        if(board.blockLevel(beforePoint) < board.blockLevel(nextPoint))
            return true;
        return false;
    }

    @Override
    public boolean isWinner(Board board, Worker worker) {
        return false;
    }
}
