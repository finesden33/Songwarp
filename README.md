# SongWarp

Do you have a music playlist on YouTube you want on Spotify? Or a Spotify playlist you want on YouTube? This YouTube-Spotify Playlist converter has got you covered!

This software lets you input a url for a YouTube Playlist, and it'll convert it into something you can upload to your Spotify account. You can also do the same from Spotify to YouTube.

## Created by:
- Abhishek Sharma (abhilo)
- Ethan Fine (finesden33)
- Xinze Wang (wilson0516)
- Mahek Cheema (mmahekk)


## Specification
- **YouTube to Spotify Playlist Conversion:** YouTube playlist url → Match Songs → Upload to Spotify account

- **Spotify to YouTube Playlist Conversion:** Spotify playlist url → Match Songs → Upload to YouTube account

- **Save Playlist as a File or View Playlist:** Spotify or YouTube url → Match Songs → Download File or View

- **Load Saved File for Playlist Conversion:** Spotify or YouTube url → Try matching songs → Run out of
  tokens → Auto-save playlist file → Reload program later → Load
  file → Continue matching → Download/View/Upload

- **Load / Create an API token sheet or use the default shared API keys (not recommended)**


## API usage documentation links
- **Spotify API:**
  https://developer.spotify.com/documentation/web-api
- **YouTube API:**
  https://developers.google.com/youtube/v3/getting-started

# Notes:
- Currently, you have to create a project (we used IntelliJ) with SDK set to Oracle openJDK 20
- Currently, if you do not make your own YouTube and Spotify API projects (through your Spotify and Google accounts), you can opt to ignore creating/loading an API token sheet. However, this means you need to request access from the project owner (a joint account that the group members have access to).
- Converting from a Spotify playlist to YouTube is a lot more expensive (API token wise) than vice versa. This is because Youtube API calls, especially search song and add song to playlist use a lot of the daily API quota. Therefore, if your playlist has about 80 songs or more, it will probably run out of tokens. But don't worry, since in the case you run out of tokens, the program will autosave your progress and you can continue the following day when your quota renews. (This isn't so much a problem YouTube to Spotify, since Spotify API requests are pretty cheap. You can easily get away with converting+uploading a 500+ song youtube playlist through our program). 
- If you want to know precise quota specifications: There is a daily 10,000 quota for YouTube API usage per day per API project. Say, your spotify playlist had 100 songs. The program will need to perform 100 video search requests, 1 playlist creation request, and 100 song addition requests. Each video search request costs 100 quota, the playlist creation costs 50 quota, and then each song addition costs 50 quota. Also, when performing a YouTube to Spotify conversion, only the Playlist get request from the Youtube API is necessary. This costs 1 quota per 50 songs in the playlist (rounded up) (e.g. a 583 playlist would cost 12 YouTube API quota to retrieve all the data for it)