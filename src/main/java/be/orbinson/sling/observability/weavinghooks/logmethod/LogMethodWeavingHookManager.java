package be.orbinson.sling.observability.weavinghooks.logmethod;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.hooks.weaving.WeavingHook;
import org.osgi.service.component.annotations.*;

import java.util.HashMap;
import java.util.Map;

@Component(immediate = true, service = LogMethodWeavingHookManager.class)
public class LogMethodWeavingHookManager {

    private final Map<LogMethodWeavingHookConfiguration, ServiceRegistration<?>> registrations = new HashMap<>();
    private final BundleContext bundleContext;

    @Activate
    public LogMethodWeavingHookManager(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Reference(service = LogMethodWeavingHookConfiguration.class, cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
    void bindLogMethodWeavingHookConfiguration(LogMethodWeavingHookConfiguration config) {
        LogMethodWeavingHook weavingHook = new LogMethodWeavingHook(config);
        ServiceRegistration<?> reg = bundleContext.registerService(WeavingHook.class.getName(), weavingHook, null);
        registrations.put(config, reg);
    }

    void unbindLogMethodWeavingHookConfiguration(LogMethodWeavingHookConfiguration config) {
        ServiceRegistration<?> reg = registrations.get(config);
        reg.unregister();
        registrations.remove(config);
    }
}