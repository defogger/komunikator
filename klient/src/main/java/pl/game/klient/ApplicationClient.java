package pl.game.klient;

import Message.src.Chat;
import Message.src.Introduction;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ApplicationClient extends Application {

    Client client;
    String nick;

    HBox container = new HBox();
    VBox left = new VBox();
    BorderPane textIP = new BorderPane();
    TextArea setIP = new TextArea();

    BorderPane textNick = new BorderPane();
    TextArea setNick = new TextArea();
    Button connectButton = new Button("Połącz");
    TextArea users = new TextArea();
    VBox right = new VBox();
    TextArea chat = new TextArea();
    TextArea setMessage = new TextArea();
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Client");
        stage.setScene(getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    Scene getScene()
    {
        container.setPrefSize(600,300);

        left.setPrefSize(300,300);
        left.setAlignment(Pos.CENTER);
        left.setPadding(new Insets(20));
        left.setSpacing(10);

        textIP.setPrefSize(50,10);
        textIP.setCenter(new Text("IP: "));

        setIP.setPrefSize(250,10);
        setIP.setText("localhost");

        textNick.setPrefSize(50,10);
        textNick.setCenter(new Text("Nick: "));

        setNick.setPrefSize(250,10);
        setNick.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER))
                connect();
        });

        connectButton.setPrefSize(100,20);
        connectButton.setOnAction(e->connect());

        users.setPrefSize(300,200);
        users.setEditable(false);
        users.setCursor(Cursor.DEFAULT);
        users.setDisable(true);

        HBox boxIP = new HBox();
        boxIP.getChildren().addAll(textIP,setIP);

        HBox boxNick = new HBox();
        boxNick.getChildren().addAll(textNick,setNick);

        left.getChildren().addAll(boxIP,boxNick,connectButton,users);

        right.setPrefSize(300,300);
        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(20));

        chat.setPrefSize(250,250);
        chat.setEditable(false);
        chat.setWrapText(true);
        chat.setCursor(Cursor.DEFAULT);

        setMessage.setPrefSize(50,50);
        setMessage.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER))
            {
                String info = setMessage.getText();

                if(info.length() > 1 && client != null)
                    client.sendObject(new Chat(nick,info));

                setMessage.setText("");
            }
        });

        right.getChildren().addAll(chat,setMessage);

        container.getChildren().addAll(left,right);

        scene = new Scene(container);

        return scene;
    }

    void connect()
    {
        if(client != null && client.isConnected)
            return;

        String ip = setIP.getText();
        String n = setNick.getText();
        n = n.replace("\n","");
        nick = n;

        client = new Client(ip);

        if(client.isConnected)
        {
            setIP.setDisable(true);
            setNick.setDisable(true);
            client.setChat(chat);
            client.setUsersTextArea(users);
            client.sendObject(new Introduction(nick));
        }
        else
        {
            client = null;
        }
    }
}
