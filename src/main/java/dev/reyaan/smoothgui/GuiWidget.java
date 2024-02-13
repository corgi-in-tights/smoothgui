package dev.reyaan.smoothgui;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GuiWidget {
    boolean serialize() default false;
}
