package za.net.hanro50.forgiac.core;
import java.io.File;
import java.util.function.Predicate;

import javax.swing.JFileChooser;
@SuppressWarnings({"rawtypes"})
public class Install2 extends Install {
        /** Installer for 1.12+ installer */

        private void installer(File jar, File MCpath, Object callback) throws Exception {
            Class Util = load("net.minecraftforge.installer.json.Util");
            Object InstallProfile = invoke(Util, "loadInstallProfile");
            System.out.println(InstallProfile);
    
            JFileChooser filesjooser = new JFileChooser();
            filesjooser.showOpenDialog(null);
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
    
        public Install2(File jar, File MCpath, Object callback) throws Exception {
            super(jar,MCpath);
            installer(jar, MCpath, callback);
        }
    
        public Install2(File jar, File MCpath) throws Exception {
            super(jar,MCpath);
            Class ProgressCallback = load("net.minecraftforge.installer.actions.ProgressCallback");
            Object withOutputs = ProgressCallback.getField("TO_STD_OUT").get(null);
            installer(jar, MCpath, withOutputs);
        }
}
