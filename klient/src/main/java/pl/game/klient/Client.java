package pl.game.klient;

import Message.src.*;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Client {

    Socket socet;
    ObjectInputStream serverIn;
    ObjectOutputStream serverOut;

    ReceiveMessage receiveStatus;

    LocalDateTime lastMessage = LocalDateTime.now();

    Vector<String> users = new Vector<>();
    Vector<Pair<String,String>> conversation = new Vector<>();
    TextArea usersTextArea;
    TextArea chatTextArea;

    public Boolean isConnected = false;
    Thread checkConnection;

    public Client(String ip)
    {
        try
        {
            socet = new Socket(ip,2137);
            serverIn = new ObjectInputStream(socet.getInputStream());
            serverOut = new ObjectOutputStream(socet.getOutputStream());
        }
        catch (Exception ignore){}

        isConnected = true;

        startCheckConnection();

        receiveStatus = new ReceiveMessage(this,serverIn);
        receiveStatus.start();
    }

    void startCheckConnection()
    {
        Thread checkConnection = new Thread(()->CheckConnection());
        checkConnection.setDaemon(true);
        checkConnection.start();
    }

    void CheckConnection()
    {
        while (true)
        {
            synchronized (lastMessage)
            {
                if(lastMessage.plusSeconds(10).compareTo(LocalDateTime.now()) < 0)
                {
                    isConnected = false;
                    conversation.add(new Pair<>("ERROR","Conection lost with server"));
                    UpdateUsersGUI();
                    break;
                }

            }

            try
            {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (Exception e){}
        }
    }

    public void setUsersTextArea(TextArea t)
    {
        usersTextArea = t;
    }

    synchronized String getUsersTextArea()
    {
        StringBuilder out = new StringBuilder();
        for(String u:users)
            out.append(u+"\n");
        return out.toString();
    }

    public void setChat(TextArea t)
    {
        chatTextArea = t;
    }

    synchronized String getChatText()
    {
        StringBuilder out = new StringBuilder();
        for(Pair<String,String> p:conversation)
            out.append(p.getKey()+": "+p.getValue());

        return out.toString();
    }

    public synchronized void setMessage(Message m)
    {
        if(m instanceof Pulse)
        {
            synchronized (lastMessage)
            {
                lastMessage = LocalDateTime.now();
            }
            sendObject(m);
        }
        else if(m instanceof Chat)
        {
            Chat chat = (Chat) m;
            conversation.add(new Pair<>(chat.from, chat.info));

            UpdateUsersGUI();
        }
        else if(m instanceof Introduction)
        {
            Introduction i = (Introduction) m;
            users.add(i.nick);

            UpdateUsersGUI();
        }
        else if(m instanceof ListUsers)
        {
            ListUsers l = (ListUsers) m;
            users = l.users;

            UpdateUsersGUI();
        }
    }

    void UpdateUsersGUI()
    {
        Platform.runLater(() -> {

            usersTextArea.setText(getUsersTextArea());

            chatTextArea.setText(getChatText());
        });
    }

    public void sendObject(Object o)
    {
        try
        {
            serverOut.writeObject(o);
        }
        catch (Exception e){}
    }
}
