package classes;

public class Mostsold
{
String productname ;
String count;


public  Mostsold(String productname,String count)
{

	this.productname = productname;
    this.count = count;
}


public String getProductname(){
 return productname;
}

public String getCount () {
 return count;
}
}