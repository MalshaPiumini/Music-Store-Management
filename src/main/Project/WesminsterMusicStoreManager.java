package Project;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

class WestminsterMusicStoreManager implements StoreManager  {
    private static int duration_total=0;
    private ArrayList<MusicItem> storeItem = new ArrayList<MusicItem>();
    private static final int MAX_COUNT = 1000;
    private ArrayList<MusicItem> boughtItem = new ArrayList<MusicItem>();
    private int count=0;

    CD tempCD;
    Vinyl tempVinyl;

    private int deleteItemIndex =0;

    private  DBConn db = new DBConn("MusicStore"); //create database connection
    MongoCollection<Document> collection = db.database.getCollection("MusicItems");

    // Fetch all the documents from the mongo collection.
    public void getAllDocuments(MongoCollection<Document> col) {
        ArrayList<Document> docummnetList = col.find().into(new ArrayList<Document>());
        try {
            for(int i=0;i<docummnetList.size();i++) {
                if((docummnetList.get(i).get("Type").toString()).equals("CD")){
                    tempCD = new CD(docummnetList.get(i).get("ItemID").toString(),
                            docummnetList.get(i).get("Title").toString(),
                            docummnetList.get(i).get("Genre").toString(),
                            docummnetList.get(i).get("ReleaseDate").toString(),
                            docummnetList.get(i).get("Artist").toString(),
                            Double.valueOf(docummnetList.get(i).get("Price").toString()),
                            Integer.parseInt(docummnetList.get(i).get("Duration").toString())) {
                    };
                    duration_total+=Integer.parseInt(docummnetList.get(i).get("Duration").toString());

                    storeItem.add(tempCD);
                }
                else{
                    tempVinyl = new Vinyl(docummnetList.get(i).get("ItemID").toString(),
                            docummnetList.get(i).get("Title").toString(),
                            docummnetList.get(i).get("Genre").toString(),
                            docummnetList.get(i).get("ReleaseDate").toString(),
                            docummnetList.get(i).get("Artist").toString(),
                            Double.valueOf(docummnetList.get(i).get("Price").toString()),
                            Double.valueOf(docummnetList.get(i).get("Speed").toString()),
                            Double.valueOf(docummnetList.get(i).get("Diameter").toString())) {
                    };
                    storeItem.add(tempVinyl);
                }

            }
        } catch (Exception e){
            System.out.println(e);
        } finally{
            System.out.println("Total Minutes:"+(duration_total/60));
        }
    }
    //add items
    @Override
    public void addCD(CD item) {
        if (storeItem.size() < MAX_COUNT) {
            storeItem.add(item);

            //add item to database change it to item
            count=count+1;
            Document doc= new Document("ItemID",item.itemID).
                    append("Title",item.title).
                    append("Genre",item.genre).
                    append("ReleaseDate",item.releaseDate).
                    append("Artist",item.artist).
                    append("Price",item.price).
                    append("Duration",item.getDuration()).
                    append("Type","CD");
            collection.insertOne(doc);
            int freeSpace=MAX_COUNT-storeItem.size();
            System.out.println("Free spaces left: "+freeSpace);
        } else {
            throw new IllegalArgumentException("Store is full no place to add!!!...");
        }
    }
    @Override
    public void addVinyl(Vinyl item) {
        if (storeItem.size() < MAX_COUNT) {
            storeItem.add(item);
            //add item to database change it to item
            Document doc= new Document("ItemID",item.itemID).
                    append("Title",item.title).
                    append("Genre",item.genre).
                    append("ReleaseDate",item.releaseDate).
                    append("Artist",item.artist).
                    append("Price",item.price).
                    append("Speed",item.getSpeed()).
                    append("Diameter",item.getDiameter()).
                    append("Type","Vinyl");
            collection.insertOne(doc);
            int freeSpace=MAX_COUNT-storeItem.size();
            System.out.println("Free spaces left: "+freeSpace);
        } else {
            throw new IllegalArgumentException("Store is full no place to add!!!...");
        }
    }
    // delete item
    @Override
    public boolean delete(MusicItem item) {
        String delete_id=item.itemID;
        collection.deleteOne(new Document("ItemID" ,delete_id));
        System.out.println("ItemIndex "+delete_id+" Item Deleted");
        storeItem.remove(deleteItemIndex);
        int freeSpace=MAX_COUNT-storeItem.size();
        System.out.println("Free spaces left: "+freeSpace);
        return false;
    }

    //printing
    @Override
    public void print() {
        System.out.println("\n-----Items in Database-----\n");

        System.out.println("List of CDs");
        for (MusicItem item : storeItem) {
            if (item instanceof CD) {
                System.out.println((CD) item);
            }
        }
        System.out.println("\nList of Vinyl");
        for (MusicItem item : storeItem) {
            if (item instanceof Vinyl) {
                System.out.println((Vinyl) item);
                printSelected();
            }
        }
        System.out.println("\n---------------------------\n");
    }

    @Override
    public void printSelected() {
        MongoCursor<Document> cursor=collection.find().iterator();
            try{
                while (cursor.hasNext()){
                    Document doc=cursor.next();
                    String itemID=doc.getString("ItemID");
                    String title=doc.getString("Title");
                    String type=doc.getString("Type");

                    System.out.format("%1$-6s%2$-10s%3$-20s\n", itemID,type,title );
                }
            }finally {
                cursor.close();
        }
    }

    //sorting
    @Override
    public void sortByTitle(ArrayList<MusicItem> list, SortCategory c) {
        System.out.println("\n---Sorted According to Title----\n");
        storeItem = c.byTitle(list);
        for (MusicItem item : storeItem) {
                System.out.println(item);
        }
        System.out.println("\n---------------------------------\n");
    }

    @Override
    public void sortByGenre(ArrayList<MusicItem> list, SortCategory c) {
        System.out.println("\n---Sorted According to Genre----\n");
        storeItem = c.byGenre(list);
        for (MusicItem item : storeItem) {
            System.out.println(item);
        }
        System.out.println("\n---------------------------------\n");
    }

    //write in file
    public void writeIntoFile(String details){
        String str = details;
        BufferedWriter writer = null;
        File file =new File("E:/IIT/java/Project/src/main/java/Project/ItemsBought.txt");
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(' ');
            writer.append(str);

            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    //buy
    @Override
    public void buy(MusicItem item,int copies) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        double totalCost=item.price*copies;
        String details_="Title:"+item.title.toString()+"        id:"+item.itemID.toString()+"      Price:"+item.price+"       Selling Date & Time:"+dtf.format(now)+"        Total Cost:"+totalCost+"\n";

        writeIntoFile(details_);
    }

    //gen report
    @Override
    public void generateReport() {
        System.out.println("\n---Sold Items---\n");
        try {
            BufferedReader in = new BufferedReader(new FileReader("E:/IIT/java/Project/src/main/java/Project/ItemsBought.txt"));
            String line;
            while((line = in.readLine()) != null)
            {
                System.out.println(line);
            }
            in.close();
            System.out.println("\n---------------");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllDatacount(){
        count=(int)collection.count();
    }
    public ArrayList<MusicItem> getStoreItem() {
        return storeItem;
    }

    public void setDeleteItemIndex(int deleteItemIndex) {
        this.deleteItemIndex = deleteItemIndex;
    }
    class PriceComparator implements Comparator<MusicItem>{
        @Override
        public int compare(MusicItem o1, MusicItem o2) {
            return (int)((o1.price - o2.price)*100);
        }
    }

    class GenreComparator implements Comparator<MusicItem>{
        @Override
        public int compare(MusicItem o1, MusicItem o2) {
            return (int)((o1.price - o2.price)*100);
        }
    }

}