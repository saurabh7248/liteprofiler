package org.dexter;

import net.bytebuddy.asm.Advice;

import java.time.Instant;

public class ProfileMethodAdvice {

    @Advice.OnMethodEnter
    public static long onMethodEnter() {
        return Instant.now().toEpochMilli();
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Enter long startTime, @Advice.Origin("#t:#m") String methodName) {
        long timeExecuted = Instant.now().toEpochMilli() - startTime;
        System.out.println(methodName + "," + timeExecuted + "ms");
    }

}