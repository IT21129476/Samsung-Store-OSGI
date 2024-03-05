package mobilephoneproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	ServiceRegistration ServiceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		
		System.out.println("Samsung Mobile Service Started");
		MobilePhoneService mobilePhoneService = new MobilePhoneServiceImpl();
		ServiceRegistration = bundleContext.registerService(
				MobilePhoneService.class.getName(), mobilePhoneService, null);		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Samsung Mobile Service Stopped");
	}
}
