package app;

import data_access.*;
import data_access.APIs.SpotifyAPIAdapter;
import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapter;
import data_access.APIs.YoutubeAPIAdapterInterface;
import interface_adapter.*;
import interface_adapter.load_playlist.LoadPlaylistController;
import interface_adapter.load_playlist.LoadPlaylistPresenter;
import interface_adapter.load_token.LoadTokenController;
import interface_adapter.load_token.LoadTokenPresenter;
import interface_adapter.spotify_get.SpotifyGetController;
import interface_adapter.spotify_get.SpotifyGetPresenter;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;
import use_case.load_playlist.LoadPlaylistInputBoundary;
import use_case.load_playlist.LoadPlaylistInteractor;
import use_case.load_playlist.LoadPlaylistOutputBoundary;
import use_case.load_token.LoadTokenDataAccessInterface;
import use_case.load_token.LoadTokenInputBoundary;
import use_case.load_token.LoadTokenInteractor;
import use_case.load_token.LoadTokenOutputBoundary;
import use_case.spotify_get.SpotifyGetDataAccessInterface;
import use_case.spotify_get.SpotifyGetInputBoundary;
import use_case.spotify_get.SpotifyGetInteractor;
import use_case.spotify_get.SpotifyGetOutputBoundary;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInputBoundary;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import view.InitialView;

import javax.swing.*;
import java.io.IOException;

public class GetPlaylistUseCaseFactory {

    private GetPlaylistUseCaseFactory() {}

    public static InitialView create(SpotifyAPIAdapterInterface spotifyAPI, YoutubeAPIAdapterInterface youtubeAPI,
                                     ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
                                     ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel,
                                     TempFileWriterDataAccessObject fileWriter) {
        try {
            LoadTokenController loadTokenController = createLoadTokenUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel, youtubeAPI, spotifyAPI);
            LoadPlaylistController loadPlaylistController = createLoadPlaylistUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
            YoutubeGetController youtubeGetController = createYoutubeGetUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, fileWriter, youtubeAPI);
            SpotifyGetController spotifyGetController = createSpotifyGetUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, fileWriter, spotifyAPI);
            return new InitialView(getPlaylistViewModel, youtubeGetController, spotifyGetController, loadPlaylistController, loadTokenController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load initial page");
        }
        return null;
    }

    private static LoadTokenController createLoadTokenUseCase(
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel,
            YoutubeAPIAdapterInterface youtubeAPI, SpotifyAPIAdapterInterface spotifyAPI) throws IOException {

        LoadTokenOutputBoundary loadTokenOutputBoundary = new LoadTokenPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
        LoadTokenDataAccessInterface loadTokenDataAccessObject = new LoadTokenDataAccessObject();
        LoadTokenInputBoundary loadTokenInteractor = new LoadTokenInteractor(loadTokenDataAccessObject, loadTokenOutputBoundary, youtubeAPI, spotifyAPI);

        return new LoadTokenController(loadTokenInteractor);
    }


    private static LoadPlaylistController createLoadPlaylistUseCase(
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel) throws IOException {

        LoadPlaylistOutputBoundary loadPlaylistOutputBoundary = new LoadPlaylistPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
        LoadPlaylistDataAccessInterface loadPlaylistDataAccessObject = new LoadPlaylistDataAccessObject();
        LoadPlaylistInputBoundary loadPlaylistInteractor = new LoadPlaylistInteractor(loadPlaylistDataAccessObject, loadPlaylistOutputBoundary);

        return new LoadPlaylistController(loadPlaylistInteractor);
    }

    private static YoutubeGetController createYoutubeGetUseCase(
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, TempFileWriterDataAccessObject fileWriter, YoutubeAPIAdapterInterface api) throws IOException {

        YoutubeGetOutputBoundary youtubeGetOutputBoundary = new YoutubeGetPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel);
        YoutubeGetDataAccessInterface youtubeGetDataAccessObject = new YoutubeGetDataAccessObject(api);

        YoutubeGetInputBoundary youtubeGetInteractor = new YoutubeGetInteractor(youtubeGetDataAccessObject,
                fileWriter, youtubeGetOutputBoundary);

        return new YoutubeGetController(youtubeGetInteractor);
    }

    private static SpotifyGetController createSpotifyGetUseCase (
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, TempFileWriterDataAccessObject fileWriter, SpotifyAPIAdapterInterface api) throws IOException{

        SpotifyGetDataAccessInterface spotifyGetDataAccessObject = new SpotifyGetDataAccessObject(api);
        SpotifyGetOutputBoundary spotifyGetOutputBoundary = new SpotifyGetPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel);

        SpotifyGetInputBoundary spotifyGetInteractor = new SpotifyGetInteractor(spotifyGetDataAccessObject, fileWriter, spotifyGetOutputBoundary);

        return new SpotifyGetController(spotifyGetInteractor);
    }
}
