package it.polimi.ingsw.ParenteVenturini.Model.Actions;

import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.IllegalBlockUpdateException;
import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.IllegalBuildingException;
import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.IllegalMovementException;
import it.polimi.ingsw.ParenteVenturini.Model.Match;
import it.polimi.ingsw.ParenteVenturini.Model.Player;
import it.polimi.ingsw.ParenteVenturini.Model.Point;
import it.polimi.ingsw.ParenteVenturini.Model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicMovementTest {
    private Match instance;
    private Player player;
    private Action tester;

    @BeforeEach
    void setUp() {
        instance= new Match();
        player= new Player("player",instance);
        Point x= new Point(0,0);
        player.placeWorker(1,x,instance.getBoard());
        tester= new BasicMovement();
    }
    @Test
    void doAction() throws IllegalBuildingException, IllegalMovementException, IllegalBlockUpdateException {
        Point p1= new Point(1,1);
        Point p2= new Point(-1,1);
        Point p3= new Point(0,0);
        Point p4= new Point(3,0);
        //check if worker moves on his own position
        assertThrows(IllegalMovementException.class,()->tester.doAction(p3,instance.getBoard(),player.selectWorker(0)));

        //check if worker moves in a valid position
        tester.doAction(p1,instance.getBoard(),player.selectWorker(0));
        assertEquals(player.selectWorker(0).getPosition(),p1);

        //check if worker moves on invalid positions
        assertThrows(IllegalMovementException.class,()->tester.doAction(p2,instance.getBoard(),player.selectWorker(0)));
        assertThrows(IllegalMovementException.class,()->tester.doAction(p4,instance.getBoard(),player.selectWorker(0)));

        //check if worker moves on a position occupied by another worker
        Point p5= new Point(1,0);
        player.placeWorker(1,p5,instance.getBoard());
        assertThrows(IllegalMovementException.class,()->tester.doAction(p5,instance.getBoard(),player.selectWorker(0)));

        //check if worker moves on a position with dome
        Point p6= new Point(0,1);
        instance.getBoard().setDome(p6, true);
        assertThrows(IllegalMovementException.class,()->tester.doAction(p6,instance.getBoard(),player.selectWorker(0)));

        //check if worker moves on a too high position
        instance.getBoard().setBlockLevel(p1,2);
        assertThrows(IllegalMovementException.class,()->tester.doAction(p1,instance.getBoard(),player.selectWorker(0)));
    }

    @Test
    void isValid() {
        Point p1= new Point(1,1);
        Point p2= new Point(-1,1);
        Point p3= new Point(0,0);
        Point p4= new Point(1,0);
        instance.getBoard().setDome(p4,true);
        assertTrue(tester.isValid(p1,instance.getBoard(),player.selectWorker(0)));
        assertFalse(tester.isValid(p2,instance.getBoard(),player.selectWorker(0)));
        assertFalse(tester.isValid(p3,instance.getBoard(),player.selectWorker(0)));
        assertFalse(tester.isValid(p4,instance.getBoard(),player.selectWorker(0)));
    }

    @Test
    void getPossibleActions() throws IllegalBuildingException, IllegalMovementException {
        List<Point> positions=tester.getPossibleActions(instance.getBoard(),player.selectWorker(0));
        assertEquals(3, positions.size());
        assertTrue(positions.get(0).getX()==0 &&positions.get(0).getY()==1);
        assertTrue(positions.get(1).getX()==1 &&positions.get(1).getY()==0);
        assertTrue(positions.get(2).getX()==1 &&positions.get(2).getY()==1);

        Point p1= new Point(1,1);
        tester.doAction(p1,instance.getBoard(),player.selectWorker(0));
        positions=tester.getPossibleActions(instance.getBoard(),player.selectWorker(0));
        assertEquals(8, positions.size());

        Point p2= new Point(1,0);
        tester.doAction(p2,instance.getBoard(),player.selectWorker(0));
        positions=tester.getPossibleActions(instance.getBoard(),player.selectWorker(0));
        assertEquals(5, positions.size());
    }
}