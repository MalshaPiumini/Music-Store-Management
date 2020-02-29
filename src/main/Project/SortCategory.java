package Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortCategory {
    public ArrayList<MusicItem> byTitle(ArrayList<MusicItem> list){
        Comparator<MusicItem> compareByTitle = new Comparator<MusicItem>() {
            @Override
            public int compare(MusicItem o1, MusicItem o2) {
                return o1.title.compareTo(o2.title);
            }
        };
        Collections.sort(list,compareByTitle);
        return list;
    }

    public ArrayList<MusicItem> byGenre(ArrayList<MusicItem> list){
        Comparator<MusicItem> compareByGenre = new Comparator<MusicItem>() {
            @Override
            public int compare(MusicItem o1, MusicItem o2) {
                return o1.genre.compareTo(o2.genre);
            }
        };
        Collections.sort(list,compareByGenre);
        return list;
    }
}
