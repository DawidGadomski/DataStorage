package pl.pwr.edu.s241223.datastorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    private Board board;
    private ListView listView;
    private Button bPlay;
    private Button bEndGame;
    private ArrayAdapter<Player> adapter;
    private List<String> playersNames;
    private List<Integer> playersColor;
    private List<Integer> playersPoints;
    private Player winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        GameDBHelper dbHelper = new GameDBHelper(this);
        database = dbHelper.getWritableDatabase();

        playersNames = new ArrayList<>();
        playersColor = new ArrayList<>();
        playersPoints = new ArrayList<>();

        listView = findViewById(R.id.listView);
        bPlay = findViewById(R.id.bPlay);
        bEndGame = findViewById(R.id.bPlayAgain);

        board = (Board) getIntent().getSerializableExtra("Board");
        assert board != null;
        winner = board.getPlayers().get(0);
        for(Player player : board.getPlayers()){
            playersNames.add(player.getName());
            playersColor.add(player.getColor());
            playersPoints.add(player.getPoints());
            if(winner.getPoints() < player.getPoints()){
                winner = player;
            }

        }
        if(board.isOver()){
            bPlay.setVisibility(View.INVISIBLE);
        }

        final MyAdapter adapter = new MyAdapter(this, playersNames, playersColor, playersPoints);
        listView.setAdapter(adapter);

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("Board", board);
                startActivity(gameIntent);

            }
        });
        bEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newGameIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newGameIntent);
                ContentValues cv = new ContentValues();
                cv.put(GameHistory.GameHistoryEntry.COLUMN_NAME, playersNames.toString());
                cv.put(GameHistory.GameHistoryEntry.COLUMN_WON, winner.getName());


                database.insert(GameHistory.GameHistoryEntry.TABLE_NAME, null, cv);

                finish();
            }
        });


    }
    class MyAdapter extends ArrayAdapter<String>{
        private Context context;
        private List<String> playersNames;
        private List<Integer> playersColor;
        private List<Integer> playersPoints;
        private TextView playerName;
        private TextView playerPoints;

        MyAdapter (Context c, List<String> playersNames, List<Integer> playersColor, List<Integer> playersPoints) {
            super(c, R.layout.player_row, R.id.tvPlayerName, playersNames);
            this.context = c;

        this.playersNames = playersNames;
        this.playersColor = playersColor;
        this.playersPoints = playersPoints;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.player_row, parent, false);
            playerName= row.findViewById(R.id.tvPlayerName);
            playerPoints = row.findViewById(R.id.tvPlayerPoints);

            // now set our resources on views
            playerName.setText(playersNames.get(position));
            playerName.setTextColor(playersColor.get(position));
            if(board.isOver()){
                playerPoints.setVisibility(View.VISIBLE);
                playerPoints.setTextColor(playersColor.get(position));
                playerPoints.setText(String.valueOf(playersPoints.get(position)));
            }
            return row;
        }
    }
}
