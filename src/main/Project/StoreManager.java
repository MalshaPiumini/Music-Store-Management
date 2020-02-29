package Project;
import java.util.ArrayList;

public interface StoreManager {
    //add items
    public void addCD(CD item);
    public void addVinyl(Vinyl item);

    public boolean delete(MusicItem item);

    public void print();
    public void printSelected();

    public void sortByTitle(ArrayList<MusicItem> list, SortCategory c);
    public void sortByGenre(ArrayList<MusicItem> list, SortCategory c);

    public void buy(MusicItem item,int i);
    public void generateReport();
}
