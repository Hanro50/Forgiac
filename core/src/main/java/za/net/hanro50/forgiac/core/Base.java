package za.net.hanro50.forgiac.core;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

public class Base {
    private static Boolean lock = false;
    private static File jar;
    private static File dotMC;

    static {
        ArgsParser.Register("version", new ArgObj("Shows build information", 0, (argz) -> {
            try {
                InputStream propIs = Base.class.getResourceAsStream("/META-INF/build.txt");
                Scanner s = new Scanner(propIs);
                while (s.hasNextLine()) {
                    System.out.println(s.nextLine());
                }
                s.close();
            } catch (Throwable t) {
                System.out.println("Could not read build file");
                t.printStackTrace();
            }
            System.exit(0);
        }));

        ArgsParser.Register("installer",
                new ArgObj("The path towards the location of a forge installer jar", 1, (argz) -> {
                    jar = new File(argz[0]);
                }));
        ArgsParser.Register(".minecraft",
                new ArgObj("The path towards the location of a .minecraft directory", 1, (argz) -> {
                    dotMC = new File(argz[0]);
                }));// lock = lst.indexOf("--lock") < 0;
        ArgsParser.Register("lock", new ArgObj("Locks in the given set of values", 0, (argz) -> {
            lock = true;
        }));
    }

    public static void init(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        new ArgsParser(args);
    }

    public static void setJar(File jar) {
        if (lock)
            return;
        Base.jar = jar;
    }

    public static void setJar() {
        if (lock && jar != null)
            return;
        JFileChooser mc = new JFileChooser();
        mc.setDialogTitle("Select forge Installation jar");
        mc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        mc.showOpenDialog(null);
        jar = mc.getSelectedFile();
    }

    public static File getJar() {
        if (jar == null)
            setJar();
        return jar;
    }

    public void setDotMC(File dotMC) {
        if (lock)
            return;
        Base.dotMC = dotMC;
    }

    public static void setDotMC() {
        if (lock && dotMC != null)
            return;
        JFileChooser dotMC = new JFileChooser();
        dotMC.setDialogTitle("Set location of .Minecraft folder location");
        dotMC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dotMC.showOpenDialog(null);
        Base.dotMC = dotMC.getCurrentDirectory();
    }

    public static File getDotMC() {
        if (dotMC == null)
            setDotMC();
        return dotMC;
    }

}
