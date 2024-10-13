package classes;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MySqlDataStoreUtilities
{
	static Connection conn = null;
	static String message;
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/smarthomesdb?serverTimezone=UTC","root","Nithin@136");							
			message="Successfull";
			System.out.println("Success");
			return message;
		}
		catch(SQLException e)
		{
			message="unsuccessful";
			System.out.println(e);
			return message;
		}
		catch(Exception e)
		{
			message=e.getMessage();
			return message;
		}
	}

	public static void Insertproducts()
	{
		try{
			
			
			getConnection();
			
			
			String truncatetableacc = "delete from Product_accessories;";
			PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
			pstt.executeUpdate();
			
			String truncatetableprod = "delete from  Productdetails;";
			PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
			psttprod.executeUpdate();
			
			
			
			String insertProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productOnSale,productQuantity)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?);";
			for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
			{   
				String name = "accessories";
				Accessory acc = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,acc.getId());
				pst.setString(3,acc.getName());
				pst.setDouble(4,acc.getPrice());
				pst.setString(5,acc.getImage());
				pst.setString(6,acc.getRetailer());
				pst.setString(7,acc.getCondition());
				pst.setDouble(8,acc.getDiscount());
				pst.setString(9,acc.getproductOnSale());
				//System.out.println("TEST"+acc.getproductOnSale());
				pst.setInt(10,acc.getproductQuantity());
				
				pst.executeUpdate();
				
				
			}
			
			for(Map.Entry<String,Console> entry : SaxParserDataStore.consoles.entrySet())
			{   
				Console con = entry.getValue();
				String name = "doorbells";
				
				
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,con.getId());
				pst.setString(3,con.getName());
				pst.setDouble(4,con.getPrice());
				pst.setString(5,con.getImage());
				pst.setString(6,con.getRetailer());
				pst.setString(7,con.getCondition());
				pst.setDouble(8,con.getDiscount());
				pst.setString(9,con.getproductOnSale());
				pst.setInt(10,con.getproductQuantity());
				
				pst.executeUpdate();
				try{
					HashMap<String,String> acc = con.getAccessories();
					String insertAccessoryQurey = "INSERT INTO  Product_accessories(productName,accessoriesName)" +
					"VALUES (?,?);";
					for(Map.Entry<String,String> accentry : acc.entrySet())
					{
						PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
						pstacc.setString(1,con.getId());
						pstacc.setString(2,accentry.getValue());
						pstacc.executeUpdate();
					}
				}catch(Exception et){
					et.printStackTrace();
				}
			}
			for(Map.Entry<String,Game> entry : SaxParserDataStore.games.entrySet())
			{   
				// response.setContentType("text/html");
				// PrintWriter pw = response.getWriter();
				String name = "doorlocks";
				Game game = entry.getValue();
				System.out.print(game.getName());
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,game.getId());
				pst.setString(3,game.getName());
				pst.setDouble(4,game.getPrice());
				pst.setString(5,game.getImage());
				pst.setString(6,game.getRetailer());
				pst.setString(7,game.getCondition());
				pst.setDouble(8,game.getDiscount());
				pst.setString(9,game.getproductOnSale());
				pst.setInt(10,game.getproductQuantity());
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Tablet> entry : SaxParserDataStore.tablets.entrySet())
			{   
				String name = "speakers";
				Tablet tablet = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,tablet.getId());
				pst.setString(3,tablet.getName());
				pst.setDouble(4,tablet.getPrice());
				pst.setString(5,tablet.getImage());
				pst.setString(6,tablet.getRetailer());
				pst.setString(7,tablet.getCondition());
				pst.setDouble(8,tablet.getDiscount());
				pst.setString(9,tablet.getproductOnSale());
				pst.setInt(10,tablet.getproductQuantity());
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Lightning> entry : SaxParserDataStore.lightnings.entrySet())
			{   
				String name = "lights";
				Lightning light = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,light.getId());
				pst.setString(3,light.getName());
				pst.setDouble(4,light.getPrice());
				pst.setString(5,light.getImage());
				pst.setString(6,light.getRetailer());
				pst.setString(7,light.getCondition());
				pst.setDouble(8,light.getDiscount());
				pst.setString(9,light.getproductOnSale());
				pst.setInt(10,light.getproductQuantity());
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Thermostat> entry : SaxParserDataStore.thermostats.entrySet())
			{   
				String name = "thermos";
				Thermostat thermo = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,thermo.getId());
				pst.setString(3,thermo.getName());
				pst.setDouble(4,thermo.getPrice());
				pst.setString(5,thermo.getImage());
				pst.setString(6,thermo.getRetailer());
				pst.setString(7,thermo.getCondition());
				pst.setDouble(8,thermo.getDiscount());
				pst.setString(9,thermo.getproductOnSale());
				pst.setInt(10,thermo.getproductQuantity());
				
				pst.executeUpdate();
				
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	} 

	public static HashMap<String,Console> getConsoles()
	{	
		HashMap<String,Console> hm=new HashMap<String,Console>();
		try 
		{
			getConnection();
			
			String selectConsole="select * from  Productdetails where ProductType=?";
			PreparedStatement pst = conn.prepareStatement(selectConsole);
			pst.setString(1,"doorbells");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
				{	Console con = new Console(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			hm.put(rs.getString("Id"), con);
			con.setId(rs.getString("Id"));
			
			try
			{
				String selectaccessory = "Select * from Product_accessories where productName=?";
				PreparedStatement pstacc = conn.prepareStatement(selectaccessory);
				pstacc.setString(1,rs.getString("Id"));
				ResultSet rsacc = pstacc.executeQuery();
				
				HashMap<String,String> acchashmap = new HashMap<String,String>();
				while(rsacc.next())
				{    
					if (rsacc.getString("accessoriesName") != null){
						
						acchashmap.put(rsacc.getString("accessoriesName"),rsacc.getString("accessoriesName"));
						con.setAccessories(acchashmap);
					}
					
				}					
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Tablet> getTablets()
{	
	HashMap<String,Tablet> hm=new HashMap<String,Tablet>();
	try 
	{
		getConnection();
		
		String selectTablet="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectTablet);
		pst.setString(1,"speakers");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Tablet tab = new Tablet(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
		hm.put(rs.getString("Id"), tab);
		tab.setId(rs.getString("Id"));

	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Game> getGames()
{	
	HashMap<String,Game> hm=new HashMap<String,Game>();
	try 
	{
		getConnection();
		
		String selectGame="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectGame);
		pst.setString(1,"doorlocks");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Game game = new Game(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
		hm.put(rs.getString("Id"), game);
		game.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Lightning> getLightnings()
{	
	HashMap<String,Lightning> hm=new HashMap<String,Lightning>();
	try 
	{
		getConnection();
		
		String selectLightning="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectLightning);
		pst.setString(1,"lights");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Lightning light = new Lightning(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
		hm.put(rs.getString("Id"), light);
		light.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Thermostat> getThermostats()
{	
	HashMap<String,Thermostat> hm=new HashMap<String,Thermostat>();
	try 
	{
		getConnection();
		
		String selectThermostat="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectThermostat);
		pst.setString(1,"thermos");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Thermostat thermo = new Thermostat(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
		hm.put(rs.getString("Id"), thermo);
		thermo.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Accessory> getAccessories()
{	
	HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
	try 
	{
		getConnection();
		
		String selectAcc="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		pst.setString(1,"accessories");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Accessory acc = new Accessory(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
		hm.put(rs.getString("Id"), acc);
		acc.setId(rs.getString("Id"));

	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Store> getStoreLocations()
{	
	HashMap<String,Store> hm=new HashMap<String,Store>();
	try 
	{
		getConnection();
		
		String stores="select * from  Stores";
		PreparedStatement pst = conn.prepareStatement(stores);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
		{	
			Store loc = new Store(rs.getString("name"),rs.getString("street"),rs.getString("city"),rs.getString("state"),rs.getString("zip"));
			hm.put(rs.getString("storeId"), loc);
			loc.setId(rs.getString("storeId"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static void Insertstores(){
	try{
			
			
			getConnection();
			
			
			String truncatetablestore = "delete from  Stores;";
			PreparedStatement psttstore = conn.prepareStatement(truncatetablestore);
			psttstore.executeUpdate();

			String insertStoreQuery = "INSERT INTO  Stores(storeId,name,street,city,state,zip)" +
			"VALUES (?,?,?,?,?,?);";
			for(Map.Entry<String,Store> entry : SaxParserDataStore.stores.entrySet())
			{   
				Store store = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertStoreQuery);
				pst.setString(1,store.getId());
				pst.setString(2,store.getId());
				pst.setString(3,store.getStreet());
				pst.setString(4,store.getCity());
				pst.setString(5,store.getState());
				pst.setString(6,store.getZip());
				
				pst.executeUpdate();
				
				
			}
			}
	catch(Exception e)
		{
			e.printStackTrace();
		}
	}

public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String productOnSale,int productQuantity,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productOnSale,productQuantity)" +
		"VALUES (?,?,?,?,?,?,?,?,?,?);";
		
		String name = producttype;
		
		PreparedStatement pst = conn.prepareStatement(addProductQurey);
		pst.setString(1,name);
		pst.setString(2,productId);
		pst.setString(3,productName);
		pst.setDouble(4,productPrice);
		pst.setString(5,productImage);
		pst.setString(6,productManufacturer);
		pst.setString(7,productCondition);
		pst.setDouble(8,productDiscount);
		pst.setString(9,productOnSale);
		pst.setInt(10,productQuantity);
		
		pst.executeUpdate();
		try{
			if (!prod.isEmpty())
			{
				String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
				"VALUES (?,?);";
				PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
				pst1.setString(1,prod);
				pst1.setString(2,productId);
				pst1.executeUpdate();
				
			}
		}catch(Exception e)
		{
			msg = "Erro while adding the product";
			e.printStackTrace();
			
		}
		
		
		
	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();
		
	}
	return msg;
}
public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String productOnSale, int productQuantity)
{ 
	String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=?,productOnSale=?,productQuantity=? where Id =?;" ;
		
		
		
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		
		pst.setString(1,productName);
		pst.setDouble(2,productPrice);
		pst.setString(3,productImage);
		pst.setString(4,productManufacturer);
		pst.setString(5,productCondition);
		pst.setDouble(6,productDiscount);
		pst.setString(7,productOnSale);
		pst.setInt(8,productQuantity);
		pst.setString(9,productId);
		pst.executeUpdate();
		
		
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
	return msg;	
}

public static HashMap<String,Inventory> getDailyTransactions()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectDailyTransactionsQurey="select purchaseDate,sum(orderPrice) as totalSales from CustomerOrders group by purchaseDate";
		ResultSet rs = stmt.executeQuery(selectDailyTransactionsQurey);
		
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("purchaseDate"),rs.getDouble("totalSales"));
			
				hm.put(rs.getString("purchaseDate"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static HashMap<String,Inventory> getSoldProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectSoldProductsQurey="select orderName,MAX(orderPrice) as orderPrice,COUNT(orderName) as itemsSold,MAX(orderPrice) * COUNT(orderName) as totalSales FROM CustomerOrders GROUP BY orderName;";
		ResultSet rs = stmt.executeQuery(selectSoldProductsQurey);
		
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getInt("itemsSold"),rs.getDouble("totalSales"));
			
				hm.put(rs.getString("orderName"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

public static HashMap<String,Inventory> getAllProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductQurey="Select * from Productdetails";
		ResultSet rs = stmt.executeQuery(selectProductQurey);
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			
				hm.put(rs.getString("ProductType"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static HashMap<String,Inventory> getOnRebateProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductQurey="select * from Productdetails where productDiscount <>0";
		ResultSet rs = stmt.executeQuery(selectProductQurey);
	
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			
				hm.put(rs.getString("ProductType"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static HashMap<String,Inventory> getOnSaleProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductQurey="select * from Productdetails where productOnSale='YES';";
		ResultSet rs = stmt.executeQuery(selectProductQurey);
	
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			
				hm.put(rs.getString("ProductType"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}


public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
try
{
	
	getConnection();
	String deleteproductsQuery ="Delete from Productdetails where Id=?";
	PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
	pst.setString(1,productId);
	
	pst.executeUpdate();
}
catch(Exception e)
{
	msg = "Proudct cannot be deleted";
}
return msg;
}

public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
		
	}
}

public static void insertOrder(int orderId,String userName,String customerName,String orderName,double orderPrice,String userAddress,String creditCardNo,String storeAddress,String storeId)
{
	LocalDateTime currentDateTime = LocalDateTime.now();
	LocalDateTime futureDateTime = currentDateTime.plus(20, ChronoUnit.DAYS);
	Timestamp timestamp = Timestamp.valueOf(futureDateTime);
	try
	{
		
		getConnection();
		
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,userName,customerName,customerAddress,creditCardNo,purchaseDate,shipDate,orderName,productCategory,quantity,orderPrice,shippingCost,discount,totalSales,storeId,storeAddress) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";	
		
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,customerName);
		pst.setString(4,userAddress);
		pst.setString(5,creditCardNo);
		pst.setTimestamp(6,java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
		pst.setTimestamp(7,timestamp);
		pst.setString(8,orderName);
		pst.setString(9,"smartHome");
		pst.setInt(10,1);
		pst.setDouble(11,orderPrice);
		pst.setDouble(12,30.0);
		pst.setDouble(13,10.0);
		pst.setInt(14,2);
		pst.setString(15,storeId);
		pst.setString(16,storeAddress);
		pst.execute();
	}
	catch(Exception e)
	{
		
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
	
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("customerAddress"),rs.getString("creditCardNo"));
			listOrderPayment.add(order);
			
		}
		
		
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String usertype)
{
	try
	{	

		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
		+ "VALUES (?,?,?,?);";	
		
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
		
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
			{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
		hm.put(rs.getString("username"), user);
	}
}
catch(Exception e)
{
}
return hm;			
}


}	