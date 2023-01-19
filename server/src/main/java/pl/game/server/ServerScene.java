package pl.game.server;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerScene {
    public static Stage stage = new Stage();
    public static Scene scene;
    public static VBox container = new VBox();
    public static Label infoUsers = new Label();
    public static TextArea users = new TextArea();

    static
    {
        container.setPrefSize(300,300);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));

        infoUsers.setPrefSize(200,50);
        infoUsers.setText("Użytkownicy (?)");
        infoUsers.setPadding(new Insets(10));
        infoUsers.setAlignment(Pos.CENTER);

        users.setPrefSize(300,250);
        users.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        users.setPadding(new Insets(10));
        users.setEditable(false);
        users.setDisable(true);
        users.setCursor(Cursor.DEFAULT);

        container.getChildren().addAll(infoUsers, users);

        scene = new Scene(container);
    }
}
