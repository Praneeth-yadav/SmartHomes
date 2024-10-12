package classes;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Startup")

/*  
startup servlet Intializes HashMap in SaxParserDataStore
*/

public class Startup extends HttpServlet
{

	public void init() throws ServletException
    {
		// System.out.println("before hashmap");
		SaxParserDataStore.addHashmap();
		//System.out.println("after hashmap");
		MySqlDataStoreUtilities.Insertproducts();
		MySqlDataStoreUtilities.Insertstores();
	

    }
}
