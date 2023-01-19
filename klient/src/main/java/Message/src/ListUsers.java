package Message.src;

import java.util.Vector;

public class ListUsers extends Message{
    public Vector<String> users;

   public ListUsers(Vector<String> u)
   {
       users = u;
   }
}
