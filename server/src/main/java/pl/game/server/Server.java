package pl.game.server;

import javafx.application.Platform;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import Message.src.*;
public class Server {
    ServerSocket serverSocket;
    Thread acceptClient;
    Thread checkConnection;
    Vector<User> users = new Vector<>();

    public Server()
    {
        try
        {
            serverSocket = new ServerSocket(2137);
        }
        catch (Exception ignore){}

        startCheckConnection();
        startAcceptClient();
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
            synchronized (users)
            {
                for(User u:users)
                    u.sendMessage(new Pulse());

                users.removeIf(u->{
                    LocalDateTime dataTime = u.lastMessage.plusSeconds(10);
                    if(dataTime.compareTo(LocalDateTime.now()) < 0)
                    {
                        System.out.println("Conection lost with "+u.nick);
                        return true;
                    }
                    return false;
                });

                for(User u:users)
                    u.sendMessage(new ListUsers(getListUsers()));

                UpdateUsersGUI();
            }

            try
            {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (Exception e){}
        }
    }

    void startAcceptClient()
    {
        acceptClient = new Thread(()->Accept());
        acceptClient.setDaemon(true);
        acceptClient.start();
    }

    public void Accept()
    {
        try
        {
            while(true)
            {
                Socket s = serverSocket.accept();

                synchronized(users)
                {
                    User u = new User(s,this);
                    users.add(u);
                }
            }
        }
        catch (Exception ignore){}
    }

    void UpdateUsersGUI()
    {
        Platform.runLater(() -> {
            ServerScene.infoUsers.setText("Użytkownicy ("+ users.size()+")");

            StringBuilder out = new StringBuilder();

            for(User u:users)
                out.append(u.nick+"\n");

            ServerScene.users.setText(out.toString());
        });
    }

    public void setMessage(Message m)
    {
        synchronized (users)
        {
            if(m instanceof Chat)
            {
                for(User u:users)
                    u.sendMessage(m);
            }
            else if(m instanceof Introduction)
            {
                for(User u:users)
                    u.sendMessage(m);

                UpdateUsersGUI();
            }
        }
    }

    public Vector<String> getListUsers()
    {
        Vector<String> out = new Vector<>();

        synchronized (users)
        {
            for(User u:users)
                out.add(u.nick);
        }

        return out;
    }
}