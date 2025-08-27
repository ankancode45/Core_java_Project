// MobileStoreApp.java
// ------------------------------------------------------------
// 1. Custom Exceptions
class MobileNotFoundException extends Exception {
    public MobileNotFoundException(String message) {
        super(message);
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

// ------------------------------------------------------------
// 2. Mobile class
class Mobile {
    int id;
    String brand;
    String model;
    double price;
    int stock;

    Mobile(int id, String brand, String model, double price, int stock) {
        this.id    = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.stock = stock < 0 ? 0 : stock;          // avoid negative stock
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + brand + " " + model +
               ", Price: ₹" + price + ", Stock: " + stock;
    }
}

// ------------------------------------------------------------
// 3. Store Manager
class MobileStore {
    private final Mobile[] mobiles;
    private int count;

    MobileStore(int size) {
        mobiles = new Mobile[size];
        count   = 0;
    }

    void addMobile(Mobile m) {
        if (count < mobiles.length) {
            mobiles[count++] = m;
            System.out.println("\nMobile added successfully!");
        } else {
            System.out.println("\nStore is full, cannot add more.");
        }
    }

    void viewMobiles() {
        if (count == 0) {
            System.out.println("\nNo mobiles available.");
            return;
        }
        System.out.println("\n--- Available Mobiles ---");
        for (int i = 0; i < count; i++) {
            System.out.println(mobiles[i]);
        }
    }

    void searchMobile(int id) throws MobileNotFoundException {
        for (int i = 0; i < count; i++) {
            if (mobiles[i].id == id) {
                System.out.println("\nFound: " + mobiles[i]);
                return;
            }
        }
        throw new MobileNotFoundException("Mobile with ID " + id + " not found!");
    }

    void buyMobile(int id) throws MobileNotFoundException, OutOfStockException {
        for (int i = 0; i < count; i++) {
            if (mobiles[i].id == id) {
                if (mobiles[i].stock > 0) {
                    mobiles[i].stock--;
                    System.out.println("\nPurchase successful! You bought: "
                            + mobiles[i].brand + " " + mobiles[i].model);
                    return;
                } else {
                    throw new OutOfStockException(
                            "Mobile " + mobiles[i].brand + " " + mobiles[i].model + " is out of stock!");
                }
            }
        }
        throw new MobileNotFoundException("Mobile with ID " + id + " not found!");
    }
}

// ------------------------------------------------------------
// 4. Main Class
public class MobileStoreApp {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        MobileStore store    = new MobileStore(10);   // capacity 10

        int choice;
        do {
            System.out.println("\n===== Startup Mobile Store =====");
            System.out.println("1. Add Mobile");
            System.out.println("2. View Mobiles");
            System.out.println("3. Search Mobile by ID");
            System.out.println("4. Buy Mobile");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Mobile ID   : ");
                    int id = sc.nextInt();
                    sc.nextLine();                    // consume newline

                    System.out.print("Enter Brand      : ");
                    String brand = sc.nextLine();

                    System.out.print("Enter Model      : ");
                    String model = sc.nextLine();

                    System.out.print("Enter Price      : ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Stock Qty  : ");
                    int stock = sc.nextInt();

                    store.addMobile(new Mobile(id, brand, model, price, stock));
                }
                case 2 -> store.viewMobiles();
                case 3 -> {
                    System.out.print("Enter Mobile ID to search: ");
                    int searchId = sc.nextInt();
                    try {
                        store.searchMobile(searchId);
                    } catch (MobileNotFoundException e) {
                        System.out.println("\nError: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Enter Mobile ID to buy: ");
                    int buyId = sc.nextInt();
                    try {
                        store.buyMobile(buyId);
                    } catch (MobileNotFoundException | OutOfStockException e) {
                        System.out.println("\nError: " + e.getMessage());
                    }
                }
                case 5 -> System.out.println("\nExiting Mobile Store...");
                default -> System.out.println("\nInvalid choice! Try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}
