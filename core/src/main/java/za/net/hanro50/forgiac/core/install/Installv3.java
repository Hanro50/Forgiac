package za.net.hanro50.forgiac.core.install;

import java.io.InputStreamReader;
import java.lang.reflect.Method;

import com.google.gson.GsonBuilder;

import za.net.hanro50.forgiac.core.misc.VJson;
import za.net.hanro50.forgiac.core.misc.VManifest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

public class Installv3 extends Common {

    public Installv3(File jar, File dotMC) throws Exception {
        super(jar, dotMC);
        try {
            Class<?> main = load("net.minecraftforge.installer.SimpleInstaller");
            Method method = main.getMethod("main", new Class[] { Array.newInstance(String.class, 0).getClass() });

            File profiles = (new File(dotMC, "launcher_profiles.json"));
            if (!profiles.exists()) {
                profiles.createNewFile();
            }
            method.invoke(null, new Object[] { new String[] { "--installClient", dotMC.getAbsolutePath() } });

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public VManifest getManifest() {
        BufferedReader buf = new BufferedReader(new InputStreamReader(child.getResourceAsStream("version.json")));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            line = buf.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            buf.close();

            VJson json = new GsonBuilder().create().fromJson(sb.toString(), VJson.class);

            return new VManifest(json.id, json.inheritsFrom);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
