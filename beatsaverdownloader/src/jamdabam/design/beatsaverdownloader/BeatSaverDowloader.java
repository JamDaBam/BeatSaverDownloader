package jamdabam.design.beatsaverdownloader;

import java.io.IOException;
import java.util.List;

import jamdabam.design.beatsaverdownloader.entities.Song;
import jamdabam.design.beatsaverdownloader.service.SongServiceImpl;
import jamdabam.design.beatsaverdownloader.service.SongServiceInt;

public class BeatSaverDowloader {
	public static void main(String[] args) throws IOException {
		SongServiceInt songService = new SongServiceImpl();
		List<Song> latestSongs = songService.getLatestSongs(5);
		songService.downloadSongs(latestSongs, "C:\\BSaberSongsTest");
		System.out.println("fertig");
	}
}
