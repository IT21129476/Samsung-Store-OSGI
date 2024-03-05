package serviceproducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SamsungServiceImpl implements SamsungService {
    private final Map<String, Double> services = new HashMap<>();
    private final Map<String, List<RepairRequest>> customerRepairs = new HashMap<>();

    public SamsungServiceImpl() {
      
        addService("Screen Replacement", 120.0);
        addService("Battery Replacement", 80.0);
        addService("Software Update", 50.0);
        addService("Camera Repair", 100.0);
        addService("Charging Port Repair", 90.0);
    }

    @Override
    public void addService(String service, double price) {
        services.put(service, price);
    }

    @Override
    public void removeService(String service) {
        services.remove(service);
    }

    @Override
    public void listServices() {
    	System.out.println("-----------------------------------------------");
        System.out.println("************** Samsung Services ************** ");
        System.out.println("-----------------------------------------------");
        services.forEach((service, price) -> System.out.println("- " + service + ": RS" + price));
    }

    @Override
    public String requestRepair(String name, String mobile, String service) {
        if (!services.containsKey(service)) {
            return "Service not found.";
        }
        String customerId = name + "-" + mobile; 
        RepairRequest request = new RepairRequest(name, mobile, service, services.get(service));

        customerRepairs.computeIfAbsent(customerId, k -> new ArrayList<>()).add(request);
        return "Repair requested for " + service + " by " + name;
    }

    @Override
    public void generateInvoice(String customerId) {
        List<RepairRequest> repairs = customerRepairs.get(customerId);
        if (repairs == null || repairs.isEmpty()) {
            System.out.println("No repairs found for customer.");
            return;
        }
        System.out.println("\n----------------------------------------");
        System.out.println("************* Repair Invoice *************");
        System.out.println("\n----------------------------------------");
        System.out.println("Customer: " + repairs.get(0).name + ", Mobile: " + repairs.get(0).mobile);
        double subtotal = 0.0;
        for (RepairRequest repair : repairs) {
            System.out.println("- Service: " + repair.service + ", Cost: RS" + repair.cost);
            subtotal += repair.cost;
        }
        System.out.println("Subtotal: RS " + subtotal);
        double discount = subtotal * 0.1; // 10% discount
        System.out.println("Discount ( 10% ): RS " + discount);
        double total = subtotal - discount;
        System.out.println("Total after discount : RS " + total);
        System.out.println("Thank you for choosing us!");
        System.out.println("\n----------------------------------------\n");
    }

    @Override
    public boolean login(String username, String password) {
        return "manager".equals(username) && "123".equals(password);
    }

    private static class RepairRequest {
        String name, mobile, service;
        double cost;

        RepairRequest(String name, String mobile, String service, double cost) {
            this.name = name;
            this.mobile = mobile;
            this.service = service;
            this.cost = cost;
        }
    }
}
