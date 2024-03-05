package laptoppcproducer;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	ServiceRegistration publisherServiceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		
		System.out.println("Start Samsung Laptop Service");
		SamsungLaptopService samsungLaptopService = new SamsungLaptopServiceImpl();
		publisherServiceRegistration = bundleContext.registerService(
				SamsungLaptopService.class.getName(), samsungLaptopService, null);
				
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Samsung Laptop Service Stopped");
		publisherServiceRegistration.unregister();
	}

}
