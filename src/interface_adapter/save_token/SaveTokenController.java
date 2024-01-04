package interface_adapter.save_token;

import entity.Playlist;
import use_case.save_playlist.SavePlaylistInputBoundary;
import use_case.save_playlist.SavePlaylistInputData;
import use_case.save_token.SaveTokenInputBoundary;
import use_case.save_token.SaveTokenInputData;

import javax.swing.*;


public class SaveTokenController {
    private final SaveTokenInputBoundary interactor;

    public SaveTokenController(SaveTokenInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(JTextField[] info) {
        try {
            SaveTokenInputData inputData = new SaveTokenInputData(info);

            interactor.execute(inputData);
        } catch (Exception e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }
    }

}