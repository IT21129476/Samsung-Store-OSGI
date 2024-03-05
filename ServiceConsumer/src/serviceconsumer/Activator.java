package serviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import serviceproducer.SamsungService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Activator implements BundleActivator {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("-------------------------------------------");
        System.out.println(" *** Welcome to Samsung Service Center *** ");
        System.out.println("-------------------------------------------");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        String userTypeInput = scanner.nextLine();
        int userType = Integer.parseInt(userTypeInput);

        ServiceReference serviceReference = context.getServiceReference(SamsungService.class.getName());
        if (serviceReference != null) {
            SamsungService service = (SamsungService) context.getService(serviceReference);
            if (userType == 1) {
                adminInteraction(service);
            } else if (userType == 2) {
                customerInteraction(service);
            }
        } else {
            System.out.println("Service not found.");
        }
    }

    private void adminInteraction(SamsungService service) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (service.login(username, password)) {
            String choice;
            do {
                System.out.println("\n1. Add Service");
                System.out.println("2. Remove Service");
                System.out.println("3. List Services");
                System.out.println("4. Logout");
                choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Enter service name:");
                        String serviceName = scanner.nextLine();
                        System.out.println("Enter price:");
                        double price = Double.parseDouble(scanner.nextLine());
                        service.addService(serviceName, price);
                        break;
                    case "2":
                        System.out.println("Enter service name to remove:");
                        serviceName = scanner.nextLine();
                        service.removeService(serviceName);
                        break;
                    case "3":
                        service.listServices();
                        break;
                }
            } while (!choice.equals("4"));
        } else {
            System.out.println("Login failed.");
        }
    }

    private void customerInteraction(SamsungService service) {
        String name, mobile;
        System.out.println("Enter your name:");
        name = scanner.nextLine();
        System.out.println("Enter your mobile number:");
        mobile = scanner.nextLine();
        String customerId = name + "-" + mobile;

        List<String> requestedServices = new ArrayList<>(); 

        int choice;
        do {
            System.out.println("\n1. List Services");
            System.out.println("2. Request Repair");
            System.out.println("3. Exit");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    service.listServices();
                    break;
                case 2:
                    System.out.println("Enter the service name or 0 to Checkout:");
                    String serviceName = scanner.nextLine();
                    while (!"0".equals(serviceName)) {
                        String response = service.requestRepair(name, mobile, serviceName);
                        System.out.println(response);
                        requestedServices.add(serviceName); 
                        System.out.println("Enter another service name or 0 to Checkout:");
                        serviceName = scanner.nextLine();
                    }

                    if (!requestedServices.isEmpty()) {
                        service.generateInvoice(customerId); 
                    }
                    break;
            }
        } while (choice != 3);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Consumer stopped.");
    }
}
