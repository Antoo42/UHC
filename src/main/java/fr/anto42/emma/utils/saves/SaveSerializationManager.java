package fr.anto42.emma.utils.saves;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.anto42.emma.game.impl.config.UHCConfig;

public class SaveSerializationManager {

    private final Gson gson;

    public SaveSerializationManager() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(UHCConfig uhcConfig) {
        return this.gson.toJson(uhcConfig);
    }

    public UHCConfig deserialize(String json) {
        return this.gson.fromJson(json, UHCConfig.class);
    }

}
