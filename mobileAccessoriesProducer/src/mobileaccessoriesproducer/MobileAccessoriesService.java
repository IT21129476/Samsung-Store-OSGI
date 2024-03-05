package mobileaccessoriesproducer;

public interface MobileAccessoriesService {
	public void defaultList();
	public void printAccessoriesList();
	public String LoginVerification(String username,String password);
	public void addAccessories(String model, float Price);
	public void removeAccessories(int itemId);
	public int getListSize();
	public float calculateBill(int itemId, int Qty, int count);
	public float calcSubTotal(float total, float tax);
	public float calcBalance(float subTotal, float cash);

}
