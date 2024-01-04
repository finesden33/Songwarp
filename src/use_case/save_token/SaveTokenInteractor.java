package use_case.save_token;

import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapterInterface;

public class SaveTokenInteractor implements SaveTokenInputBoundary {
    private final SaveTokenDataAccessInterface dataAccessObject;
    private final SaveTokenOutputBoundary presenter;
    private final YoutubeAPIAdapterInterface youtubeAPI;
    private final SpotifyAPIAdapterInterface spotifyAPI;

    public SaveTokenInteractor(SaveTokenDataAccessInterface dataAccessObject,
                               SaveTokenOutputBoundary presenter,
                               YoutubeAPIAdapterInterface youtubeAPI, SpotifyAPIAdapterInterface spotifyAPI) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
        this.youtubeAPI = youtubeAPI;
        this.spotifyAPI = spotifyAPI;
    }

    @Override
    public void execute(SaveTokenInputData inputData) {
        try {
            String filepath = dataAccessObject.saveTokenSheet(inputData);
            dataAccessObject.updateAPIAdapters(inputData.getData(), youtubeAPI, spotifyAPI);
            SaveTokenOutputData outputData = new SaveTokenOutputData(filepath);
            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            presenter.prepareFailView("Failed to token sheet: " + e.getMessage());
        }
    }
}