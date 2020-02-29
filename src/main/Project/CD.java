package Project;

public class CD extends MusicItem {
    private int duration;

    public CD(String itemID, String title, String genre, String releaseDate, String artist, double price,
              int duration) {
        super(itemID, title, genre, releaseDate, artist, price);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + duration;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CD other = (CD) obj;
        if (duration != other.duration)
            return false;
        return true;
    }
}