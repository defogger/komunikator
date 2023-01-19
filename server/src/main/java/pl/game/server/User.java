package pl.game.server;

import Message.src.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class User
{
    String nick;
    Server server;
    Thread receiveMessage;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    LocalDateTime lastMessage = LocalDateTime.now();

    public User(Socket s,Server s1)
    {
        socket = s;
        server = s1;

        try
        {
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
        }
        catch(Exception ignore){}

        sendMessage(new ListUsers(server.getListUsers()));

        setReceiveMessage();
    }

    void setReceiveMessage()
    {
        receiveMessage = new Thread(()->{receiveMessage();});
        receiveMessage.setDaemon(true);
        receiveMessage.start();
    }

    public void sendMessage(Object o)
    {
        try
        {
            out.writeObject(o);
        }
        catch (Exception ignore){}
    }

    void receiveMessage()
    {
        while (true)
        {
            try
            {
                Object o = in.readObject();

                if(o instanceof Message)
                {
                    if(o instanceof Pulse)
                        lastMessage = LocalDateTime.now();
                    else if(o instanceof Introduction)
                    {
                        nick = ((Introduction) o).nick;
                        server.setMessage((Message) o);
                    }
                    else
                        server.setMessage((Message) o);
                }
                else
                {
                    //log strange Object
                }
            }
            catch (Exception ignore){}
        }
    }
}
