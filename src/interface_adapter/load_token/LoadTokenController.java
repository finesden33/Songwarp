package interface_adapter.load_token;

import use_case.load_token.LoadTokenInputBoundary;

public class LoadTokenController {
    final LoadTokenInputBoundary loadTokenUseCaseInteractor;

    public LoadTokenController(LoadTokenInputBoundary loadTokenUseCaseInteractor) {
        this.loadTokenUseCaseInteractor = loadTokenUseCaseInteractor;
    }

    public void execute() {
        loadTokenUseCaseInteractor.execute();
    }
}
