package interface_adapter.load_token;

import interface_adapter.*;
import use_case.load_playlist.LoadPlaylistOutputData;
import use_case.load_token.LoadTokenOutputBoundary;
import use_case.load_token.LoadTokenOutputData;

public class LoadTokenPresenter implements LoadTokenOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GetPlaylistViewModel getPlaylistViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;
    private final PutPlaylistViewModel putPlaylistViewModel;

    public LoadTokenPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
                              ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }
    @Override
    public void prepareSuccessView(LoadTokenOutputData data) {
        GetPlaylistState state = getPlaylistViewModel.getState();
        state.setError(data.toString());
        getPlaylistViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState state = getPlaylistViewModel.getState();
        state.setError(error);
        getPlaylistViewModel.firePropertyChanged();
    }
}
