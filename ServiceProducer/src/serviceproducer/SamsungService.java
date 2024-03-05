package serviceproducer;

public interface SamsungService {
    void addService(String service, double price);
    void removeService(String service);
    void listServices();
    String requestRepair(String name, String mobile, String service);
    void generateInvoice(String customerId);
    boolean login(String username, String password);
}
