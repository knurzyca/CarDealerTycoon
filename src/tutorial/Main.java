package tutorial;

import tutorial.Database.JDBCConnector;
import tutorial.Flota.Car;
import tutorial.People.Client;
import tutorial.People.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static tutorial.Static.line;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Car> allCars;
    static List<Car> fiveCars;
    static List<Client> allClients;

    public static void main(String[] args) throws SQLException {
        allCars = getAllCars();
        allClients = getAllClients();
        //Add five cars from all
        fiveCars = new ArrayList<>(allCars.subList(0, 5));
        //Remove the added cars from the set of all cars
        allCars.removeAll(fiveCars);


        System.out.println("Hello and welcome to the Car Dealer Tycoon." +
                "\nThe goal of the game is to double your cash with the fewest number of moves" +
                "\nHow much money are you starting with today?" +
                "\n" + line);
//        allCars.forEach(System.out::println);
        double beginningCash = 0.0;
        while (true) {
            try {
                beginningCash = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.next();
            }

        }
        Player mainPlayer = new Player(beginningCash);
        displayMainMenu();
        int answer;
        while (true) {
            try {
                answer = scanner.nextInt();
                playTheGame(answer, mainPlayer);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choose a proper number");
            }
        }


    }

    static void playTheGame(int answer, Player player) {
        switch (answer) {
            case 1:
                System.out.println("Displaying available cars:");
                fiveCars.forEach(System.out::println);
                break;
            case 2:
                buyCar(player);
                break;
            case 3:
                System.out.println("Displaying all cars owned by player:");
                player.getMyCars().forEach(System.out::println);
                break;
            case 4:
                if (player.getMyCars().size() == 0) {
                    System.out.println("you don't have any cars to repair");
                    break;
                }
                System.out.println("Cars to repair: ");
                makeRepairs(player);
                break;
            case 5:
                System.out.println("Some of your clients are interested in passenger cars, the others prefer trucks." +
                        " Every client has a certain amount of cash likes some brands and look for car from one segment.");
                allClients.forEach(System.out::println);
                break;
            case 6:
                System.out.println("Sell a car to potential client");
                sellACar(player);
                break;
            case 7:
                System.out.println("You're too good you don't need advertisements");
            case 8:
                System.out.println("Player's cash:\n" + player.getCash());
                break;
            case 9:
                System.out.println("Check transactions history of player: ");
                player.getTransactionHistory().forEach(System.out::println);
                break;
            case 10:
                System.out.println("Check car's repair history ");
                for (Car c : player.getMyCars()) {
                    System.out.println("Repair history for: " + c +
                            "\n" + c.getHistoryOfRepair());
                }
                break;
            case 11:
                System.out.println("Check how much you spent to fix and clean your cars ");
                for (Car c : player.getMyCars()) {
                    System.out.println("Repair costs for: " + c +
                            "\n" + c.getMaintenanceCosts());
                }
                break;
            case 12:
                System.out.println("Thanks for playing :)");
                return;
            default:
                System.out.println("Please choose a different number");
        }
        displayMainMenu();
        try {
            answer = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("That's not a valid choice");
        }
        playTheGame(answer, player);//calling loop - unnested metthod
    }

    private static void sellACar(Player player) {
        System.out.println("Choose a car to sell");
        int answer = 0;
        for (int i = 0; i < player.getMyCars().size(); i++)
            System.out.println(i + ".     " + player.getMyCars().get(i));

        //Print all of the player's cars
        player.getMyCars().forEach(System.out::println);
        System.out.println("Please type a number to choose a car.");
        while (true) {
            try {
                answer = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choose a valid number pls");
            }
            scanner.next();
        }
        Car car  = player.getMyCars().get(answer);
        List<Client> clients = findPotentialClients(car);
        player.sellYourCar(car, clients);
    }

    private static void makeRepairs(Player player) {
        System.out.println("Choose a car to repair");
        int answer = 0;
        for (int i = 0; i < player.getMyCars().size(); i++)
            System.out.println(i + ".     " + player.getMyCars().get(i));

        //Print all of the player's cars
        player.getMyCars().forEach(System.out::println);
        System.out.println("Please type a number to choose a car.");
        while (true) {
            try {
                answer = scanner.nextInt();
                if ((answer < player.getMyCars().size()) && answer >= 0)
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choose a valid number pls");
            }
            scanner.next();
        }
        player.makeRepairs(player.getMyCars().get(answer));
    }

    private static void buyCar(Player player) {
        int answer = 0;
        System.out.println("The cars available are:");
        fiveCars.forEach(System.out::println);
        System.out.println("Please type 0-4 to choose a car, all decisions are final.");
        while (true) {
            try {
                answer = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choose a valid number pls");
            }
            scanner.next();
        }
        if (player.buyNewCar(fiveCars.get(answer))) {
            fiveCars.remove(answer);
            restockCars();
        }

    }

    static void restockCars() {
        if (allCars.size() == 0) {
            System.out.println("You are down to " + fiveCars.size() + " cars.");
            return;
        }
        for (int i = fiveCars.size(); i < 5; i++) {
            Car car = allCars.get((int) (Math.random() * allCars.size()));
            fiveCars.add(car);
            allCars.remove(car);
            if (allCars.size() == 0) {
                System.out.println("You are down to " + fiveCars.size() + " cars.");
                return;
            }
        }
    }


    static void displayMainMenu() {
        System.out.println(line + "\nMain Menu, choose a number:" +
                "1 for list of the cars you can buy\n" +
                "2 to buy a car\n" +
                "3 for list of owned cars\n" +
                "4 to repair a car\n" +
                "5 to view potential clients\n" +
                "6 to sell a car to a potential client\n" +
                "7 to buy an advertising\n" +
                "8 to check your account balance\n" +
                "9 to check transactions history\n" +
                "10 to check a car's repair history \n" +
                "11 to check how much you spent to fix and clean a car.\n" +
                "12 Quit");
    }

    static List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            JDBCConnector.connect();
            String sql = "Select * from \"Cars\"";
            ResultSet rs = JDBCConnector.getDataFromTable(sql);
            while (rs.next()) {
                Car tempCar = new Car();
                tempCar.setValue(rs.getDouble(1));
                tempCar.setBrand(rs.getString(2));
                tempCar.setMileage(rs.getLong(3));
                tempCar.setColor(rs.getString(4));
                tempCar.setSegment(rs.getString(5));
                tempCar.setId(rs.getInt(6));
                cars.add(tempCar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try {
            JDBCConnector.connect();
            String sql = "Select * from \"Clients\"";
            ResultSet rs = JDBCConnector.getDataFromTable(sql);
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt(1));
                client.setName(rs.getString(2));
                client.setFlota((String[]) rs.getArray(3).getArray());
                client.setBrands((String[]) rs.getArray(4).getArray());
                client.setWillBuyBroken(rs.getBoolean(5));
                client.setSegment((String[]) rs.getArray(6).getArray());
                client.setCash(rs.getDouble(7));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    static List<Client> findPotentialClients(Car c){
        List<Client> potentialClients = new ArrayList<>();
            System.out.println("Potential clients for your: " + c);
            potentialClients = allClients
                    .stream()
                    .filter(client -> client.getCash() > c.getValue())
                    .filter(client -> client.getBrandsList().contains(c.getBrand()))
                    .filter(client -> client.getSegmentsList().contains(c.getSegment()))
                    .collect(Collectors.toList());
            if(c.isDamaged())
                potentialClients = potentialClients
                        .stream()
                        .filter(Predicate.not(Client::isWillBuyBroken))
                        .collect(Collectors.toList());
            return potentialClients;

    }
}
//brand segment willbuydamaged cash