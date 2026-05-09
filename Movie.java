public class Movie {

    private int id;

    private String name;

    private String category;

    private boolean available;

    public Movie(int id, String name, String category) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {

        return id +
                " | " +
                name +
                " | " +
                category +
                " | " +
                (available ? "Disponível" : "Alugado");
    }
}