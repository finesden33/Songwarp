package data_access.APIs;

import org.json.JSONArray;

public interface SpotifyAPIAdapterInterface {
    void setTokens(String clientID, String clientSecret);
    String request(APIRequestInfo info);
    String getPlaylist(String playlistID);
    String searchSong(String query);
    String createPlaylist(String userID, String name, String relatedUrl, String key);
    String addSongsToPlaylist(String playlistID, JSONArray songs, String key);
    String getUser(String key);
    String getUserAuthAccessToken();
}
