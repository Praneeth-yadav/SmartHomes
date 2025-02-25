package classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class AjaxUtility {
	StringBuffer sb = new StringBuffer();
	boolean namesAdded = false;
	static Connection conn = null;
    static String message;
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/smarthomesdb?serverTimezone=UTC","root","Nithin@136");
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			 message="unsuccessful";
		     return message;
		}
		catch(Exception e)
		{
			System.out.println("Exception Ajax getConnection"+e);

			 message="unsuccessful";
		     return message;
		}
	}

	public  StringBuffer readdata(String searchId)
	{
		HashMap<String,Product> data;
		data=getData();

 	    Iterator it = data.entrySet().iterator();
        while (it.hasNext())
	    {
                    Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				Product p=(Product)pi.getValue();
                if (p.getName().toLowerCase().startsWith(searchId))
                {
                        sb.append("<product>");
                        sb.append("<id>" + p.getId() + "</id>");
                        sb.append("<productName>" + p.getName() + "</productName>");
                        sb.append("</product>");
                }
			}
       }

	   return sb;
	}

	public static HashMap<String,Product> getData()
	{
		HashMap<String,Product> hm=new HashMap<>();
		try
		{
			getConnection();

		    String selectproduct="select * from  Productdetails";
		    PreparedStatement pst = conn.prepareStatement(selectproduct);
			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{	Product p = new Product(rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getString("ProductType"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), p);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Ajax getData"+e);

			e.printStackTrace();
		}
		return hm;
	}
	public static void storeData(HashMap<String,Product> productdata)
	{
		try
		{

			getConnection();

			String insertIntoProductQuery = "INSERT INTO product(productId,productName,image,retailer,productCondition,productPrice,productType,discount) "
			+ "VALUES (?,?,?,?,?,?,?,?);";
			for(Map.Entry<String, Product> entry : productdata.entrySet())
			{

				PreparedStatement pst = conn.prepareStatement(insertIntoProductQuery);
				//set the parameter for each column and execute the prepared statement
				pst.setString(1,entry.getValue().getId());
				pst.setString(2,entry.getValue().getName());
				pst.setString(3,entry.getValue().getImage());
				pst.setString(4,entry.getValue().getRetailer());
				pst.setString(5,entry.getValue().getCondition());
				pst.setDouble(6,entry.getValue().getPrice());
				pst.setString(7,entry.getValue().getType());
				pst.setDouble(8,entry.getValue().getDiscount());
				pst.execute();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Ajax storeData"+e);

		}
	}

}
