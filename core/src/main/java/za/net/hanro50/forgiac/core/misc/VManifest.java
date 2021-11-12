package za.net.hanro50.forgiac.core.misc;

public class VManifest {

    public String id;
    public String base;
    public String type = "forge";

    public VManifest(String id, String base) {
        this.id = id;
        this.base = base;
    }

    @Override
    public String toString() {
        return String.format("{\"id\": \"%s\",\"base\": \"%s\",\"type\": \"forge\"}", id, base);
    }
}
