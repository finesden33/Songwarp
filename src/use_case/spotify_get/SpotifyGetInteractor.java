package use_case.spotify_get;

import data_access.TempFileWriterDataAccessObject;
import entity.PlaylistBuilderDirector;
import entity.SpotifyPlaylist;
import entity.SpotifyPlaylistBuilder;
import org.json.JSONObject;


public class SpotifyGetInteractor implements SpotifyGetInputBoundary {

    final SpotifyGetDataAccessInterface spotifyGetDataAccessObject;
    final TempFileWriterDataAccessObject fileWriter;
    final SpotifyGetOutputBoundary spotifyGetPresenter;

    public SpotifyGetInteractor(SpotifyGetDataAccessInterface spotifyGetDataAccessInterface,
                                TempFileWriterDataAccessObject fileWriter, SpotifyGetOutputBoundary spotifyGetOutputBoundary) {
        this.spotifyGetDataAccessObject = spotifyGetDataAccessInterface;
        this.fileWriter = fileWriter;
        this.spotifyGetPresenter = spotifyGetOutputBoundary;
    }


    public void execute(SpotifyGetInputData spotifyGetInputData){

        String id = spotifyGetInputData.getId();

        if (id != null) {
            JSONObject jsonFile = spotifyGetDataAccessObject.getPlaylistJSON(id);

            if (!jsonFile.has("error")) {
                System.out.println(jsonFile);
                // build youtubePlaylist object from static builder class in entity
                PlaylistBuilderDirector director = new PlaylistBuilderDirector();
                SpotifyPlaylistBuilder builder = new SpotifyPlaylist.Builder();
                director.BuildSpotifyPlaylist(builder, jsonFile, id);
                SpotifyPlaylist spotifyPlaylist = builder.build();

                // store instance in project temp save file (DAO request 3)
                fileWriter.writePlaylistFile(spotifyPlaylist);

                // invoke presenter
                SpotifyGetOutputData spotifyGetOutputData = new SpotifyGetOutputData(spotifyPlaylist, false);
                spotifyGetPresenter.prepareSuccessView(spotifyGetOutputData);
            } else {
                String errorMessage = "Error: " + jsonFile.getJSONObject("error").getString("message");
                spotifyGetPresenter.prepareFailView(errorMessage);
            }
        } else {
            spotifyGetPresenter.prepareFailView("Invalid Spotify Playlist Url");
        }
    }
}
