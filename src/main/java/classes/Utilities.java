package classes;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet("/Utilities")

/*
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.

*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument,
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				if(((String) session.getAttribute("usertype")).equalsIgnoreCase("manager"))
				{
					result = result + "<li><a href='ProductModify?button=Addproduct'><span class='glyphicon'>Addproduct</span></a></li>"
						+ "<li><a href='ProductModify?button=Updateproduct'><span class='glyphicon'>Updateproduct</span></a></li>"
						+"<li><a href='ProductModify?button=Deleteproduct'><span class='glyphicon'>Deleteproduct</span></a></li>"
						+"<li><a href='DataVisualization'><span class='glyphicon'>Trending</span></a></li>"
						+"<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}

				else if(((String) session.getAttribute("usertype")).equalsIgnoreCase("retailer")){
					result = result + "<li><a href='Registration'><span class='glyphicon'>Create Customer</span></a></li>"
						+ "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				else
				{
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			    }
			} else {
				result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
			}
				result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
				pw.print(result);
		} else {
			pw.print(result);
		}
	}


	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		}
		catch (Exception e) {
		}
		return result;
	}

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}

	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null) {
			return false;
		}
		return true;
	}

	/*  username Function returns the username from the session variable.*/

	public String username(){
		if (session.getAttribute("username")!=null) {
			return session.getAttribute("username").toString();
		}
		return null;
	}

	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null) {
			return session.getAttribute("usertype").toString();
		}
		return null;
	}

	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<>();
		try
		{
			hm=MySqlDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{
			System.out.println("Exception utility get user"+e);

		}
		User user = hm.get(username());
		return user;
	}

	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<>();
		if(OrdersHashMap.orders.containsKey(username())) {
			order= OrdersHashMap.orders.get(username());
		}
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();
		int size=0;
		try
		{
			orderPayments =MySqlDataStoreUtilities.selectOrder();

		}
		catch(Exception e)
		{
			System.out.println("Exception utility getOrderPaymentSize"+e);


		}
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
				size=entry.getKey();
		}

		return size;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin()) {
			return getCustomerOrders().size();
		}
		return 0;
	}

	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){
			ArrayList<OrderItem> arr = new ArrayList<>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		HashMap<String,Console> allconsoles = new HashMap<> ();
			HashMap<String,Tablet> alltablets = new HashMap<> ();
			HashMap<String,Game> allgames = new HashMap<> ();
			HashMap<String,Lightning> alllights = new HashMap<> ();
			HashMap<String,Thermostat> allthermos = new HashMap<> ();
			HashMap<String,Accessory> allaccessories=new HashMap<>();
		if(type.equalsIgnoreCase("consoles")){
			Console console;
			try{
			allconsoles = MySqlDataStoreUtilities.getConsoles();

			}
			catch(Exception e){
				System.out.println("Exception utility storeProduct1"+e);

			}
			console = allconsoles.get(name);
			System.out.println("out"+console);
			OrderItem orderitem = new OrderItem(console.getName(), console.getPrice(), console.getImage(), console.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equalsIgnoreCase("games")){
			Game game = null;
			try{
			allgames = MySqlDataStoreUtilities.getGames();
			}
			catch(Exception e){
				System.out.println("Exception utility storeProduct2"+e);

			}
			game = allgames.get(name);
			OrderItem orderitem = new OrderItem(game.getName(), game.getPrice(), game.getImage(), game.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equalsIgnoreCase("tablets")){
			Tablet tablet = null;
			try{
			alltablets = MySqlDataStoreUtilities.getTablets();
			}
			catch(Exception e){
				System.out.println("Exception utility storeProduct3"+e);

			}
			tablet = alltablets.get(name);
			OrderItem orderitem = new OrderItem(tablet.getName(), tablet.getPrice(), tablet.getImage(), tablet.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equalsIgnoreCase("lightnings")){
			Lightning lightning = null;
			try{
			alllights = MySqlDataStoreUtilities.getLightnings();
			}
			catch(Exception e){
				System.out.println("Exception utility storeProduct4"+e);

			}
			lightning = alllights.get(name);
			OrderItem orderitem = new OrderItem(lightning.getName(), lightning.getPrice(), lightning.getImage(), lightning.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equalsIgnoreCase("thermostats")){
			Thermostat thermostat = null;
			try{
			allthermos = MySqlDataStoreUtilities.getThermostats();
			}
			catch(Exception e){
				System.out.println("Exception utility storeProduct5"+e);

			}
			thermostat = allthermos.get(name);
			OrderItem orderitem = new OrderItem(thermostat.getName(), thermostat.getPrice(), thermostat.getImage(), thermostat.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equalsIgnoreCase("accessories")){
			try{
			allaccessories = MySqlDataStoreUtilities.getAccessories();
			}
			catch(Exception e){
				System.out.println("Exception utility storeProduct6"+e);

			}
			Accessory accessory = allaccessories.get(name);
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer());
			orderItems.add(orderitem);
		}

	}
	// store the payment details for orders
	public void storePayment(int orderId,String customer,String userAddress,String creditCardNo,
		String orderName,double orderPrice,String storeAddress,String storeId){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<>();
			// get the payment details file
		try
		{
			orderPayments=MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{
			System.out.println("Exception utility storePayment1"+e);

		}
		if(orderPayments==null)
		{
			orderPayments = new HashMap<>();
		}
			// if there exist order id already add it into same list for order id or create a new record with order id

		if(!orderPayments.containsKey(orderId)){
			ArrayList<OrderPayment> arr = new ArrayList<>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);

		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);

			// add order details into database
		try
		{	if(((String) session.getAttribute("usertype")).equalsIgnoreCase("retailer"))
			{
				MySqlDataStoreUtilities.insertOrder(orderId,customer,customer,orderName,orderPrice,userAddress,creditCardNo,storeAddress,storeId);
			}else

				{MySqlDataStoreUtilities.insertOrder(orderId,username(),customer,orderName,orderPrice,userAddress,creditCardNo,storeAddress,storeId);}
		}
		catch(Exception e)
		{
			System.out.println("inside exception file not written properly");
		}
	}
     public String storeReview(String productname,String producttype,String productprice,String productmaker,String reviewrating,String storeid,String storecity,String storestate,String storezip,String productsale,String rebate,String userid,String age,String gender,String occupation,String reviewdate,String  reviewtext){
	String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productprice,productmaker,reviewrating,storeid,storecity,storestate,storezip,productsale,rebate,userid,age,gender,occupation,reviewdate,reviewtext);
	System.out.println("message"+message);	
	if(!message.equalsIgnoreCase("Successfull"))
		{ return "UnSuccessfull";
		}
		else
		{
		HashMap<String, ArrayList<Review>> reviews= new HashMap<>();
		try
		{
			reviews=MongoDBDataStoreUtilities.selectReview();
		}
		catch(Exception e)
		{
			System.out.println("Exception storeReview "+e);


		}
		if(reviews==null)
		{
			reviews = new HashMap<>();
		}
			// if there exist product review already add it into same list for productname or create a new record with product name

		if(!reviews.containsKey(productname)){
			ArrayList<Review> arr = new ArrayList<>();
			reviews.put(productname, arr);
		}
		ArrayList<Review> listReview = reviews.get(productname);
		Review review = new Review(productname,username(),producttype,productprice,productmaker,reviewrating,storeid,storecity,storestate,storezip,productsale,rebate,userid,age,gender,occupation,reviewdate,reviewtext);
		listReview.add(review);

			// add Reviews into database

		return "Successfull";
		}
	}

	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Console> getConsoles(){
			HashMap<String, Console> hm = new HashMap<>();
			hm.putAll(SaxParserDataStore.consoles);
			return hm;
	}

	/* getGames Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, Game> getGames(){
			HashMap<String, Game> hm = new HashMap<>();
			hm.putAll(SaxParserDataStore.games);
			return hm;
	}

	/* getTablets Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Tablet> getTablets(){
			HashMap<String, Tablet> hm = new HashMap<>();
			hm.putAll(SaxParserDataStore.tablets);
			return hm;
	}

	/* getLightnings Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Lightning> getLightnings(){
			HashMap<String, Lightning> hm = new HashMap<>();
			hm.putAll(SaxParserDataStore.lightnings);
			return hm;
	}

	/* getThermostats Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Thermostat> getThermostats(){
			HashMap<String, Thermostat> hm = new HashMap<>();
			hm.putAll(SaxParserDataStore.thermostats);
			return hm;
	}



	/* getProducts Functions returns the Arraylist of consoles in the store.*/

	public ArrayList<String> getProducts(){
		ArrayList<String> ar = new ArrayList<>();
		for(Map.Entry<String, Console> entry : getConsoles().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of games in the store.*/

	public ArrayList<String> getProductsGame(){
		ArrayList<String> ar = new ArrayList<>();
		for(Map.Entry<String, Game> entry : getGames().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductsTablets(){
		ArrayList<String> ar = new ArrayList<>();
		for(Map.Entry<String, Tablet> entry : getTablets().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of Lightnings in the store.*/

	public ArrayList<String> getProductsLightnings(){
		ArrayList<String> ar = new ArrayList<>();
		for(Map.Entry<String, Lightning> entry : getLightnings().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of Thermostats in the store.*/

	public ArrayList<String> getProductsThermostats(){
		ArrayList<String> ar = new ArrayList<>();
		for(Map.Entry<String, Thermostat> entry : getThermostats().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}



}
