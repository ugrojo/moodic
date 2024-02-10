package com.moodic.music;

public class SpotifyMusicAPI implements MusicAPI {

    @Override
    public String[] getSongsByGenre(String genre) {

        return new String[]{"No songs for " + genre};
    }
}
