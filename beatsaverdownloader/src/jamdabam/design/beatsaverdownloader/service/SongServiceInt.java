package jamdabam.design.beatsaverdownloader.service;

import java.util.List;

import jamdabam.design.beatsaverdownloader.entities.Song;

public interface SongServiceInt {
	public List<Song> getLatestSongs(int aSongs);

	public void downloadSongs(List<Song> aSongs, String aTo);

	public boolean donwloadSong(Song aSong, String aTo);
}
