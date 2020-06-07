package decision.service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceLocator {


    static class Holder
    {
        static ServiceLocator serviceLocator = new ServiceLocator();
    }

    private ServiceLocator() {}

    public static ServiceLocator getInstance()
    {
        return Holder.serviceLocator;
    }

    Map<String,Service> services = new HashMap<>();

    public Optional<Service> get(String name)
    {
        Service tmp = services.get(name);
        Optional<Service> service = (tmp==null?Optional.empty():Optional.of(tmp));

        return service;
    }


}
