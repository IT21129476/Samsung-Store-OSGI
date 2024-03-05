package mobileaccessoriesconsumer;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import mobileaccessoriesproducer.MobileAccessoriesService;

public class Activator implements BundleActivator {

    ServiceReference serviceReference;

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Start Samsung Accessories Consumer Service");
        Scanner scanner = new Scanner(System.in);

        ServiceReference serviceReference = bundleContext.getServiceReference(MobileAccessoriesService.class.getName());
        if (serviceReference == null) {
            System.out.println("Mobile Accessories Service not found");
            return;
        }
        MobileAccessoriesService mobileAccessoriesService = (MobileAccessoriesService) bundleContext.getService(serviceReference);
        mobileAccessoriesService.defaultList();

        while (true) {
            System.out.println("Select User Type");
            System.out.println("1.Admin\n2.Customer");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("Admin")) {
                handleAdminOperations(scanner, mobileAccessoriesService);
                break; 
            } else if (userType.equalsIgnoreCase("Customer")) {
                handleCustomerOperations(scanner, mobileAccessoriesService);
                break; 
            } else {
                System.out.println("Invalid user type. Please enter 'Admin' or 'Customer'.");
            }
        }
    }

    private void handleAdminOperations(Scanner scanner, MobileAccessoriesService service) {
        while (true) {
            System.out.println("============== Login ==============");
            System.out.print("Enter User Name : ");
            String username = scanner.nextLine();
            System.out.print("Enter Password : ");
            String password = scanner.nextLine();

            String choice = service.LoginVerification(username, password);
            if (choice.equals("manager")) {
                System.out.println("****** Welcome To System ******");
                adminOptionsLoop(scanner, service);
                break; 
            } else {
                System.out.println("Login failed, invalid credentials.");
            }
        }
    }

    private void adminOptionsLoop(Scanner scanner, MobileAccessoriesService service) {
        while (true) {
            System.out.println("-------------------------------------------------");
            System.out.println("Choose your option:");
            System.out.println("1. Add Item\n2. Remove Item\n3. Logout");
            int ad_type = safeNextInt(scanner);

            switch (ad_type) {
            case 1:
                System.out.print("Enter model: ");
                String model = scanner.nextLine();
                model = scanner.nextLine(); 
                System.out.print("Enter price: ");
                float price = safeNextFloat(scanner);
                service.addAccessories(model, price);
                service.printAccessoriesList();
                break;
                case 2:
                    service.printAccessoriesList();
                    System.out.print("Enter Index : ");
                    int index = safeNextInt(scanner);
                    service.removeAccessories(index);
                    service.printAccessoriesList();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return; 
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private void handleCustomerOperations(Scanner scanner, MobileAccessoriesService service) {
        do {
            System.out.println("Choose your option");
            System.out.println("1.Samsung Phones\n2.Samsung Phone Accessories");
            int option = safeNextInt(scanner);

            switch (option) {
                case 2:
                    customerAccessorySelection(scanner, service);
                    break;
                default:
                    System.out.println("Option not available. Please try again.");
            }
        } while (askForRepeatService(scanner));
        System.out.println("---------Thank you! Enjoy your purchase.------------");
    }

    private void customerAccessorySelection(Scanner scanner, MobileAccessoriesService service) {
        service.printAccessoriesList();
        System.out.println("============Invoice===========");
        float total = 0;
        int count = 0;
        while (true) {
            System.out.print("Enter Accessories Id or '0' to proceed to checkout: ");
            int itemId = safeNextInt(scanner);
            if (itemId == 0) {
                break;
            }
            System.out.print("Enter Qty : ");
            int Qty = safeNextInt(scanner);
            total += service.calculateBill(itemId, Qty, count);
            count++;
        }
        printInvoice(total, scanner, service, count);
    }

    private void printInvoice(float total, Scanner scanner, MobileAccessoriesService service, int count) {
        System.out.println("-------------------------------------------------");
        System.out.println("Total Amount Rs: " + total);
        System.out.print("Enter Tax % : ");
        float tax = safeNextFloat(scanner);
        float subtotal = service.calcSubTotal(total, tax);
        System.out.println("SubTotal - Rs: " + subtotal);
        System.out.print("Cash Amount Rs: ");
        float cash = safeNextFloat(scanner);
        float balance = service.calcBalance(subtotal, cash);
        System.out.println("Balance Rs: " + balance);
        System.out.println("No of Items = " + count);
        System.out.println("___________________________________________________");
    }

    private boolean askForRepeatService(Scanner scanner) {
        System.out.print("Do you want another service (Y/N)? ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("Y");
    }

    private int safeNextInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); 
        }
        return scanner.nextInt();
    }

    private float safeNextFloat(Scanner scanner) {
        while (!scanner.hasNextFloat()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextFloat();
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Stop Samsung Accessories Consumer Service");
    }
}
