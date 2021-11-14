package za.net.hanro50.forgiac.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;

import za.net.hanro50.forgiac.core.misc.ArgObj;
import za.net.hanro50.forgiac.core.misc.LogOut;

public class Base {
    private static Boolean lock = false;
    private static File jar;
    private static File dotMC;
    public static Boolean noGui = false;
    public static Boolean standalone = false;
    private static File Manifest;

    public static Boolean isLocked() {
        return lock;
    };

    static {
        ArgsParser.Register("mk_release", new ArgObj(
                "Creates help files that allows you to release this in a repository", new String[0], (argz) -> {
                    String basePath = Base.class.getProtectionDomain().getCodeSource().getLocation().getFile();
                    File file = new File(basePath);
                    File sha1File = new File(basePath + ".sha1");
                    MessageDigest digest;
                    try {
                        digest = MessageDigest.getInstance("SHA-1");
                        InputStream fis = new FileInputStream(file);

                        int n = 0;
                        byte[] buffer = new byte[8192];
                        while (n != -1) {
                            n = fis.read(buffer);
                            if (n > 0) {
                                digest.update(buffer, 0, n);
                            }
                        }
                        fis.close();

                        if (sha1File.exists())
                            sha1File.delete();
                        sha1File.createNewFile();
                        FileWriter myWriter = new FileWriter(sha1File);
                        String out = "";
                        for (Byte bit : digest.digest()) {
                            String parsep = Integer.toHexString(bit & 0xFF);
                            out += (parsep.length() < 2 ? "0" + parsep : parsep.substring(0, 2));
                        }
                        myWriter.write(out);
                        myWriter.close();
                    } catch (NoSuchAlgorithmException | IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }));// lock = lst.indexOf("--lock") < 0;
        ArgsParser.Register("mk_manifest",
                new ArgObj("Creates a manifest file in a given directory", new String[] { "Folder" }, (argz) -> {
                    Manifest = new File(argz[0]);
                }));
        ArgsParser.Register("no_gui", new ArgObj("Disables some of the gui elements", new String[0], (argz) -> {
            noGui = true;
        }));
        ArgsParser.Register(".minecraft", new ArgObj("The path towards the location of a .minecraft directory",
                new String[] { "Folder" }, (argz) -> {
                    dotMC = new File(argz[0]);
                }));// lock = lst.indexOf("--lock") < 0;
        ArgsParser.Register("lock", new ArgObj("Locks in the given set of values", new String[0], (argz) -> {
            lock = true;
        }));
        ArgsParser.Register("version", new ArgObj("Shows build information", new String[0], (argz) -> {
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
            ExitCodes.exit(0);
        }));

        ArgsParser.Register("log",
                new ArgObj("Specifies the location of a log file", new String[] { "File" }, (argz) -> {

                    try {
                        File outPutFile = new File(argz[0], "log.txt");
                        outPutFile.createNewFile();
                        LogOut out = new LogOut(new PrintStream(new FileOutputStream(outPutFile, true), true),
                                System.out);
                        System.setOut(out.getPrintStream());
                        System.out.println("[core]: New log file created on => " + new Date().getTime());
                        System.out.println("[core]: installer: " + outPutFile.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }));

        ArgsParser.Register("installer", new ArgObj("The path towards the location of a forge installer jar",
                new String[] { "Folder" }, (argz) -> {
                    jar = new File(argz[0]);
                }));
        ArgsParser.Register(".minecraft", new ArgObj("The path towards the location of a .minecraft directory",
                new String[] { "Folder" }, (argz) -> {
                    dotMC = new File(argz[0]);
                }));// lock = lst.indexOf("--lock") < 0;
        ArgsParser.Register("lock", new ArgObj("Locks in the given set of values", new String[0], (argz) -> {
            lock = true;
        }));
    }

    public static void init(String[] args) {
ExitCodes.Init();
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
        chk(mc);
        System.out.println("[core]: installer: " + mc.getSelectedFile().getAbsolutePath());

        jar = mc.getSelectedFile();
    }

    public static File getJar() {
        if (jar == null)
            setJar();
        return jar;
    }

    public static void setDotMC(File dotMC) {
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
        chk(dotMC);
        System.out.println("[core]: .minecraft: " + dotMC.getSelectedFile().getAbsolutePath());
        Base.dotMC = dotMC.getSelectedFile();
    }

    public static File getDotMC() {
        if (dotMC == null)
            setDotMC();
        return dotMC;
    }

    public static File getManifestFolder() {
        return Manifest;
    }

    private static void chk(JFileChooser fj) {
        if (fj.getSelectedFile() == null) {
            ExitCodes.exit(200);
        }
    }

}
