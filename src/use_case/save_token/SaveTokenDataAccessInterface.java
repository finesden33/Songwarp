package use_case.save_token;

import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapterInterface;
import org.json.JSONObject;

public interface SaveTokenDataAccessInterface {
    String saveTokenSheet(SaveTokenInputData inputData);
    void updateAPIAdapters(JSONObject tokenObject, YoutubeAPIAdapterInterface youtubeAPI, SpotifyAPIAdapterInterface spotifyAPI);
}