package classes;
import java.io.*;
public class Bestrating
{
    String productname ;
    String rating;


    public  Bestrating(String productname,String rating)
    {
       
       this.productname = productname ;
       this.rating = rating;
   }


   public String getProductname(){
       return productname;
   }

   public String getRating () {
       return rating;
   }

   public String toString(){
        return " Product name is: "+productname+"rating is: "+rating;

   }
}