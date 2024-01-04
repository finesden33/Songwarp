package use_case.load_token;

public interface LoadTokenOutputBoundary {
    void prepareSuccessView(LoadTokenOutputData outputData);

    void prepareFailView(String error);
}
