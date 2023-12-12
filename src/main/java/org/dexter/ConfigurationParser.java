package org.dexter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationParser {

    private final Map<String, List<String>> classWiseMethodNames;
    private String outputFilePath;
    private final BufferedReader bufferedReader;

    private String listenerType;

    public ConfigurationParser(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        classWiseMethodNames = new HashMap<>();
        this.listenerType = null;
    }

    public ProfilingConfiguration parse() throws IOException {
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            if (line.equals("profile-config")) {
                parseProfilingConfiguration();
            } else if (line.equals("output-config")) {
                parseOutputConfiguration();
            } else if (line.startsWith("listener-type:")) {
                listenerType = line.substring(14);
            }
        }
        return new ProfilingConfiguration(classWiseMethodNames, outputFilePath, listenerType);
    }

    private void parseProfilingConfiguration() throws IOException {
        String className = null;
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            if (line.equals("profile-config-end")) {
                return;
            } else if (line.startsWith("class:")) {
                className = line.substring(6);
            } else if (line.startsWith("method:")) {
                classWiseMethodNames.computeIfAbsent(className, x -> new ArrayList<>()).add(line.substring(7));
            }
        }
    }

    private void parseOutputConfiguration() throws IOException {
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            if (line.equals("output-config-end")) {
                return;
            } else if (line.startsWith("output-file-path:")) {
                outputFilePath = line.substring(17);
            }
        }
    }


}