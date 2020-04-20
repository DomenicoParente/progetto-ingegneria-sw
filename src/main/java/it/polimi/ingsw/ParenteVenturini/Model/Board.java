package it.polimi.ingsw.ParenteVenturini.Model;

import it.polimi.ingsw.ParenteVenturini.Model.Exceptions.IllegalBlockUpdateException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private Block[][] board;
    private List<Worker> workers;

    public Board() {
        board = new Block[5][5];
        workers = new ArrayList<Worker>();

        for(int i = 0; i<5; i++){
            for (int j = 0; j<5; j++){
                board[i][j] = new Block();
            }
        }
    }

    public void setWorker(Worker w) {
        workers.add(w);
    }

    public boolean isOccupied(Point point) {
        for (Worker w : workers) {
            if (w.getPosition().equals(point))
                return true;
        }
        return false;
    }

    public boolean isOccupied(int x, int y) {
        Point pointTemp = new Point(x, y);
        for (Worker w : workers) {
            if (w.getPosition().equals(pointTemp))
                return true;
            }
        return false;
    }

    public boolean isThereDome(Point point) {
        return board[point.getX()][point.getY()].isDome();
    }

    public boolean isThereDome(int x, int y) {
        return board[x][y].isDome();
    }

    public int blockLevel(Point point) {
        return board[point.getX()][point.getY()].getLevel();
    }

    public int blockLevel(int x, int y) {
            return board[x][y].getLevel();
    }

    public void setBlockLevel(Point point, int level) throws IllegalBlockUpdateException {
            board[point.getX()][point.getY()].updateLevel(level);
    }
    public void setDome(Point point,boolean x) {
            board[point.getX()][point.getY()].setDome(x);
    }

    public Worker findByPosition(Point point) {
        for (Worker w : workers) {
            if (w.getPosition().equals(point))
                return w;
            }
            return null;
    }

    public Worker findByPosition(int x, int y) {
        Point point= new Point(x,y);
        for (Worker w : workers) {
            if (w.getPosition().equals(point))
                return w;
        }
        return null;
    }

    public boolean isValidPoint(Point point){
        return point.getX() >= 0 && point.getX() <= 4 && point.getY() >= 0 && point.getY() <= 4;
    }

    public boolean isValidPoint(int x, int y){
        return x >= 0 && x <= 4 && y >= 0 && y <= 4;
    }

    public boolean isPerimeterPoint(Point point){
        return point.getX() == 0 || point.getX() == 4 || point.getY() == 0 || point.getY() == 4;
    }

    public Block getBlock(int x, int y){
        return board[x][y];
    }

    public List<Worker> getWorkers() {
        return workers;
    }
}
