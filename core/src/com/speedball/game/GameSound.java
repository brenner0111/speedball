package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound{
    private Sound losingSound;
    private Sound gameStarted;
    private Sound paintballSound;
    private Sound winningSound;
    private ArrayList<Sound> sounds;
    
    public GameSound() {
        sounds = new ArrayList<Sound>();
        losingSound = Gdx.audio.newSound(Gdx.files.internal("sound/losingSound.wav"));
        gameStarted = Gdx.audio.newSound(Gdx.files.internal("sound/gameStart.wav"));
        paintballSound = Gdx.audio.newSound(Gdx.files.internal("sound/perfectPaintballSound.wav"));
        winningSound = Gdx.audio.newSound(Gdx.files.internal("sound/perfectChicken.wav"));
        
        sounds.add(losingSound);
        sounds.add(gameStarted);
        sounds.add(paintballSound);
        sounds.add(winningSound);
    }
    
    public ArrayList<Sound> getSounds() {
        return sounds;
    }
    
    public Sound getLosingSound() {
        return losingSound;
    }
    
    public Sound getGameStartedSound() {
        return gameStarted;
    }
    
    public Sound getPaintballSound() {
        return paintballSound;
    }
    
    public Sound getwinningSound() {
        return winningSound;
    }
    

}
