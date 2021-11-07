package za.net.hanro50.forgiac.core.install;

import java.io.File;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import za.net.hanro50.forgiac.core.Base;

@SuppressWarnings({ "rawtypes" })
public class Installv1 extends Common {
    public Installv1(File jar, File MCpath) throws Exception {
        super(jar, MCpath);

        Class ClientInstall;
        String path = "net.minecraftforge.installer";
        try {
            ClientInstall = load("net.minecraftforge.installer.ClientInstall");
        } catch (Exception e) {
            ClientInstall = load("cpw.mods.fml.installer.ClientInstall");
            path = "cpw.mods.fml.installer";
        }
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
            JOptionPane.showMessageDialog(null, "Forge's installer reports that the installation failed!",
                    "Forgiac error", JOptionPane.ERROR_MESSAGE);
    }
}
