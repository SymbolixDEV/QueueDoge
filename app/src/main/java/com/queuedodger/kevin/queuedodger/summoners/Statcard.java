package com.queuedodger.kevin.queuedodger.summoners;

/**
 * Created by Kevin on 9/29/2015.
 */
public class Statcard {

    private int player;
    private float championWinrate;
    private int position;
    private float positionWinrate;
    private float kda;

    public float getkda(){
        return kda;
    }
    public void setKda(float x){
        kda = x;
    }

    public float getChampionWinrate(){
        return championWinrate;
    }
    public float getPosition(){
        return position;
    }
    public float  getPositionWinrate(){
        return positionWinrate;
    }
    public int getPlayer(){
        return player;
    }

    public void setPlayer(int playerNumber){
        player = playerNumber;
    }
    public void setChampionWinrate(float champWinrate){
        championWinrate = champWinrate;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public void setPositionWinrate(float winrate){
        positionWinrate = winrate;
    }
}
