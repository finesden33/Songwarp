package use_case.load_token;

import data_access.APIs.SpotifyAPIAdapter;
import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapterInterface;
import entity.CompletePlaylist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import org.json.JSONObject;

public interface LoadTokenDataAccessInterface {

    void setFilePath(String filePath);
    String fetchFilePath();
    String getFilePath();
    JSONObject readTokenSave(String file);

    void updateAPIAdapters(JSONObject tokenObject, YoutubeAPIAdapterInterface youtubeAPI,
                           SpotifyAPIAdapterInterface spotifyAPI);
}
