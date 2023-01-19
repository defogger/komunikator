package pl.game.server;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationServer extends Application {

    Server server;

    @Override
    public void start(Stage stage) throws Exception {

        server = new Server();

        stage.setTitle("Server");
        stage.setScene(ServerScene.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
