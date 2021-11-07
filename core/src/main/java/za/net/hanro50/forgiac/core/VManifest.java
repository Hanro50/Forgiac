package za.net.hanro50.forgiac.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VManifest {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String id;
    public String base;
    public String type = "forge";

    public VManifest(String id, String base) {
        this.id = id;
        this.base = base;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
