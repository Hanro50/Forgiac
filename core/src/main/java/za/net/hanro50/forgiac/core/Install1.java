package za.net.hanro50.forgiac.core;

import java.io.File;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

@SuppressWarnings({ "rawtypes" })
public class Install1 extends Install {
    public Install1(File jar, File MCpath) throws Exception {
        super(jar, MCpath);
        Class ClientInstall;
        try{
            ClientInstall = load("net.minecraftforge.installer.ClientInstall");
        }catch(Exception e){
            ClientInstall = load("cpw.mods.fml.installer.ClientInstall");
        }
        Object Client = construct(ClientInstall);
        Predicate<String> optionals = a -> true;
        Boolean suc;
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
