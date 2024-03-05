package mobileaccessoriesproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {



	private static BundleContext context;
	ServiceRegistration ServiceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		
		System.out.println("Samsung accessories Service Started");
		MobileAccessoriesService mobileAccessoriesService = new MobileAccessoriesServiceImpl();
		ServiceRegistration = bundleContext.registerService(
				MobileAccessoriesService.class.getName(), mobileAccessoriesService, null);
				
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Samsung accessories Service Stopped");
	}

}
