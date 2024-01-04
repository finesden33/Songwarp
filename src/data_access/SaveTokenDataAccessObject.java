package data_access;

import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapterInterface;
import org.json.JSONObject;
import use_case.save_token.SaveTokenDataAccessInterface;
import use_case.save_token.SaveTokenInputData;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SaveTokenDataAccessObject implements SaveTokenDataAccessInterface {
    @Override
    public String saveTokenSheet(SaveTokenInputData inputData) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Select Folder to Save");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showDialog(null, "Save");

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedDirectory = fileChooser.getSelectedFile().getAbsolutePath();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss", Locale.ENGLISH));
            String fileName = "API_token_sheet_" + time;
            String filePath = selectedDirectory + File.separator + fileName + ".SWtokens";
            JSONObject jsonObject = inputData.getData();

            try (FileWriter file = new FileWriter(filePath)) {
                for (String key : jsonObject.keySet()) {
                    file.write(jsonObject.getString(key) + "\n");
                }
                System.out.println("Token Sheet saved to " + filePath);
                return filePath;

            } catch (IOException e) {
                return null;
            }
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            System.out.println("User canceled the operation");
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