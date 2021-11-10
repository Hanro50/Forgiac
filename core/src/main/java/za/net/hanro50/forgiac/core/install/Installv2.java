package za.net.hanro50.forgiac.core.install;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import javax.swing.JFrame;

import za.net.hanro50.forgiac.core.Base;
import za.net.hanro50.forgiac.core.VManifest;

@SuppressWarnings({ "rawtypes" })
public class Installv2 extends Common {
    /** Installer for 1.12+ installer */

    private void installer(File jar, File MCpath, Object callback) throws Exception {
        Class Util = load("net.minecraftforge.installer.json.Util");
        Object InstallProfile = invoke(Util, "loadInstallProfile");
        System.out.println(InstallProfile);

        Class ClientInstall = load("net.minecraftforge.installer.actions.ClientInstall");

        Object obj = construct(ClientInstall, InstallProfile, callback);
        File installer = new File(obj.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        Predicate<String> optionals = a -> true;
        try {
            invoke(obj, "run", MCpath, optionals, installer);
        } catch (ReflectiveOperationException e) {
            invoke(obj, "run", MCpath, optionals);
        }
    }

    public Installv2(File jar, File MCpath, Object callback) throws Exception {
        super(jar, MCpath);
        installer(jar, MCpath, callback);
    }

    public Installv2(File jar, File MCpath) throws Exception {
        super(jar, MCpath);
        Class ProgressCallback = load("net.minecraftforge.installer.actions.ProgressCallback");
        Object withOutputs = ProgressCallback.getField("TO_STD_OUT").get(null);

        if (!Base.noGui) {
            Class ProgressFrame = load("net.minecraftforge.installer.ProgressFrame");
            withOutputs = construct(ProgressFrame, withOutputs, "Forge installer", (Runnable) () -> {
                System.out.println("Cancelled by user");
                System.exit(0);
            });

        }
        JFrame p = null;
        System.out.println("PopUp:" + Base.noGui);
        if (withOutputs instanceof JFrame) {
            System.out.println("PopUp");
            p = ((JFrame) withOutputs);
            p.setVisible(true);
            p.toFront();
        }
        installer(jar, MCpath, withOutputs);
        if (p != null)
            p.dispose();
    }

    @Override
    public VManifest getManifest() {
        Class Util;
        try {
            Util = load("net.minecraftforge.installer.json.Util");// getMinecraft
            Object InstallProfile = invoke(Util, "loadInstallProfile");
            return new VManifest((String) invoke(InstallProfile, "getVersion"),

            (String) invoke(InstallProfile, "getMinecraft"));
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
