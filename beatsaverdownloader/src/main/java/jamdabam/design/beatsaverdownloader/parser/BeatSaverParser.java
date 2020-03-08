package jamdabam.design.beatsaverdownloader.parser;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jamdabam.design.beatsaverdownloader.entities.Song;

public class BeatSaverParser {
	// Json Membernames
	private static final String DOCS = "docs";

	public static List<Song> parse(String aBeatSaverJsonResult) {

		List<Song> songEntries = new ArrayList<>();

		Gson gson = new GsonBuilder().registerTypeAdapter(Song.class, new BeatSaverDeserializer()).create();

		JsonElement fromJson = gson.fromJson(aBeatSaverJsonResult, JsonElement.class);
		if (fromJson instanceof JsonObject) {
			JsonObject jsonObject = fromJson.getAsJsonObject();

			// Multiple entries
			if (jsonObject.has(DOCS)) {
				JsonArray asJsonArray = jsonObject.getAsJsonArray(DOCS);
				for (JsonElement jsonElement : asJsonArray) {
					songEntries.add(gson.fromJson(jsonElement, Song.class));
				}
			}
			// Single entry
			else {
				songEntries.add(gson.fromJson(fromJson, Song.class));
			}
		}

		return songEntries;
	}
}
