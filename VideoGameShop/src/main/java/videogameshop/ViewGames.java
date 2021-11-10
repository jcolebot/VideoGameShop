package videogameshop;

public class ViewGames {
    private int gameid;
    private String title;
    private double price;

    private User user;

    public ViewGames(int gameid, String title, double price) {
        this.gameid = gameid;
        this.title = title;
        this.price = price;
    }

    public int getGameId() {
        return gameid;
    }

    public void setGameId(int gameid) {
        this.gameid = gameid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ViewGames{" + "gameid=" + gameid + ", title='" + title
        + '\'' + ", price=" + price + '}';
    }

}
