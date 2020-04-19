package pl.pwr.edu.s241223.datastorage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Player> players;
    private String playerName;
    private Board board;
    private Button bGame;
    private Button bAddPlayer;
    private Button bHistory;
    private EditText numberOfTurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        players = new ArrayList<Player>();

        bGame = findViewById(R.id.bSummary);
        bAddPlayer = findViewById(R.id.bAddPlayer);
        bHistory = findViewById(R.id.bHistory);

        numberOfTurns = findViewById(R.id.etTurns);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        loadData();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Load Game?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


        bGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberOfTurns.getText().toString().equals("")){
                    numberOfTurns.setText(String.valueOf(1));
                }
                board = new Board(players, Integer.parseInt(numberOfTurns.getText().toString()));

                Intent summaryIntent = new Intent(getApplicationContext(), SummaryActivity.class);
                summaryIntent.putExtra("Board", board);
                startActivity(summaryIntent);
            }
        });

        bAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputIntent = new Intent(getApplicationContext(), InputPlayersActivity.class);
                inputIntent.putExtra("Player", ("Player " + players.size()));
                startActivityForResult(inputIntent, 1);
            }
        });

        bHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(getApplicationContext(), GameHistoryActivity.class);
                startActivityForResult(historyIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                playerName = data.getStringExtra("NameOfPlayer");
                players.add(new Player(playerName, players.size()));
            }
        }
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("board", null);

        Type type = new TypeToken<Board>() {}.getType();
        this.board = gson.fromJson(json, type);
        Intent summaryIntent = new Intent(getApplicationContext(), SummaryActivity.class);
        summaryIntent.putExtra("Board", board);
        startActivity(summaryIntent);
    }
}
