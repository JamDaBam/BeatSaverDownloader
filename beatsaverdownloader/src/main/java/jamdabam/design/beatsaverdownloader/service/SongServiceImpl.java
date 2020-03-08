package jamdabam.design.beatsaverdownloader.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jamdabam.design.beatsaverdownloader.configuration.Constants;
import jamdabam.design.beatsaverdownloader.entities.Song;
import jamdabam.design.beatsaverdownloader.parser.BeatSaverParser;

public class SongServiceImpl implements SongServiceInt {

	public List<Song> getLatestSongs(int aSongs) {
		List<Song> latestSongs = null;

		try {
			int site = 0;
			int size = 0;

			do {

				String beatSaverResult = readUrl(Constants.LATEST_CALL_BASE_URL + "/" + site);
				List<Song> songs = BeatSaverParser.parse(beatSaverResult);

				if (latestSongs == null) {
					latestSongs = new ArrayList<>();
				}

				size = latestSongs.size();

				if (size < aSongs) {
					if (songs.isEmpty()) {
						break;
					}

					for (Song song : songs) {
						latestSongs.add(song);
						size++;

						if (size == aSongs) {
							break;
						}
					}
				}

				site++;
			} while (size < aSongs);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Etwas ist beim lesen der latest Songs schief gelaufen. " + e.getMessage());
		}

		if (latestSongs == null) {
			latestSongs = Collections.emptyList();
		}

		return latestSongs;
	}

	@Override
	public void downloadSongs(List<Song> aSongs, String aTo) {
		for (Song song : aSongs) {
			donwloadSong(song, aTo);
		}
	}

	@Override
	public boolean donwloadSong(Song aSong, String aTo) {
		boolean isDownloaded = false;

		String downloadName = aSong.getDownloadName();

		// Checks songidprefix of files in downloadpath if found one or more files skip.
		if (!checkSongIdAlreadyDownloaded(aTo, aSong.getKey())) {
			File downloadFile = new File(aTo, downloadName);

			try {
				// Doublecheck if the new file exists
				if (!downloadFile.exists()) {
					if (!downloadFile.getParentFile().exists()) {
						downloadFile.getParentFile().mkdirs();
					}

					String downloadUrl = aSong.getDirectDownload();
					URL url = new URL(downloadUrl);
					saveUrl(downloadFile.toPath(), url, 30, 30);
					System.out.println("Downloaded: " + downloadName);
				} else {
					System.out.println("File exists: " + downloadName);
				}

				isDownloaded = true;
			} catch (IOException e) {
				if (e instanceof FileNotFoundException) {
					System.out.println("File not found " + downloadName);
				}
			}
		} else {
			isDownloaded = true;
			System.out.println("Key exists: " + downloadName);
		}

		return isDownloaded;
	}

	private boolean checkSongIdAlreadyDownloaded(final String aPath, final String aSongId) {
		File dir = new File(aPath);

		// list the files using a anonymous FileFilter
		File[] files = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.getName().startsWith(aSongId + " ");
			}
		});

		if (files != null && files.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void saveUrl(final Path aFile, final URL aUrl, int aSecsConnectTimeout, int aSecsReadTimeout)
			throws MalformedURLException, IOException {
		try (InputStream in = streamFromUrl(aUrl, aSecsConnectTimeout, aSecsReadTimeout)) {
			Files.copy(in, aFile);
		}
	}

	private InputStream streamFromUrl(URL aUrl, int aSecsConnectTimeout, int aSecsReadTimeout) throws IOException {
		URLConnection conn = aUrl.openConnection();
		conn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		if (aSecsConnectTimeout > 0)
			conn.setConnectTimeout(aSecsConnectTimeout * 1000);
		if (aSecsReadTimeout > 0)
			conn.setReadTimeout(aSecsReadTimeout * 1000);
		return conn.getInputStream();
	}

	private String readUrl(String urlString) throws IOException {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}

			return buffer.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
}
