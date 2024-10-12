package classes;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet("/OrdersHashMap")

public class OrdersHashMap extends HttpServlet{

	public static HashMap<String, ArrayList<OrderItem>> orders = new HashMap<>();

	public OrdersHashMap() {

	}
}
