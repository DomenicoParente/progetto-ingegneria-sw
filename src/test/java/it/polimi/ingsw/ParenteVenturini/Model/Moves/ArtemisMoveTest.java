package it.polimi.ingsw.ParenteVenturini.Model.Moves;

import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.*;
import it.polimi.ingsw.ParenteVenturini.Model.Match;
import it.polimi.ingsw.ParenteVenturini.Model.Player;
import it.polimi.ingsw.ParenteVenturini.Model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisMoveTest {
    private Match instance;
    private Player player;
    private Move tester;

    @BeforeEach
    void setUp() {
        instance= new Match();
        player= new Player("player",instance);
        Point x= new Point(0,0);
        player.placeWorker(1,x,instance.getBoard());
        tester= new ArtemisMove();
    }

    @Test
    void walk_test1() throws AlreadyWalkedException, IllegalBuildingException, endedMoveException, IllegalMovementException, OutOfOrderMoveException, AlreadyBuiltException {
        Point p0= new Point(0,0);
        Point p1= new Point(1,0);
        Point p2= new Point(2,1);
        Point p3= new Point(3,1);
        tester.walk(p1,instance.getBoard(),player.selectWorker(0));
        assertThrows(IllegalMovementException.class,()->tester.walk(p0,instance.getBoard(),player.selectWorker(0)));
        tester.walk(p2,instance.getBoard(),player.selectWorker(0));
        assertThrows(AlreadyWalkedException.class,()->tester.walk(p3,instance.getBoard(),player.selectWorker(0)));
        tester.build(p1,instance.getBoard(),player.selectWorker(0));
        assertThrows(endedMoveException.class,()->tester.walk(p1,instance.getBoard(),player.selectWorker(0)));
    }

    @Test
    void walk_test2() throws AlreadyWalkedException, IllegalBuildingException, endedMoveException, IllegalMovementException, AlreadyBuiltException, IllegalBlockUpdateException {
        Point p0= new Point(0,1);
        Point p1= new Point(1,0);
        Point p2= new Point(2,1);
        instance.getBoard().setBlockLevel(p0,2);
        instance.getBoard().setBlockLevel(p2,2);
        assertThrows(IllegalMovementException.class,()->tester.walk(p0,instance.getBoard(),player.selectWorker(0)));
        tester.walk(p1,instance.getBoard(),player.selectWorker(0));
        assertThrows(IllegalMovementException.class,()->tester.walk(p2,instance.getBoard(),player.selectWorker(0)));
    }

    @Test
    void build() throws AlreadyWalkedException, IllegalBuildingException, endedMoveException, IllegalMovementException, OutOfOrderMoveException, AlreadyBuiltException {
        Point p1= new Point(1,1);
        Point p2= new Point(2,1);
        assertThrows(OutOfOrderMoveException.class,()->tester.build(p1,instance.getBoard(),player.selectWorker(0)));
        tester.walk(p1,instance.getBoard(),player.selectWorker(0));
        tester.build(p2,instance.getBoard(),player.selectWorker(0));
        assertThrows(endedMoveException.class,()->tester.walk(p2,instance.getBoard(),player.selectWorker(0)));
        assertThrows(endedMoveException.class,()->tester.build(p2,instance.getBoard(),player.selectWorker(0)));
    }

    @Test
    void possibleMovements() throws AlreadyWalkedException, IllegalBuildingException, endedMoveException, IllegalMovementException, AlreadyBuiltException {
        Point p1= new Point(1,1);
        Point p2= new Point(2,1);
        assertTrue(tester.possibleMovements(instance.getBoard(), player.selectWorker(0)).size()==3);
        tester.walk(p1,instance.getBoard(),player.selectWorker(0));
        assertTrue(tester.possibleMovements(instance.getBoard(), player.selectWorker(0)).size()==7);
        tester.walk(p2,instance.getBoard(),player.selectWorker(0));
        assertThrows(AlreadyWalkedException.class,()->tester.possibleMovements(instance.getBoard(),player.selectWorker(0)));
    }

    @Test
    void eliminateFirstPointForSecondWalk() throws AlreadyWalkedException, IllegalBuildingException, endedMoveException, IllegalMovementException, AlreadyBuiltException {
        Point p1= new Point(1,1);
        Point p2= new Point(2,1);
        tester.walk(p1,instance.getBoard(),player.selectWorker(0));
        for(Point p: tester.possibleMovements(instance.getBoard(), player.selectWorker(0)))
            assertTrue(!p.equals(new Point(0,0)));
    }

    @Test
    void possibleBuildings() throws AlreadyWalkedException, IllegalBuildingException, endedMoveException, IllegalMovementException, OutOfOrderMoveException, AlreadyBuiltException {
        Point p1= new Point(1,1);
        Point p2= new Point(2,1);
        assertThrows(OutOfOrderMoveException.class,()->tester.possibleBuildings(instance.getBoard(),player.selectWorker(0)));
        tester.walk(p1,instance.getBoard(),player.selectWorker(0));
        assertNotNull(tester.possibleBuildings(instance.getBoard(), player.selectWorker(0)));
        tester.build(p2,instance.getBoard(),player.selectWorker(0));
        assertThrows(AlreadyBuiltException.class,()->tester.possibleBuildings(instance.getBoard(),player.selectWorker(0)));
    }
}