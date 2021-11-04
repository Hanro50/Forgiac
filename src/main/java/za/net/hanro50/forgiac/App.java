package za.net.hanro50.forgiac;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;


/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        
        JFileChooser filesjooser = new JFileChooser();
        filesjooser.showOpenDialog(null);
        new Install(filesjooser.getSelectedFile());
        System.out.println("Hello World!");
    }
}
