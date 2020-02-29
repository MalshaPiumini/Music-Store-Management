package Project;
import java.util.Objects;

public class Vinyl extends MusicItem {
    private double speed;
    private double diameter;

    public Vinyl(String itemID, String title, String genre, String releaseDate, String artist, double price, double speed, double diameter) {
        super(itemID, title, genre, releaseDate, artist, price);
        this.speed = speed;
        this.diameter = diameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vinyl vinyl = (Vinyl) o;
        return Double.compare(vinyl.speed, speed) == 0 &&
                Double.compare(vinyl.diameter, diameter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed, diameter);
    }

    public double getSpeed() {
        return speed;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "speed=" + speed +
                ", diameter=" + diameter +
                ", itemID='" + itemID + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", artist='" + artist + '\'' +
                ", price=" + price +
                '}';
    }
}
