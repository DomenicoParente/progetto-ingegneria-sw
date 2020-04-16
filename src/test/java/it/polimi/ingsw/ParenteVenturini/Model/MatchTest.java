package it.polimi.ingsw.ParenteVenturini.Model;

import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class MatchTest {
    private Match instance;

    @BeforeEach
    void setUp() {
        instance= new Match();
    }

    @Test
    void addPlayer() throws NoMorePlayersException, InvalidTypeOfMatch, AlreadyPresentPlayerException {
        instance.addPlayer("player1");
        assertEquals(1, instance.getNumPlayers());
        assertThrows(AlreadyPresentPlayerException.class,()->instance.addPlayer("player1"));
        instance.addPlayer("player2");
        assertEquals(2, instance.getNumPlayers());
        assertThrows(NoMorePlayersException.class,()->instance.addPlayer("player3"));
        instance.setTypeOfMatch(3);
        instance.addPlayer("player3");
        assertEquals(3, instance.getNumPlayers());
        assertThrows(NoMorePlayersException.class,()->instance.addPlayer("player4"));
    }

    @Test
    void gameOver() {

    }

    @Test
    void getOpponentEffectContainer() {
        assertNotNull(instance.getOpponentEffectContainer());
    }

    @Test
    void getTypeOfMatch() throws InvalidTypeOfMatch {
        assertEquals(2,instance.getTypeOfMatch());
        instance.setTypeOfMatch(3);
        assertEquals(3,instance.getTypeOfMatch());
        instance.setTypeOfMatch(2);
        assertEquals(2,instance.getTypeOfMatch());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,-1,4,120,-2,-3})
    void setTypeOfMatch(int value) throws InvalidTypeOfMatch {
        instance.setTypeOfMatch(3);
        assertEquals(3,instance.getTypeOfMatch());
        assertThrows(InvalidTypeOfMatch.class,()->instance.setTypeOfMatch(value));
    }

    @Test
    void setChallenger() throws NoMorePlayersException, NoPlayerException, AlreadyPresentPlayerException {
        assertNull( instance.getChallenger() );
        assertThrows(NoPlayerException.class,()->instance.setChallenger());
        instance.addPlayer("player1");
        instance.setChallenger();
        assertNotNull( instance.getChallenger());
        instance.addPlayer("player2");
        instance.setChallenger();
        assertNotNull( instance.getChallenger());
        assertTrue(instance.getPlayers().contains(instance.getChallenger()));
    }

    @Test
    void selectPlayer() throws NoMorePlayersException, AlreadyPresentPlayerException {
        assertNull(instance.selectPlayer("Player"));
        instance.addPlayer("player1");
        assertNull(instance.selectPlayer("Player"));
        assertNotNull(instance.selectPlayer("player1"));
    }

    @Test
    void selectCardFromDeck() throws InvalidCardException, NoMoreCardsException {
        assertThrows(InvalidCardException.class,()->instance.selectCardFromDeck("card"));
        instance.selectCardFromDeck("Apollo");
        assertEquals(1, instance.getChosenCards().size());
        assertThrows(InvalidCardException.class,()->instance.selectCardFromDeck("Apollo"));
        instance.selectCardFromDeck("Minotaur");
        assertThrows(NoMoreCardsException.class,()->instance.selectCardFromDeck("Pan"));

    }

    @Test
    void selectStarter() throws NoMorePlayersException, AlreadyPresentPlayerException, AlreadyChosenStarterException, InvalidNamePlayerException, NoPlayerException {
        assertThrows(NoPlayerException.class,()->instance.selectStarter("player"));
        instance.addPlayer("player1");
        instance.addPlayer("player2");
        assertThrows(InvalidNamePlayerException.class,()->instance.selectStarter("player"));
        instance.selectStarter("player1");
        assertTrue(instance.getStarter() != null);
        assertThrows(AlreadyChosenStarterException.class,()->instance.selectStarter("player2"));
    }

    @Test
    void orderPlayers() throws NoMorePlayersException, AlreadyPresentPlayerException, AlreadyChosenStarterException, InvalidNamePlayerException, NoPlayerException {
        assertThrows(NoPlayerException.class,()->instance.orderPlayers());
        instance.addPlayer("player1");
        assertThrows(NoStarterException.class,()->instance.orderPlayers());
        instance.addPlayer("player2");
        instance.selectStarter("player2");
        assertEquals(instance.selectPlayer("player2"),instance.getPlayers().get(0));
    }

    @Test
    void getBoard() {
        assertTrue(instance.getBoard() != null);
    }

    @Test
    void getChallenger() throws NoMorePlayersException, NoPlayerException, AlreadyPresentPlayerException {
        assertNull( instance.getChallenger() );
        instance.addPlayer("player1");
        instance.setChallenger();
        assertTrue(instance.getChallenger() != null);
    }

    @Test
    void getPlayers() throws NoMorePlayersException, AlreadyPresentPlayerException, NoPlayerException {
        assertThrows(NoPlayerException.class,()->instance.getPlayers());
        instance.addPlayer("player1");
        assertEquals(1, instance.getPlayers().size());
        instance.addPlayer("player2");
        assertEquals(2, instance.getPlayers().size());

    }

    @Test
    void getNumPlayers() throws NoMorePlayersException, AlreadyPresentPlayerException {
        assertEquals(0,instance.getNumPlayers());
        instance.addPlayer("player1");
        assertEquals(1,instance.getNumPlayers());
        instance.addPlayer("player2");
        assertEquals(2,instance.getNumPlayers());
    }
}