package it.polimi.ingsw.ParenteVenturini.Model.Moves;

import it.polimi.ingsw.ParenteVenturini.Model.Actions.Action;
import it.polimi.ingsw.ParenteVenturini.Model.Actions.BasicConstruction;
import it.polimi.ingsw.ParenteVenturini.Model.Actions.BasicMovement;
import it.polimi.ingsw.ParenteVenturini.Model.Board;
import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.*;
import it.polimi.ingsw.ParenteVenturini.Model.Point;
import it.polimi.ingsw.ParenteVenturini.Model.Worker;

import java.util.List;

/**
 * Handle the moves of this Card's owner
 */
public class AthenaMove extends Move {

    /**
     * init the move
     */
    public AthenaMove() {
        this.hasWalked = false;
        this.hasBuilt = false;
        this.hasEnded = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void walk(Point point, Board board, Worker worker) throws IllegalBuildingException, IllegalMovementException, AlreadyWalkedException, endedMoveException, AlreadyBuiltException {
        if(!hasEnded) {
            if( !hasBuilt) {
                if (!hasWalked) {
                    Action action = new BasicMovement();
                    action.doAction(point, board, worker);
                    hasWalked = true;
                } else throw new AlreadyWalkedException();
            }else throw new AlreadyBuiltException();
        }else throw new endedMoveException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build(Point point, Board board, Worker worker) throws IllegalBuildingException, IllegalMovementException, OutOfOrderMoveException, endedMoveException, AlreadyBuiltException {
        if(!hasEnded) {
            if( !hasBuilt) {
                if (hasWalked) {
                    Action action = new BasicConstruction();
                    action.doAction(point, board, worker);
                    hasBuilt = true;
                    hasEnded = true;
                } else {
                    throw new OutOfOrderMoveException();
                }
            }else throw new AlreadyBuiltException();
        }else throw new endedMoveException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Point> possibleMovements(Board board, Worker worker) throws AlreadyWalkedException {
        Action action = new BasicMovement();
        if(!hasEnded && !hasBuilt && !hasWalked) {
            return action.getPossibleActions(board, worker);
        }
        else throw new AlreadyWalkedException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Point> possibleBuildings(Board board, Worker worker) throws OutOfOrderMoveException, AlreadyBuiltException {
        Action action = new BasicConstruction();
        if(!hasEnded && !hasBuilt) {
            if(hasWalked) {
                return action.getPossibleActions(board, worker);
            }
            else throw new OutOfOrderMoveException();
        }
        else throw new AlreadyBuiltException();
    }

}
