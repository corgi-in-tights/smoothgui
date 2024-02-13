package dev.reyaan;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GuiWidget {
    boolean serialize() default false;
}
