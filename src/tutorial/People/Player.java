package tutorial.People;

import tutorial.Flota.Car;
import tutorial.Flota.Parts;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private double cash;
    private List<Car> myCars;
    private List<String> transactionHistory;

    public Player(double cash) {
        this.cash = cash;
        //creates an empty set
        myCars = new ArrayList<>();
        System.out.println("You have started the game with 0 cars and " + cash + " in the bank");
        transactionHistory = new ArrayList<>();
    }

    public void addMoney(double amt) {
        if (amt > 0)
            cash += amt;
        System.out.println("Your balance is: " + cash);
    }

    public void subtractMoney(double amt) {
        if (amt > 0)
            cash -= amt;
        System.out.println("Your balance is: " + cash);
    }

    public double getCash() {
        return cash;
    }


    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void makeRepairs(Car car) {
        Scanner scanner= new Scanner(System.in);
        double costOfRepairs = 0;
        List<Parts> partsToFix = car.getCarParts()
                .stream()
                .filter(Parts::isDamaged)
                .collect(Collectors.toList());
        System.out.println("The pieces to repair are:");
        partsToFix.forEach(System.out::println);
        System.out.println("Do you want to fix your car?\n" +
                "You can go to JanuszCars, guaranteed fix but he's a bit pricey\n" +
                "Your other option is MarianAuto, about a 10% chance he's just gonna keep your money and do nothing and you'll need to pay him again to fix your car.\n" +
                "Or you can go to PPHU Adrian, he's cheap and there's a 20% chance he won't do it and you'll pay again, with a 2% chance of him breaking something else");

        System.out.println("So who would you like? Janusz, type 1\nMarian, type 2\nPPHU type 3.");
        int answer;
        while (true) {
            answer = scanner.nextInt();
            if ((answer > 3) || (answer < 1)) {
                System.out.println("How can you not choose between 1-3?\nTry again");
                continue;//loop backt to the beggining
            }
            costOfRepairs = getQuote(answer, car);
            System.out.println("You chose: " + answer  +
                    "\nThis has led to a total cost of: " + costOfRepairs);
            break;
        }
        car.increaseValue(partsToFix);
        subtractMoney(costOfRepairs);
        car.getHistoryOfRepair().add("You fixed: " + partsToFix.toString());
        car.increaseMaintenanceCosts(costOfRepairs);
        partsToFix.forEach(Parts::changeDamaged);
        System.out.println("After repairs, you are left with " + getCash());


    }

    private double getPriceForParts(Car car) {
        double costOfRepairs = 0.0;
        for (Parts p : car.getCarParts()) {
            if (p.isDamaged()) {
                switch (p.getName()) {
                    case "Brakes":
                        costOfRepairs += 400;
                        break;
                    case "Dampers":
                        costOfRepairs += 10;
                        break;
                    case "Engine":
                        costOfRepairs += 5000;
                        break;
                    case "Car Body":
                        costOfRepairs += 2000;
                        break;
                    case "Gear Box":
                        costOfRepairs += 1000;
                        break;
                }
            }
        }
        return costOfRepairs;
    }

    private double getQuote(int repairShop, Car car) {
        double costOfRepairs = 0;
        double multiplier = 0.0;
        double costOfShop = 0.0;
        double priceForCleaning = 50.0;
        costOfRepairs = getPriceForParts(car);

        switch (car.getSegment()) {
            case "Standard":
                multiplier = 1.1;
                break;
            case "Budget":
                multiplier = 1.05;
            case "Premium":
                multiplier = 1.5;
        }

        switch (repairShop) {
            case 1:
                costOfShop *= 1.5;
                break;
            case 2:
                costOfShop *= 1.25;
                if (Math.random() * 11 == 1) {
                    costOfShop *= 1.5;
                    System.out.println("MarianAuto messed up, looks like you had to pay for Janusz as well :)");
                }
                break;
            case 3:
                costOfShop *= 1.15;
                if (Math.random() * 11 < 3) {
                    if (Math.random() * 101 < 3) {
                        System.out.println("PPHU Adrian broke something else!");
                        List<Parts> p = car.getCarParts()
                                .stream()
                                .filter(parts -> !parts.isDamaged())
                                .collect(Collectors.toList());
                        int index = (int) (Math.random() * (p.size() + 1));
                        car.getCarParts().get(index).changeDamaged();
                        costOfRepairs = getPriceForParts(car);
                    }
                    costOfShop *= 1.5;
                    System.out.println("PPHU Adrian failed, looks like you had to pay for Janusz as well :)");
                }
                break;
        }


        return costOfRepairs * multiplier + costOfShop + priceForCleaning;
    }

    public boolean buyNewCar(Car car){
        double price = car.getValue()*1.02;
        if(myCars.contains(car)){
            System.out.println("You already own that car :)");
            return false;
        }
        System.out.println("You are paying " + price + " for this car.");
        subtractMoney(price);
        myCars.add(car);
        transactionHistory.add("You purchased: " + car);
        System.out.println("You now have " + cash + " in the bank!");
        return true;
    }

    public boolean sellYourCar(Car car, List<Client> clients){

        double price = car.getValue()*1.02;

        if(!myCars.contains(car)){
            System.out.println("You can't sell a car that you don't own!!!");
            return false;
        }

        System.out.println("Choose a client to sell to, if no one is available, select 0 to sell it to the bank at 3/4 price");
        Scanner scanner = new Scanner(System.in);
        int answer = 0;

        while(true){
            try{
                answer = scanner.nextInt();
                if(answer < 0 || answer > clients.size())
                    System.out.println("Please enter a valid number");
                else
                    break;
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid number");
            }
        }

        Client client = new Client();
        if(answer != 0){
            answer --;
            client = clients.get(answer);
            client.decreaseCash(price);
            System.out.println("You are selling this car for " + price + " to " + client.getName());
            transactionHistory.add("You sold " + car + " to " + client + " for " + price);
            addMoney(price);
        }
        else{
            price *= 0.75;
            System.out.println("You are selling this car for " + price * 0.75  + " to the bank.");
            transactionHistory.add("You sold " + car + " to the bank for " + price);
            addMoney(price);
        }

        transactionHistory.add("You sold: " + car);
        return true;
    }

    public List<Car> getMyCars() {
        return myCars;
    }
}
