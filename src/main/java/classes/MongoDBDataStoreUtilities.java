package classes;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
//import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
import com.mongodb.client.MongoDatabase;

public class MongoDBDataStoreUtilities
{
	static DBCollection myReviews;
	public static DBCollection getConnection()
	{
		;
		String connectionString = "mongodb+srv://epraneeth27510:Smallworld@9286@atlascluster.rv2dxa7.mongodb.net/";

		try (MongoClient mongo = (MongoClient) MongoClients.create(connectionString)){
//		mongo = new MongoClient("localhost", 27017);

			MongoDatabase db = mongo.getDatabase("CustomerProductReviews");
			myReviews = (DBCollection) db.getCollection("myReviews");
//		myReviews= db.getCollection("myReviews");
		}
		return myReviews; 
	}


	public static String insertReview(String productname,String username,String producttype,String productprice,String productmaker,String reviewrating,String storeid,String retailercity,String retailerstate,String retailerpin,String productsale,String rebate,String userid,String age,String gender,String occupation,String reviewdate,String reviewtext)
	{
		try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
			append("userName", username).
			append("productName", productname).
			append("productCategory", producttype).
			append("productPrice",(int) Double.parseDouble(productprice)).
			append("productMaker", productmaker).
			append("reviewRating",Integer.parseInt(reviewrating)).
			append("storeid",storeid).
			append("Storecity", retailercity).
			append("StoreState",retailerstate).
			append("Storepin", retailerpin).
			append("ProductOnSale",productsale).
			append("ManufacturerRebate",rebate).
			append("UserId",userid).
			append("UserAge",age).
			append("UserGender",gender).
			append("UserOccupation",occupation).
			append("reviewDate", reviewdate).
			append("reviewText", reviewtext);
			
			myReviews.insert(doc);
			return "Successfull";
		}
		catch(Exception e)
		{
			return "UnSuccessfull";
		}	
		
	}

	public static HashMap<String, ArrayList<Review>> selectReview()
	{	
		HashMap<String, ArrayList<Review>> reviews=null;

		try
		{

			getConnection();
			DBCursor cursor = myReviews.find();
			reviews=new HashMap<String, ArrayList<Review>>();
			while (cursor.hasNext())
			{
				BasicDBObject obj = (BasicDBObject) cursor.next();				

				if(!reviews.containsKey(obj.getString("productName")))
				{	
					ArrayList<Review> arr = new ArrayList<Review>();
					reviews.put(obj.getString("productName"), arr);
				}
				ArrayList<Review> listReview = reviews.get(obj.getString("productName"));		
				Review review =new Review(obj.getString("productName"),obj.getString("userName"),obj.getString("productCategory"),obj.getString("productPrice"),obj.getString("productMaker"),
					obj.getString("reviewRating"),obj.getString("storeid"),obj.getString("Storecity"),obj.getString("StoreState"),obj.getString("Storepin"),
					obj.getString("ProductOnSale"),obj.getString("ManufacturerRebate"),obj.getString("UserId"),obj.getString("UserAge"),obj.getString("UserGender"),
					obj.getString("UserOccupation"),obj.getString("reviewDate"),obj.getString("reviewText"));
			//add to review hashmap
				listReview.add(review);

			}
			return reviews;
		}
		catch(Exception e)
		{
			reviews=null;
			return reviews;	
		}	


	}
	

	public static  ArrayList <Bestrating> topProducts(){
		ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
		try{

			getConnection();
			int retlimit =5;
			DBObject sort = new BasicDBObject();
			sort.put("reviewRating",-1);
			DBCursor cursor = myReviews.find().limit(retlimit).sort(sort);
			while(cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();

				String prodcutnm = obj.get("productName").toString();
				String rating = obj.get("reviewRating").toString();
				Bestrating best = new Bestrating(prodcutnm,rating);
				Bestrate.add(best);
			}


		}
		catch (Exception e)
		{ 
			System.out.println("it is"+e.getMessage());
		}
		return Bestrate;
	}

	public static ArrayList <Mostsoldzip> mostsoldZip(){
		ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
		try{

			getConnection();
			System.out.println("inside mostsoldZip in MongoDBDataStoreUtilities");
			DBObject groupProducts = new BasicDBObject("_id","$Storepin"); 
			groupProducts.put("count",new BasicDBObject("$sum",1));
			DBObject group = new BasicDBObject("$group",groupProducts);
			DBObject limit=new BasicDBObject();
			limit=new BasicDBObject("$limit",5);

			DBObject sortFields = new BasicDBObject("count",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = myReviews.aggregate(group,sort,limit);
			for (DBObject res : output.results()) {

				String zipcode =(res.get("_id")).toString();
				String count = (res.get("count")).toString();	
				Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
				System.out.println("inside mostsldzip in MongoUtilities is"+mostsldzip);
				mostsoldzip.add(mostsldzip);

			}

		}catch (Exception e){
			System.out.println("error is"+e);
		}
		return mostsoldzip;
	}

	public static ArrayList <Mostsold> mostsoldProducts(){
		ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
		try{


			getConnection();
			DBObject groupProducts = new BasicDBObject("_id","$productName"); 
			groupProducts.put("count",new BasicDBObject("$sum",1));
			DBObject group = new BasicDBObject("$group",groupProducts);
			DBObject limit=new BasicDBObject();
			limit=new BasicDBObject("$limit",5);

			DBObject sortFields = new BasicDBObject("count",-1);
			DBObject sort = new BasicDBObject("$sort",sortFields);
			AggregationOutput output = myReviews.aggregate(group,sort,limit);

			for (DBObject res : output.results()) {



				String prodcutname =(res.get("_id")).toString();
				String count = (res.get("count")).toString();	
				Mostsold mostsld = new Mostsold(prodcutname,count);
				mostsold.add(mostsld);

			}



		}catch (Exception e){ System.out.println(e.getMessage());}
		return mostsold;
	}	

  //Get all the reviews grouped by product and zip code;
	public static ArrayList<Review> selectReviewForChart() {

		
		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {

			getConnection();
			Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
			dbObjIdMap.put("Storepin", "$Storepin");
			dbObjIdMap.put("productName", "$productName");
			DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
			groupFields.put("count", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject projectFields = new BasicDBObject("_id", 0);
			projectFields.put("Storepin", "$_id");
			projectFields.put("productName", "$productName");
			projectFields.put("reviewCount", "$count");
			DBObject project = new BasicDBObject("$project", projectFields);

			DBObject sort = new BasicDBObject();
			sort.put("reviewCount", -1);

			DBObject orderby = new BasicDBObject();            
			orderby = new BasicDBObject("$sort",sort);


			AggregationOutput aggregate = myReviews.aggregate(group, project, orderby);

			for (DBObject result : aggregate.results()) {

				BasicDBObject obj = (BasicDBObject) result;
				Object o = com.mongodb.util.JSON.parse(obj.getString("Storepin"));
				BasicDBObject dbObj = (BasicDBObject) o;
				Review review = new Review(dbObj.getString("productName"), dbObj.getString("Storepin"),
					obj.getString("reviewCount"), null);
				reviewList.add(review);

			}
			return reviewList;

		}

		catch (

			Exception e) {
			reviewList = null;

			return reviewList;
		}

	}


	
}	