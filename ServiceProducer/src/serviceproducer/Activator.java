package serviceproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
   
    private ServiceRegistration registration;

    @Override
    public void start(BundleContext context) throws Exception {
        SamsungServiceImpl service = new SamsungServiceImpl();
     
        registration = context.registerService(SamsungService.class.getName(), service, null);
        System.out.println("Service registered: SamsungService");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (registration != null) {
            registration.unregister();
        }
        System.out.println("Service unregistered: SamsungService");
    }
}
