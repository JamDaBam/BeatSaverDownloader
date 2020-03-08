package jamdabam.design.beatsaverdownloader.parser;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import jamdabam.design.beatsaverdownloader.configuration.Constants;
import jamdabam.design.beatsaverdownloader.entities.Song;
import jamdabam.design.beatsaverdownloader.entities.SongDifficulty;

public class BeatSaverDeserializer implements JsonDeserializer<Song> {

	// Jsonkeys
	private static final String KEY = "key";
	private static final String SONG_NAME = "songName";
	private static final String SONG_SUB_NAME = "songSubname";
	private static final String LEVEL_AUTHOR_NAME = "levelAuthorName";
	private static final String SONG_AUTHOR_NAME = "songAuthorName";
	private static final String DOWNLOAD_URL = "downloadURL";
	private static final String BPM = "bpm";
	private static final String DOWN_VOTES = "downVotes";
	private static final String UP_VOTES = "upVotes";
	private static final String RATING = "rating";
	private static final String HEAT = "heat";
	private static final String DIFFICULTIES = "difficulties";
	private static final String COVER_URL = "coverURL";
	private static final String DIRECT_DOWNLOAD = "directDownload";
	private static final String CHARACTERISTICS = "characteristics";

	public Song deserialize(JsonElement aJson, Type aTypeOfT, JsonDeserializationContext aContext)
			throws JsonParseException {
		return deserialize(aJson, new Song());
	}

	private Song deserialize(JsonElement aJsonElement, Song aSong) {
		if (!(aJsonElement instanceof JsonPrimitive) && !(aJsonElement instanceof JsonNull)) {
			if (aJsonElement instanceof JsonArray) {
				for (JsonElement jsonElement : aJsonElement.getAsJsonArray()) {
					aSong = deserialize(jsonElement, aSong);
				}
			} else {
				JsonObject jsonObject = (JsonObject) aJsonElement;

				for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
					String key = entry.getKey();
					JsonElement value = entry.getValue();

					if (KEY.equals(key)) {
						aSong.setKey(value.getAsString());
					} else if (SONG_NAME.equals(key)) {
						aSong.setSongName(value.getAsString());
					} else if (SONG_SUB_NAME.equals(key)) {
						aSong.setSongSubName(value.getAsString());
					} else if (LEVEL_AUTHOR_NAME.equals(key)) {
						aSong.setLevelAuthorName(value.getAsString());
					} else if (SONG_AUTHOR_NAME.equals(key)) {
						aSong.setSongAuthorName(value.getAsString());
					} else if (DOWNLOAD_URL.equals(key)) {
						aSong.setDownloadURL(Constants.BASE_URL + value.getAsString());
					} else if (BPM.equals(key)) {
						aSong.setBpm(value.getAsInt());
					} else if (DOWN_VOTES.equals(key)) {
						aSong.getStats().setDownVotes(value.getAsInt());
					} else if (UP_VOTES.equals(key)) {
						aSong.getStats().setUpVotes(value.getAsInt());
					} else if (RATING.equals(key)) {
						aSong.getStats().setRating(value.getAsFloat());
					} else if (HEAT.equals(key)) {
						aSong.getStats().setHeat(value.getAsFloat());
					} else if (DIRECT_DOWNLOAD.equals(key)) {
						aSong.setDirectDownload(value.getAsString());
					} else if (COVER_URL.equals(key)) {
						aSong.setCoverURL(value.getAsString());
					} else if (DIFFICULTIES.equals(key)) {
						JsonObject difficulties = value.getAsJsonObject();
						for (Entry<String, JsonElement> difficultEntry : difficulties.entrySet()) {
							aSong.getDifficulties().put(difficultEntry.getKey(),
									difficultEntry.getValue().getAsBoolean());
						}
					} else if (CHARACTERISTICS.equals(key)) {
						JsonArray asJsonArray = value.getAsJsonArray();
						for (JsonElement jsonElement : asJsonArray) {
							for (Entry<String, JsonElement> entry2 : jsonElement.getAsJsonObject().entrySet()) {
								if ("difficulties".equals(entry2.getKey())) {

								} else {

								}
							}
						}

					} else {
						aSong = deserialize(value, aSong);
					}
				}
			}
		}
		return aSong;
	}
}
