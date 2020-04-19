package pl.pwr.edu.s241223.datastorage;

import android.provider.BaseColumns;

public class GameHistory {

    private GameHistory(){}

    public static final class GameHistoryEntry implements BaseColumns{
        public static final String TABLE_NAME = "gameHistory";
        public static final String COLUMN_NAME = "players";
        public static final String COLUMN_WON = "winner";
        public static final String COLUMN_TURNS = "turns";
        public static final String COLUMN_TIMESTAMP = "timeStamp";

    }
}
