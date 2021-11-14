package za.net.hanro50.forgiac.core.install;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.ExitCodes;
import za.net.hanro50.forgiac.core.misc.VManifest;

@SuppressWarnings({ "rawtypes" })
public class Installv1 extends Common {
    String path;
    Class ClientInstall;

    private void chickup() throws ClassNotFoundException {
        if (ClientInstall == null) {
            path = "net.minecraftforge.installer";
            try {
                ClientInstall = load("net.minecraftforge.installer.ClientInstall");
            } catch (Exception e) {
                ClientInstall = load("cpw.mods.fml.installer.ClientInstall");
                path = "cpw.mods.fml.installer";
            }
        }
    }

    public Installv1(File jar, File MCpath) throws Exception {
        super(jar, MCpath);
        chickup();
        Object Client = construct(ClientInstall);
        Predicate<String> optionals = a -> true;
        Boolean suc;
        try {
            Class LP = load(path + ".ServerInstall");
            LP.getField("headless").set(null, Base.noGui);
        } catch (Exception e) {
        }
        try {
            suc = (Boolean) invoke(Client, "run", MCpath, optionals);
        } catch (Exception e) {
            e.printStackTrace();
            suc = (Boolean) invoke(Client, "run", MCpath);
        }

        if (!suc)
            ExitCodes.exit(202);
    }

    @Override
    public VManifest getManifest() {
        try {
            chickup();
            Class clazz = load(path + ".VersionInfo");
            System.out.println(
                    (String) invoke(clazz, "getProfileName") + ":" + (String) invoke(clazz, "getVersionTarget"));
            return new VManifest((String) invoke(clazz, "getVersionTarget"), (String) invoke(clazz, "getMinecraftVersion") );
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
