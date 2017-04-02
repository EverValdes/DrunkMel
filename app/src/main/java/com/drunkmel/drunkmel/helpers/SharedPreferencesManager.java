package com.drunkmel.drunkmel.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreferencesManager {
    private static SharedPreferencesManager instance;
    //Shared preference file names
    private static final String PLAYERS_LIST = "com.drunkmel.drunkmel.PLAYERS_LIST";
    private static final String PLAYERS_SCORE = "com.drunkmel.drunkmel.PLAYERS_SCORE";
    private static final String PLAYER_TURN = "com.drunkmel.drunkmel.PLAYER_TURN";
    //Turns
    public static final String NEXT_PLAYER = "NEXT_PLAYER";
    public static final String LAST_TURN = "LAST_TURN";

    private Context context;

    //Private constructor so we ensure we user the application context instead of and activity class context.
    private SharedPreferencesManager(Context context){
        this.context = context.getApplicationContext();
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    private SharedPreferences createSharedPreference(String SP_fileName) {
        return context.getSharedPreferences(SP_fileName, Context.MODE_PRIVATE);
    }

    private void clear(String SP_fileName){
        context.getSharedPreferences(SP_fileName, Context.MODE_PRIVATE).edit().clear().apply();
    }

    private void setValue(String SP_fileName, String key, String value) {
        context.getSharedPreferences(SP_fileName, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    private void setValue(String SP_fileName, String key, int value) {
        context.getSharedPreferences(SP_fileName, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    private String getValue(String SP_fileName, String key, String defValue) {
        return context.getSharedPreferences(SP_fileName, Context.MODE_PRIVATE).getString(key, defValue);
    }

    private int getValue(String SP_fileName, String key, int defValue) {
        return context.getSharedPreferences(SP_fileName, Context.MODE_PRIVATE).getInt(key, defValue);
    }

    public void createPlayerSharedPreferences() {
        createSharedPreference(PLAYERS_LIST);
        createSharedPreference(PLAYERS_SCORE);
        createSharedPreference(PLAYER_TURN);

        clear(PLAYERS_LIST);
        clear(PLAYERS_SCORE);
        clear(PLAYER_TURN);
    }

    public void setDefaultScores(){
        Map<String, ?> allEntries = getAllPlayers();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            setPlayerScore(entry.getValue().toString(), 0);
        }
    }

    public void setPlayerInList(Integer turn, String player) {
        setValue(SharedPreferencesManager.PLAYERS_LIST, turn.toString(), player);
    }

    public void setPlayerScore(String player, int score) {
        setValue(SharedPreferencesManager.PLAYERS_SCORE, player, score);
    }

    public void setPlayerTurn(String positionTurn, String playerName) {
        setValue(SharedPreferencesManager.PLAYER_TURN, positionTurn, playerName);
    }

    private String getPlayerInList(Integer turn) {
        return getValue(SharedPreferencesManager.PLAYERS_LIST, Integer.toString(turn), "");
    }

    public String getPlayerInList(String turn) {
        return getValue(SharedPreferencesManager.PLAYERS_LIST, turn, "");
    }

    public int getPlayerScore(String player) {
        return getValue(SharedPreferencesManager.PLAYERS_SCORE, player, 0);
    }

    private Integer getPlayerTurn(String playerName) {
        Map<String, ?> allEntries = getAllPlayers();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().toString().equalsIgnoreCase(playerName)) {
                return Integer.parseInt(entry.getKey());
            }
        }
        return 0;
    }

    public Map<String, ?> getAllPlayers() {
        return context.getSharedPreferences(PLAYERS_LIST, Context.MODE_PRIVATE).getAll();
    }

    public String getNextPlayer() {
        return getValue(PLAYER_TURN, NEXT_PLAYER, null);
    }

    private String getLastPlayer() {
        return getValue(PLAYER_TURN, LAST_TURN, null);
    }

    public boolean isLastTurn() {
        return (getNextPlayer().equals(getLastPlayer()));
    }

    public void increaseTurn() {
        if(isLastTurn()){
            setPlayerTurn(NEXT_PLAYER, getFirstPlayer());
            return;
        }
        int nextTurn = getPlayerTurn(getNextPlayer())+1;
        String nextOne = getPlayerInList(nextTurn);
        setPlayerTurn(NEXT_PLAYER, nextOne);
    }

    private String getFirstPlayer(){
        return getPlayerInList(0);
    }

    public void updateScore() {
        String nextPlayer = getNextPlayer();
        int actualScore = getPlayerScore(nextPlayer);
        setPlayerScore(nextPlayer, actualScore+1);
    }
}
