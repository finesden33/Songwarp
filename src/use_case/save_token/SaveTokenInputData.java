package use_case.save_token;

import entity.Playlist;
import org.json.JSONObject;

import javax.swing.*;

public class SaveTokenInputData {
    private final JSONObject data;

    public SaveTokenInputData(JTextField[] info) {
        String[] keys = {"youtubeKey", "youtubeClientID", "youtubeClientSecret", "spotifyClientID", "spotifyClientSecret"};
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < 5; i++) {
            jsonObject.put(keys[i], info[i].getText());
        }
        this.data = jsonObject;
    }

    public JSONObject getData() {
        return data;
    }
}