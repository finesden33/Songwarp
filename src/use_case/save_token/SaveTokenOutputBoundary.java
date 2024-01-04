package use_case.save_token;

public interface SaveTokenOutputBoundary {
    void prepareSuccessView(SaveTokenOutputData outputData);
    void prepareFailView(String error);
}