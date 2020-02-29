package Project;

public class Product {
    private String ID;
    private double price;
    private String title;

    public Product(){
        this.ID = "";
        this.price = 0;
        this.title="";
    }

    public Product(String ID, double price,String title){
        this.ID = ID;
        this.price = price;
        this.title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
