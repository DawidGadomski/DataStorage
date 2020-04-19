package pl.pwr.edu.s241223.datastorage;

import java.io.Serializable;
import java.util.List;

public class Board implements Serializable {
    private List<Player> players;
    private boolean isOver;
    private int turns;

    public Board(List<Player> players, int turns) {
        this.isOver = false;
        this.players = players;
        this.turns = turns;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
}
