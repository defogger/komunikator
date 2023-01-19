package Message.src;

public class Chat extends Message {
    public String from;
    public String info;

    public Chat(String f,String i)
    {
        from = f;
        info = i;
    }

    public String toString()
    {
        return from+": "+info;
    }
}
