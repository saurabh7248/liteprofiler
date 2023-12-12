package org.dexter;

import net.bytebuddy.asm.Advice;

public class ProfileMethodAdvice {

    @Advice.OnMethodEnter
    public long onMethodEnter() {
        return 0L;
    }

    @Advice.OnMethodExit
    public void onMethodExit() {

    }

}