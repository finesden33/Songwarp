package use_case.save_token;

public class SaveTokenOutputData {
    private final String filePath;

    public SaveTokenOutputData(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}