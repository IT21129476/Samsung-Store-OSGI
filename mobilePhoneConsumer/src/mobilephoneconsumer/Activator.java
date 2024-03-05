package mobilephoneconsumer;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import mobilephoneproducer.MobilePhoneService;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("|----------------------------------------|");
        System.out.println("|   ***  Welcome to Samsung Store  ***   |");
        System.out.println("|----------------------------------------|\n");
        System.out.println("Select a category:\n1. Samsung Mobile\n2. Samsung Accessories\n3. Samsung Computers and Laptops\n4. Samsung Services");
        Scanner scanner = new Scanner(System.in);
        int category = getUserInput(scanner, 4); 

        switch (category) {
            case 1:
                showMobileStoreWelcome(scanner, context);
                break;
            case 2:
            	System.out.println("These services are not available yet.");
                break;
            case 3:
            	System.out.println("These services are not available yet.");
                break;
            case 4:
                System.out.println("These services are not available yet.");
                break;
            default:
                System.out.println("Invalid category selected.");
                break;
        }
    }

    private void showMobileStoreWelcome(Scanner scanner, BundleContext context) throws Exception {
        System.out.println("|-----------------------------------------|");
        System.out.println("| *** Welcome to Samsung Mobile Store *** |");
        System.out.println("|-----------------------------------------|\n");
        System.out.println("Select user type (1/2):\n1. Admin\n2. Customer");
        int userType = getUserInput(scanner, 2);

        ServiceReference serviceReference = context.getServiceReference(MobilePhoneService.class.getName());
        if (serviceReference == null) {
            System.out.println("Mobile Phone Service not found.");
            return;
        }
        
        MobilePhoneService service = (MobilePhoneService) context.getService(serviceReference);

        if (userType == 1) {
            handleAdmin(scanner, service);
        } else if (userType == 2) {
            handleCustomer(scanner, service);
        } else {
            System.out.println("Invalid user type.");
        }
    }

    private void handleAdmin(Scanner scanner, MobilePhoneService service) {
        boolean loginSuccess = false;
        while (!loginSuccess) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            if (service.login(username, password)) {
                loginSuccess = true;
                System.out.println("|--------------------------------------|");
                System.out.println("|***** Welcome to the Admin Panel *****|");
                System.out.println("|--------------------------------------|\n");
                adminPanel(scanner, service);
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    private void adminPanel(Scanner scanner, MobilePhoneService service) {
        boolean running = true;
        while (running) {
            System.out.println("1. Add Mobile\n2. Remove Mobile\n3. View Product List\n4. Logout");
            int choice = getUserInput(scanner, 4);

            switch (choice) {
                case 1:
                    addMobile(scanner, service);
                    break;
                case 2:
                    removeMobile(scanner, service);
                    break;
                case 3:
                    service.printMobilesList();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void handleCustomer(Scanner scanner, MobilePhoneService service) {
        service.printMobilesList();
        boolean shopping = true;
        while (shopping) {
            System.out.println("Select action:\n1. Make Purchase\n2. View Specs\n3. Confirm Checkout");
            int action = getUserInput(scanner, 3);

            switch (action) {
                case 1:
                    makePurchase(scanner, service);
                    break;
                case 2:
                    viewSpecs(scanner, service);
                    break;
                case 3:
                    System.out.println(service.generateInvoice());
                    shopping = false;
                    System.out.println("|---------------------------------------------------------------|");
                    break;
                default:
                    System.out.println("Invalid action.");
                    break;
            }
        }
    }

    private int getUserInput(Scanner scanner, int maxOption) {
        int input = 0;
        boolean valid = false;
        while (!valid) {
            try {
                input = scanner.nextInt();
                scanner.nextLine(); 
                if (input >= 1 && input <= maxOption) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + maxOption + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
        return input;
    }

    
    private void addMobile(Scanner scanner, MobilePhoneService service) {
        System.out.println("Enter model:");
        String model = scanner.nextLine();
        System.out.println("Enter price:");
        while (!scanner.hasNextFloat()) {
            System.out.println("Invalid input. Please enter a valid price.");
            scanner.next(); 
        }
        float price = scanner.nextFloat();
        scanner.nextLine(); 
        System.out.println("Enter specs:");
        String specs = scanner.nextLine();
        service.addMobile(model, price, specs);
    }

    private void removeMobile(Scanner scanner, MobilePhoneService service) {
        service.printMobilesList();
        System.out.println("Enter mobile ID to remove:");
        int itemId = getUserInput(scanner, service.getMobilesCount()); 
        service.removeMobile(itemId);
    }

  
    private void makePurchase(Scanner scanner, MobilePhoneService service) {
        service.printMobilesList();
        System.out.println("Enter Product ID No. for purchase:");
        int itemId = getUserInput(scanner, service.getMobilesCount()); 
        System.out.println("No. of quantities:");
        int qty = getUserInput(scanner, Integer.MAX_VALUE); 
        service.addToCart(itemId, qty);
        System.out.println("Product Added to cart.");
    }

    private void viewSpecs(Scanner scanner, MobilePhoneService service) {
        System.out.println("Enter Product ID to view specs:");
        int itemId = getUserInput(scanner, service.getMobilesCount()); 
        System.out.println(service.getMobileSpecs(itemId));
    	System.out.println("\n|--------------------------------------------------------------|");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Samsung Mobile Store Service Stopped");
    }
}






