package use_case.load_token;

import org.json.JSONObject;

public class LoadTokenOutputData {
    private JSONObject data;

    public LoadTokenOutputData(JSONObject tokenObject) {
        this.data = tokenObject;
    }

    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        for (String key : data.keySet()) {
            String value = (String) data.get(key);
            if (value != null) {
                toPrint.append(key).append(": ").append(value).append("\n");
            }
        }
        return toPrint.toString();
    }
}
