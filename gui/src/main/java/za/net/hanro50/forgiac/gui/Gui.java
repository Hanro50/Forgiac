package za.net.hanro50.forgiac.gui;

import java.net.URL;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import netscape.javascript.JSObject;
import za.net.hanro50.forgiac.core.Base;

public class Gui extends Application {
    static WebView webView = new WebView();
    static WebEngine webEngine = webView.getEngine();
    static VBox vBox = new VBox(webView);
    static Scene scene = new Scene(vBox, 960, 600);
   
    /** for communication to the Javascript engine. */
    private static JSObject javascriptConnector;

    /** for communication from the Javascript engine. */
    private JavaConnector javaConnector = new JavaConnector();;

    public static void boot(String[] args) {
        Base.init(args);
        launch(args);
    }

    public static void loadPage(String page) {
        URL url = Gui.class.getClass().getResource("/" + page + ".html");
        webEngine.load(url.toString());
    }

    @Override
    public void start(Stage primaryStage) {
        loadPage("index");
        primaryStage.setScene(scene);
        primaryStage.show();

        // set up the listener
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                // set an interface object named 'javaConnector' in the web engine's page
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", javaConnector);

                // get the Javascript connector object.
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
            }
        });
    }

    public static class JavaConnector {
        /**
         * called when the JS side wants a String to be converted.
         *
         * @param value the String to convert
         */
        public void toLowerCase(String value) {
            if (null != value) {
                javascriptConnector.call("showResult", value.toLowerCase());
            }
        }

    }

}