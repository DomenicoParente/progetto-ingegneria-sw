package it.polimi.ingsw.ParenteVenturini.Model.Moves;


import it.polimi.ingsw.ParenteVenturini.Model.Board;
import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.*;
import it.polimi.ingsw.ParenteVenturini.Model.Point;
import it.polimi.ingsw.ParenteVenturini.Model.Worker;

import java.util.List;


public abstract class Move {

    public abstract void walk(Point point, Board board, Worker worker) throws IllegalBuildingException, IllegalMovementException, AlreadyWalkedException, endedMoveException;

    public abstract void build(Point point, Board board, Worker worker) throws IllegalBuildingException, IllegalMovementException, AlreadyBuiltException, OutOfOrderMoveException, endedMoveException;

    public void specialBuild(Point point, Board board, Worker worker) throws IllegalBuildingException, IllegalMovementException, OutOfOrderMoveException, endedMoveException, AlreadyBuiltException {
        build(point, board, worker);
    }

    public abstract List<Point> possibleMovements(Board board, Worker worker);

    public abstract List<Point> possibleBuildings(Board board, Worker worker);

}