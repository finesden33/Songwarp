package interface_adapter.save_token;

import interface_adapter.GetPlaylistState;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.save_playlist.SavePlaylistOutputBoundary;
import use_case.save_playlist.SavePlaylistOutputData;
import use_case.save_token.SaveTokenOutputBoundary;
import use_case.save_token.SaveTokenOutputData;


public class SaveTokenPresenter implements SaveTokenOutputBoundary {
    private final GetPlaylistViewModel getPlaylistViewModel;
    private final ViewManagerModel viewManagerModel;

    public SaveTokenPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel) {
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SaveTokenOutputData outputData) {
        GetPlaylistState state = getPlaylistViewModel.getState();
        state.setError("Successfully saved API token sheet\nand updated API keys (no need to load the file now)");
        getPlaylistViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState state = getPlaylistViewModel.getState();
        state.setError(error);
        getPlaylistViewModel.firePropertyChanged();
    }
}