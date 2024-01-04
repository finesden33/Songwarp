package data_access;

import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapterInterface;
import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.load_token.LoadTokenDataAccessInterface;
import view.InitialView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.prefs.Preferences;

import static data_access.TempFileWriterDataAccessObject.readTempJSON;

public class LoadTokenDataAccessObject implements LoadTokenDataAccessInterface {
    private String filepath;

    public void setFilePath(String filepath) {
        this.filepath = filepath;
    }
    public String fetchFilePath() {
        return this.filepath;
    }

    public String getFilePath() {
        JFileChooser fileChooser = new JFileChooser("src");
        Preferences prefs = Preferences.userNodeForPackage(InitialView.class);
        String lastOpenedFilePath = prefs.get("lastOpenedFilePath", null);

        // Set the initially selected file to the parent folder of the most recently opened file
        if (lastOpenedFilePath != null) {
            File lastOpenedFile = new File(lastOpenedFilePath);
            if (lastOpenedFile.exists() && lastOpenedFile.isFile()) {
                fileChooser.setSelectedFile(lastOpenedFile.getAbsoluteFile());
            }
        }

        //sets a filter for possible extensions
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("SongWarp API token sheet", "SWtokens");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter1);
        fileChooser.addChoosableFileFilter(filter2);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            prefs.put("lastOpenedFilePath", selectedFile.getAbsolutePath());

            // Get the absolute path of the selected file and set it in the text field
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public JSONObject readTokenSave(String file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            JSONObject jsonObject = new JSONObject();
            String line;
            String[] keyThings = {"youtubeKey", "youtubeClientID", "youtubeClientSecret", "spotifyClientID", "spotifyClientSecret"};
            int i = 0;
            while ((line = br.readLine()) != null) {
                jsonObject.put(keyThings[i], line.trim());
                i++;
            }
            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateAPIAdapters(JSONObject tokenObject, YoutubeAPIAdapterInterface youtubeAPI, SpotifyAPIAdapterInterface spotifyAPI) {
        String yKey = tokenObject.getString("youtubeKey");
        String yID = tokenObject.getString("youtubeClientID");
        String ySecret = tokenObject.getString("youtubeClientSecret");
        String sID = tokenObject.getString("spotifyClientID");
        String sSecret = tokenObject.getString("spotifyClientSecret");

        youtubeAPI.setTokens(yID, ySecret, yKey);
        spotifyAPI.setTokens(sID, sSecret);
    }
}