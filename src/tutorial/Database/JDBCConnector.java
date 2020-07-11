package tutorial.Database;

import tutorial.Flota.Car;
import tutorial.People.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class JDBCConnector {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5433/oop";
    private static Connection CONN; //establish connection to database

    static final String USER = "postgres";
    static final String PASS = "kotsimona";

    public static void connect() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        CONN = DriverManager.getConnection(DB_URL, props);
        System.out.println("connected");
    }

    public static Statement getStatement() throws SQLException {
        return CONN.createStatement();
    }

    public static void executeSQL(String sql) throws SQLException {
        CONN.createStatement().execute(sql);
    }

    public static ResultSet getDataFromTable(String sql) throws SQLException {
        PreparedStatement ps = CONN.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static void saveCarsToDB() throws SQLException {
        List<Car> cars = new ArrayList<>();
        Car car1 = new Car(110000, "BMW", 1300, "Black", "Premium", 1);
        Car car2 = new Car(1000, "Seat", 160000, "Red", "Budget", 2);
        Car car3 = new Car(250000, "Mercedes", 13000, "Black", "Premium", 3);
        Car car4 = new Car(55000, "Skoda", 21000, "Silver", "Standard", 4);
        Car car5 = new Car(48000, "Skoda", 33000, "White", "Standard", 5);
        Car car7 = new Car(3000, "Ford", 10000, "Red", "Standard", 6);
        Car car6 = new Car(50000, "Bugati", 10000, "Red", "Premium", 7);
        Car car8 = new Car(60000, "Audi", 10000, "Red", "Premium", 8);
        Car car9 = new Car(70000, "Tesla", 500000, "Red", "Premium", 9);
        Car car0 = new Car(80000, "Toyota", 10000, "Red", "Standard", 10);
        Car car10 = new Car(80000, "Hyundai", 10000, "Red", "Budget", 11);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);
        cars.add(car7);
        cars.add(car8);
        cars.add(car9);
        cars.add(car0);
        cars.add(car10);

        for (Car c : cars) {
            //f for float, s for string, d for decimal
            String sql = String.format(Locale.US,
                    "insert into \"Cars\" values(%.2f, '%s', %d, '%s', '%s')",
                    c.getValue(), c.getBrand(), c.getMileage(), c.getColor(), c.getSegment());
            executeSQL(sql);
        }


    }

    public static void saveClientsToDB() throws SQLException {
        List<Client> clients = new ArrayList<>();
        Client client1 = new Client(2, "Jeff", new String[]{"Trucks"}, new String[]{"Toyota", "Hyundai"}, true, new String[]{"Budget"}, 10000);
        clients.add(client1);
        client1 = new Client(3, "Geoff", new String[]{"Cars"}, new String[]{"BMW", "Audi"}, false, new String[]{"Standard"}, 500000);
        clients.add(client1);
        client1 = new Client(4, "Dzef", new String[]{"Cars", "Trucks"}, new String[]{"Skoda", "Toyota", "Ford"}, true, new String[]{"Standard", "Budget"}, 250000);
        clients.add(client1);
        client1 = new Client(5, "Heff", new String[]{"Trucks"}, new String[]{"Volkswagen"}, true, new String[]{"Standard"}, 70000);
        clients.add(client1);
        client1 = new Client(6, "Maria", new String[]{"Cars"}, new String[]{"Tesla"}, false, new String[]{"Premium"}, 1000000);
        clients.add(client1);
        client1 = new Client(7, "Mary", new String[]{"Cars"}, new String[]{"Volkswagen", "Toyota", "Skoda"}, false, new String[]{"Budget", "Standard"}, 90000);
        clients.add(client1);
        client1 = new Client(8, "Marianne", new String[]{"Cars"}, new String[]{"Mercedes", "Audi"}, false, new String[]{"Premium"}, 234000);
        clients.add(client1);
        client1 = new Client(9, "Mariusz", new String[]{"Cars"}, new String[]{"Toyota", "BMW"}, false, new String[]{"Premium", "Standard"}, 130000);
        clients.add(client1);
        client1 = new Client(10, "Mary Anne", new String[]{"Cars"}, new String[]{"Bugati", "Tesla"}, false, new String[]{"Premium"}, 5000000);
        clients.add(client1);
        client1 = new Client(11, "Jeffrey", new String[]{"Cars"}, new String[]{"Audi", "Volkswagen"}, false, new String[]{"Premium", "Standard"}, 3000000);
        clients.add(client1);
        client1 = new Client(12, "Mary-Kate", new String[]{"Cars"}, new String[]{"BMW", "Ford"}, false, new String[]{"Standard"}, 500000);
        clients.add(client1);
        client1 = new Client(13, "Ashley", new String[]{"Cars"}, new String[]{"Ford", "Audi"}, false, new String[]{"Standard"}, 200000);
        clients.add(client1);
        client1 = new Client(14, "Colonel Sanders", new String[]{"Cars"}, new String[]{"Mercedes", "Audi"}, false, new String[]{"Premium"}, 120000);
        clients.add(client1);
        client1 = new Client(100, "Mr. Donald J. Trump JR.", new String[]{"Cars, Trucks"}, new String[]{"Mercedes", "Audi", "Bugati", "Tesla"}, false, new String[]{"Premium"}, 1000000000);
        clients.add(client1);


        for (Client c : clients) {
            String sql = "insert into \"Clients\" values(?, ?, ?, ?, ?, ?, ?);";
            Array arrayFlota = CONN.createArrayOf("text", c.getFlota());
            Array arrayBrand = CONN.createArrayOf("text", c.getBrands());
            Array arraySegment = CONN.createArrayOf("text", c.getSegment());


            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            ps.setArray(3, arrayFlota);
            ps.setArray(4, arrayBrand);
            ps.setBoolean(5, c.isWillBuyBroken());
            ps.setArray(6, arraySegment);
            ps.setDouble(7, c.getCash());

            ps.executeUpdate();
        }
    }
}
