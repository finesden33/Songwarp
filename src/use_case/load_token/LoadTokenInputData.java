package use_case.load_token;

public class LoadTokenInputData {
    private final String filePath;

    public LoadTokenInputData(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
