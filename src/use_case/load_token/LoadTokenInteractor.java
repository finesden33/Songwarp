package use_case.load_token;

import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapterInterface;
import entity.CompletePlaylist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import org.json.JSONObject;
import utilities.CheckMultiplePlaylist;
import utilities.SplitFile;

import java.util.Objects;

public class LoadTokenInteractor implements LoadTokenInputBoundary {
    private final LoadTokenDataAccessInterface loadTokenDataAccessObject;
    private final LoadTokenOutputBoundary loadTokenPresenter;
    private final YoutubeAPIAdapterInterface youtubeAPI;
    private final SpotifyAPIAdapterInterface spotifyAPI;

    public LoadTokenInteractor(LoadTokenDataAccessInterface loadTokenDataAccessObject,
                               LoadTokenOutputBoundary loadTokenPresenter,
                               YoutubeAPIAdapterInterface youtubeAPI, SpotifyAPIAdapterInterface spotifyAPI) {
        this.loadTokenDataAccessObject = loadTokenDataAccessObject;
        this.loadTokenPresenter = loadTokenPresenter;
        this.youtubeAPI = youtubeAPI;
        this.spotifyAPI = spotifyAPI;
    }

    @Override
    public void execute() {
        try {
            String filepath = loadTokenDataAccessObject.getFilePath();
            JSONObject tokenObject = loadTokenDataAccessObject.readTokenSave(filepath);
            loadTokenDataAccessObject.updateAPIAdapters(tokenObject, youtubeAPI, spotifyAPI);
            LoadTokenOutputData loadTokenOutputData = new LoadTokenOutputData(tokenObject);
            loadTokenPresenter.prepareSuccessView(loadTokenOutputData);

        } catch (Exception e) {
            loadTokenPresenter.prepareFailView("Failed to load tokenSheet");
        }
    }
}
