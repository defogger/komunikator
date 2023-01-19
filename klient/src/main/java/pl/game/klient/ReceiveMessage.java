package pl.game.klient;

import Message.src.Message;

import java.io.ObjectInputStream;

public class ReceiveMessage extends Thread
{
    Client client;
    ObjectInputStream serverIn;

    public ReceiveMessage(Client c,ObjectInputStream in)
    {
        setDaemon(true);

        client = c;
        serverIn = in;
    }

    public void run() {
        while (true)
        {
            try
            {
                Object readObject = serverIn.readObject();
                if(readObject instanceof Message)
                {
                    Message m = (Message) readObject;

                    client.setMessage(m);
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
