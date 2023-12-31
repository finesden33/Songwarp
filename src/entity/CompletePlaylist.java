package entity;

import org.json.JSONObject;

import java.util.ArrayList;

public class CompletePlaylist extends Playlist {
    private String youtubeID;
    private String spotifyID;

    public CompletePlaylist(String name, String genre, String youtubeID, String spotifyID) {
        super(name, genre);
        this.youtubeID = youtubeID;
        this.spotifyID = spotifyID;
    }

    public String[] getIDs() {
        return new String[]{youtubeID, spotifyID};
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public int getDuration() {
        int totalDuration = 0;
        for (CompleteSong song : this.getCompleteSongs()) {
            totalDuration += song.getDuration();
        }
        return totalDuration;
    }

    public ArrayList<CompleteSong> getCompleteSongs() {
        // Implement the logic to filter and return Spotify songs from the playlist
        ArrayList<CompleteSong> completeSongs = new ArrayList<>();
        for (Song song : this.getList()) {
            if (song instanceof CompleteSong) {
                completeSongs.add((CompleteSong) song);
            }
        }
        return completeSongs;
    }

    @Override
    public ArrayList<Song> getList() {
        return super.getList();
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = super.convertToJSON();
        jsonObject.append("youtubeID", this.getIDs()[0]);
        jsonObject.append("spotifyID", this.getIDs()[1]);
        return jsonObject;
    }
}