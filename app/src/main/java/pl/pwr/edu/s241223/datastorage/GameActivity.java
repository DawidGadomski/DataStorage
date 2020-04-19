package pl.pwr.edu.s241223.datastorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.WeakHashMap;

public class GameActivity extends AppCompatActivity {

    private Board board;

    private BoardComp boardComp;
    private ArrayList<Segment> segments;
    private int startAngle, sweepAngle;
    private int secondsToStart;
    private String[] countDown = {"5...", "4...", "3...", "2...", "1...", "GO!!!"};
    private TextView tvTimer;
    private Button bNextRound;
    private Button bEndGame;
    private int points;
    private int turns;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvTimer = findViewById(R.id.tvTimer);
        boardComp = findViewById(R.id.boardComp);
        bNextRound = findViewById(R.id.bNextRound);
        bEndGame = findViewById(R.id.bEndGame);

        board = (Board) getIntent().getSerializableExtra("Board");
        assert board != null;
        turns = board.getTurns();
        startAngle = 0;
        sweepAngle = 360/board.getPlayers().size();
        segments = new ArrayList<Segment>();

        for(Player player : board.getPlayers()){
            segments.add(new Segment(startAngle, sweepAngle, player.getColor()));
            startAngle+=sweepAngle;
        }


        boardComp.setSegments(segments);

        boardComp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean value = GameActivity.super.onTouchEvent(event);

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    for(Segment segment : segments){
                        if(segment.click(event.getX(), event.getY())){
                            segment.setClickable(false);
                            for (Player player : board.getPlayers()){
                                if (player.getColor() == segment.getColor()){
                                    player.score(points);
                                    points--;
                                }
                            }

                            segment.blockPlayer();
                            break;
                        }

                    }
                    boardComp.invalidate();
                    return true;
                }
                return value;
            }
        });

    }

    public void startAsyncTask(View v){
        GameAsyncTask task = new GameAsyncTask(this);
        task.execute();
        saveData();
    }

    public void endGame(View v){
        Intent summaryIntent = new Intent(getApplicationContext(), SummaryActivity.class);
        board.setOver(true);
        summaryIntent.putExtra("Board", board);
        startActivity(summaryIntent);
    }

    private static class GameAsyncTask extends AsyncTask<Integer, Integer, String>{
        private WeakReference<GameActivity> activityWeakReference;

        GameAsyncTask(GameActivity gameActivity){
            activityWeakReference = new WeakReference<GameActivity>(gameActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            GameActivity gameActivity = activityWeakReference.get();
            if(gameActivity == null || gameActivity.isFinishing()){
                return;
            }
            gameActivity.turns--;
            if(gameActivity.turns == 0){
                gameActivity.bNextRound.setVisibility(View.INVISIBLE);
                gameActivity.bEndGame.setVisibility(View.VISIBLE);
            }
            gameActivity.secondsToStart = new Random().nextInt((5-1) + 1) + 1;
            gameActivity.points = 5;
            for (Segment segment : gameActivity.segments) {
                segment.unblockPlayer();
            }
            gameActivity.boardComp.invalidate();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            GameActivity gameActivity = activityWeakReference.get();
            if(gameActivity == null || gameActivity.isFinishing()){
                return;
            }

            gameActivity.tvTimer.setText(gameActivity.countDown[gameActivity.countDown.length - 1]);
            for (Segment segment : gameActivity.segments) {
                segment.setClickable(true);
            }


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            GameActivity gameActivity = activityWeakReference.get();
            if(gameActivity == null || gameActivity.isFinishing()){
                return;
            }

            gameActivity.tvTimer.setText(gameActivity.countDown[values[0]]);

        }

        @Override
        protected String doInBackground(Integer... integers) {
            GameActivity gameActivity = activityWeakReference.get();
            if(gameActivity == null || gameActivity.isFinishing()){
                return null;
            }
            for (int i = 0; i < gameActivity.secondsToStart; i++) {
                publishProgress(i);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "done";
        }
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        System.out.println(turns);
        board.setTurns(turns);
        String json = gson.toJson(board);
        editor.putString("board", json);

        editor.apply();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("board", board);
        outState.putInt("Turns", turns);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedInstanceState.getSerializable("board");
        savedInstanceState.getInt("Turns");
    }
}
