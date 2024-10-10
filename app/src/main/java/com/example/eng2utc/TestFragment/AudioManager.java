package com.example.eng2utc.TestFragment;

import android.media.MediaPlayer;
import java.io.IOException;
import java.util.ArrayList;

public class AudioManager {
    private ArrayList<MediaPlayer> audioPlayers;

    public AudioManager(ArrayList<String> listURL) {
        audioPlayers = new ArrayList<>();
        loadAudioList(listURL);
    }

    private void loadAudioList(ArrayList<String> listURL) {
        for (String url : listURL) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // Chuẩn bị để phát
            } catch (IOException e) {
                e.printStackTrace();
            }
            audioPlayers.add(mediaPlayer);
        }
    }

    public MediaPlayer getMediaPlayer(int index) {
        return audioPlayers.get(index);
    }

    public int getAudioListSize() {
        return audioPlayers.size();
    }

    public void releasePlayers() {
        for (MediaPlayer player : audioPlayers) {
            if (player != null) {
                player.release();
            }
        }
        audioPlayers.clear();
    }
}