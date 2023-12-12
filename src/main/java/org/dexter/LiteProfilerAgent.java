package org.dexter;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Map;

public class LiteProfilerAgent {

    static ProfilingConfiguration profilingConfiguration;

    public static void premain(String args, Instrumentation instrumentation) {
        if (args != null && !args.isBlank()) {
            String[] agentArguments = args.split(",");
            String configurationFilePath = agentArguments[0];
            try {
                ConfigurationParser configurationParser = new ConfigurationParser(new BufferedReader(new FileReader(configurationFilePath)));
                profilingConfiguration = configurationParser.parse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (profilingConfiguration != null) {
            AgentBuilder defaultAgent = new AgentBuilder.Default();
            defaultAgent = setListener(defaultAgent, profilingConfiguration);
            Map<String, List<String>> classWiseMethodNames = profilingConfiguration.getClassWiseMethodNames();
            for (Map.Entry<String, List<String>> classWiseEntry : classWiseMethodNames.entrySet()) {
                String className = classWiseEntry.getKey();
                List<String> methodNames = classWiseEntry.getValue();
                defaultAgent.type(ElementMatchers.named(className))
                        .transform((builder, typeDescription, classLoader, javaModule, protectionDomain) ->
                                builder.method(ElementMatchers.namedOneOf(methodNames.toArray(new String[0])))
                                        .intercept(Advice.to(ProfileMethodAdvice.class))).installOn(instrumentation);
            }
        }
    }

    private static AgentBuilder setListener(AgentBuilder agentBuilder, ProfilingConfiguration profilingConfiguration) {
        if (profilingConfiguration.getListenerType() == ListenerType.ERROR) {
            return agentBuilder.with(AgentBuilder.Listener.StreamWriting.toSystemOut().withErrorsOnly());
        } else if (profilingConfiguration.getListenerType() == ListenerType.TRANSFORM) {
            return agentBuilder.with(AgentBuilder.Listener.StreamWriting.toSystemOut().withTransformationsOnly());
        }
        return agentBuilder;
    }

}