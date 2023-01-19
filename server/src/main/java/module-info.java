module pl.game.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.game.server to javafx.fxml;
    exports pl.game.server;
}