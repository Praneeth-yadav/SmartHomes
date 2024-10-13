package classes;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
//import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

public class MongoDBDataStoreUtilities
{
//	static MongoCollection<Document> myReviews;
//	public static MongoCollection<Document> getConnection()
//	{
//		;
//		String connectionString = "mongodb+srv://epraneeth27510:Smallworld%409286@atlascluster.rv2dxa7.mongodb.net/";
//
//		try (MongoClient mongoclient =  MongoClients.create(connectionString)){
////		mongo = new MongoClient("localhost", 27017);
//
//			MongoDatabase db = mongoclient.getDatabase("CustomerProductReviews");
//			myReviews = db.getCollection("myReviews");
//			System.out.println("myReviews : "+myReviews);
//
////		myReviews= db.getCollection("myReviews");
//		}catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("Mongo error : "+e);
//		}
//		return myReviews; 
//	}

	  private static MongoClient mongoClient;
	    private static MongoCollection<Document> myReviews;

	    public static MongoCollection<Document> getConnection() {
	        String connectionString = "mongodb+srv://epraneeth27510:Smallworld%409286@atlascluster.rv2dxa7.mongodb.net/";

	        try {
	            // Create MongoClient only if it's not already initialized
	            if (mongoClient == null) {
	                mongoClient = MongoClients.create(connectionString);
	                System.out.println("MongoClient created.");
	            }

	            // Access the database and collection
	            MongoDatabase db = mongoClient.getDatabase("CustomerProductReviews");
	            myReviews = db.getCollection("myReviews");
	            System.out.println("myReviews collection: " + myReviews);

	        } catch (Exception e) {
	            System.out.println("Mongo error: " + e.getMessage());
	        }

	        return myReviews;
	    }

	    // Optional: Method to close the MongoClient connection when needed
	    public static void closeConnection() {
	        if (mongoClient != null) {
	            mongoClient.close();
	            mongoClient = null;  // Reset the client for future use
	            System.out.println("MongoClient connection closed.");
	        }
	    }

	public static String insertReview(String productname,String username,String producttype,String productprice,String productmaker,String reviewrating,String storeid,String retailercity,String retailerstate,String retailerpin,String productsale,String rebate,String userid,String age,String gender,String occupation,String reviewdate,String reviewtext)
	{
		try
		{		
			getConnection();
			Document doc = new Document("title", "myReviews").
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
			System.out.println("doc	"+ doc	);
			
			myReviews.insertOne(doc);
			return "Successfull";
		}
		catch(Exception e)
		{
			System.out.println("Exception inserting "+e);
			return "UnSuccessfull";
		}	
		
	}

//	public static HashMap<String, ArrayList<Review>> selectReview()
//	{	
//		HashMap<String, ArrayList<Review>> reviews=null;
//
//		try
//		{
//
//			getConnection();
//			MongoCursor<Document> cursor = myReviews.find().iterator();			
//			reviews=new HashMap<String, ArrayList<Review>>();
//			while (cursor.hasNext())
//			{
//                Document obj = cursor.next();
//
//				if(!reviews.containsKey(obj.getString("productName")))
//				{	
//					ArrayList<Review> arr = new ArrayList<Review>();
//					reviews.put(obj.getString("productName"), arr);
//				}
//				ArrayList<Review> listReview = reviews.get(obj.getString("productName"));		
//				Review review =new Review(obj.getString("productName"),obj.getString("userName"),obj.getString("productCategory"),obj.getString("productPrice"),obj.getString("productMaker"),
//					obj.getString("reviewRating"),obj.getString("storeid"),obj.getString("Storecity"),obj.getString("StoreState"),obj.getString("Storepin"),
//					obj.getString("ProductOnSale"),obj.getString("ManufacturerRebate"),obj.getString("UserId"),obj.getString("UserAge"),obj.getString("UserGender"),
//					obj.getString("UserOccupation"),obj.getString("reviewDate"),obj.getString("reviewText"));
//			//add to review hashmap
//				listReview.add(review);
//
//			}
//			return reviews;
//		}
//		catch(Exception e)
//		{
//			System.out.println("Exception mongo selectReview"+e);
//
//			reviews=null;
//			return reviews;	
//		}	
//
//
//	}
	
	public static HashMap<String, ArrayList<Review>> selectReview() {	
	    HashMap<String, ArrayList<Review>> reviews = null;

	    try {
	        getConnection();
	        MongoCursor<Document> cursor = myReviews.find().iterator();			
	        reviews = new HashMap<>();

	        while (cursor.hasNext()) {
	            Document obj = cursor.next();

	            String productName = obj.getString("productName");

	            // Check if the product name exists in the hashmap, if not, initialize a new list
	            if (!reviews.containsKey(productName)) {	
	                reviews.put(productName, new ArrayList<Review>());
	            }

	            // Retrieve the list of reviews for this product
	            ArrayList<Review> listReview = reviews.get(productName);

	            // Create a Review object by handling possible data type variations
	            Review review = new Review(
	                productName,
	                obj.getString("userName"),
	                obj.getString("productCategory"),
	                String.valueOf(obj.get("productPrice")),  // In case productPrice is stored as a number
	                obj.getString("productMaker"),
	                String.valueOf(obj.get("reviewRating")),  // In case reviewRating is stored as a number
	                obj.getString("storeid"),
	                obj.getString("Storecity"),
	                obj.getString("StoreState"),
	                String.valueOf(obj.get("Storepin")),  // In case Storepin is stored as an Integer
	                obj.getString("ProductOnSale"),
	                obj.getString("ManufacturerRebate"),
	                obj.getString("UserId"),
	                String.valueOf(obj.get("UserAge")),  // In case UserAge is stored as an Integer
	                obj.getString("UserGender"),
	                obj.getString("UserOccupation"),
	                obj.getString("reviewDate"),
	                obj.getString("reviewText")
	            );

	            // Add the review to the list associated with the product
	            listReview.add(review);
	        }
	        return reviews;
	    } catch (Exception e) {
	        System.out.println("Exception in mongo selectReview: " + e);
	        return null;  // Return null in case of an error
	    }
	}

	

	public static  ArrayList <Bestrating> topProducts(){
		ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
		try{

			getConnection();
			int retlimit =5;
//			DBObject sort = new BasicDBObject();
//			sort.put("reviewRating",-1);
            Document sort = new Document("reviewRating", -1);

//			DBCursor cursor = myReviews.find().limit(retlimit).sort(sort);
            MongoCursor<Document> cursor = myReviews.find().sort(sort).limit(retlimit).iterator();

			while(cursor.hasNext()) {
//				BasicDBObject obj = (BasicDBObject) cursor.next();
	                Document obj = cursor.next();
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
//			DBObject groupProducts = new BasicDBObject("_id","$Storepin"); 
//			groupProducts.put("count",new BasicDBObject("$sum",1));
//			DBObject group = new BasicDBObject("$group",groupProducts);
//			DBObject limit=new BasicDBObject();
//			limit=new BasicDBObject("$limit",5);
//
//			DBObject sortFields = new BasicDBObject("count",-1);
//			DBObject sort = new BasicDBObject("$sort",sortFields);
//			AggregationOutput output = myReviews.aggregate(group,sort,limit);
			Document groupProducts = new Document("_id", "$Storepin")
                    .append("count", new Document("$sum", 1));

            Document group = new Document("$group", groupProducts);

            // Sort by the count in descending order
            Document sortFields = new Document("count", -1);
            Document sort = new Document("$sort", sortFields);

            // Limit the result to 5 entries
            Document limit = new Document("$limit", 5);

            // Perform the aggregation
            AggregateIterable<Document> output = myReviews.aggregate(Arrays.asList(group, sort, limit));

			for (Document res : output) {

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
//			DBObject groupProducts = new BasicDBObject("_id","$productName"); 
//			groupProducts.put("count",new BasicDBObject("$sum",1));
//			DBObject group = new BasicDBObject("$group",groupProducts);
//			DBObject limit=new BasicDBObject();
//			limit=new BasicDBObject("$limit",5);
//
//			DBObject sortFields = new BasicDBObject("count",-1);
//			DBObject sort = new BasicDBObject("$sort",sortFields);
//			AggregationOutput output = myReviews.aggregate(group,sort,limit);
			Document groupProducts = new Document("_id", "$productName")
                    .append("count", new Document("$sum", 1));

            Document group = new Document("$group", groupProducts);

            // Sort the results by 'count' in descending order
            Document sortFields = new Document("count", -1);
            Document sort = new Document("$sort", sortFields);

            // Limit the results to 5 entries
            Document limit = new Document("$limit", 5);

            // Perform the aggregation query
            AggregateIterable<Document> output = myReviews.aggregate(Arrays.asList(group, sort, limit));

			for (Document res : output) {



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
//			DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
//			groupFields.put("count", new BasicDBObject("$sum", 1));
//			DBObject group = new BasicDBObject("$group", groupFields);
//
//			DBObject projectFields = new BasicDBObject("_id", 0);
//			projectFields.put("Storepin", "$_id");
//			projectFields.put("productName", "$productName");
//			projectFields.put("reviewCount", "$count");
//			DBObject project = new BasicDBObject("$project", projectFields);
//
//			DBObject sort = new BasicDBObject();
//			sort.put("reviewCount", -1);
//
//			DBObject orderby = new BasicDBObject();            
//			orderby = new BasicDBObject("$sort",sort);
//
//
//			AggregationOutput aggregate = myReviews.aggregate(group, project, orderby);
			
			Document groupFields = new Document("_id", new Document(dbObjIdMap))
                    .append("count", new Document("$sum", 1));

            Document group = new Document("$group", groupFields);

            // Create the projection stage to reshape the output
            Document projectFields = new Document("_id", 0)
                    .append("Storepin", "$_id.Storepin")
                    .append("productName", "$_id.productName")
                    .append("reviewCount", "$count");

            Document project = new Document("$project", projectFields);

            // Create the sort stage to sort by reviewCount in descending order
            Document sort = new Document("reviewCount", -1);

            Document orderby = new Document("$sort", sort);

            // Perform the aggregation
            AggregateIterable<Document> aggregate = myReviews.aggregate(Arrays.asList(group, project, orderby));


			for (Document result : aggregate) {

//				BasicDBObject obj = (BasicDBObject) result;
//				Object o = com.mongodb.util.JSON.parse(obj.getString("Storepin"));
//				BasicDBObject dbObj = (BasicDBObject) o;
//				Review review = new Review(dbObj.getString("productName"), dbObj.getString("Storepin"),
//					obj.getString("reviewCount"), null);
//				reviewList.add(review);
				
				String storePin = result.getString("Storepin");
                String productName = result.getString("productName");
                String reviewCount = result.get("reviewCount").toString();

                // Create a Review object and add it to the list
                Review review = new Review(productName, storePin, reviewCount, null);
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