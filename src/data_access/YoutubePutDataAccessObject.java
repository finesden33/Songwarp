package data_access;

import data_access.APIs.SpotifyAPIAdapter;
import data_access.APIs.SpotifyAPIAdapterInterface;
import entity.CompletePlaylist;
import entity.CompleteSong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.youtube_put.YoutubePutDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.min;

public class YoutubePutDataAccessObject implements YoutubePutDataAccessInterface {
    final SpotifyAPIAdapterInterface api;

    public YoutubePutDataAccessObject(SpotifyAPIAdapterInterface api) {
        this.api = api;
    }

    @Override
    public String getUserAuthorization() {
        return api.getUserAuthAccessToken();
    }

    @Override
    public String getUserID(String token) {
        String response = api.getUser(token);
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.has("id")) {
            return jsonObject.getString("id");
        }
        return null;
    }

    @Override
    public String initializeSpotifyPlaylist(String userID, String playlistName, String youtubeUrl, String token) {
        String response = api.createPlaylist(userID, playlistName, youtubeUrl, token);
        if (response != null) {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("uri")) {  // we want to also return the spotify playlist id
                String[] parts = jsonObject.getString("uri").split(":");
                return parts[parts.length - 1];
            }
        }
        return null;
    }

    @Override
    public void uploadSongs(String spotifyPlaylistID, CompletePlaylist playlist, String token) throws IOException, InterruptedException {
        for (int i = 0; i < Math.ceil((double) playlist.getTotal() / 100); i++) {  // because request can only handle up to 100 songs at a time
            List<CompleteSong> songs = playlist.getCompleteSongs().subList(i * 100, min(playlist.getTotal(), (i + 1) * 100));
            ArrayList<String> batch = new ArrayList<>();
            for (CompleteSong song : songs) {
                batch.add("spotify:track:" + song.getSpotifyId());
            }
            api.addSongsToPlaylist(spotifyPlaylistID, new JSONArray(batch), token);
        }
    }
}
