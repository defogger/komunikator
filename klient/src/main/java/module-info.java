module pl.game.klient {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.game.klient to javafx.fxml;
    exports pl.game.klient;
    exports Message.src;
    opens Message.src to javafx.fxml;
}