package org.dexter;

import java.util.List;
import java.util.Map;

public class ProfilingConfiguration {

    private Map<String, List<String>> classWiseMethodNames;
    private String outputLocation;
    private ListenerType listenerType;


    public ProfilingConfiguration(Map<String, List<String>> classWiseMethodNames, String outputLocation, String listenerType) {
        this.classWiseMethodNames = classWiseMethodNames;
        this.outputLocation = outputLocation;
        this.listenerType = listenerType == null ? ListenerType.NONE : ListenerType.valueOf(listenerType);
    }

    public Map<String, List<String>> getClassWiseMethodNames() {
        return classWiseMethodNames;
    }

    public String getOutputLocation() {
        return outputLocation;
    }

    public ListenerType getListenerType() {
        return listenerType;
    }
}