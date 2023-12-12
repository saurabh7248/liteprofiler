package org.dexter;

import java.util.List;
import java.util.Map;

public class ProfilingConfiguration {

    private Map<String, List<String>> classWiseMethodNames;
    private String outputLocation;

    public ProfilingConfiguration(Map<String, List<String>> classWiseMethodNames, String outputLocation) {
        this.classWiseMethodNames = classWiseMethodNames;
        this.outputLocation = outputLocation;
    }

    public Map<String, List<String>> getClassWiseMethodNames() {
        return classWiseMethodNames;
    }

    public String getOutputLocation() {
        return outputLocation;
    }

}