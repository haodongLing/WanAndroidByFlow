package io.github.haodongling.lib.navannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Author: tangyuan
 * Time : 2021/9/7
 * Description:
 */
@Target(ElementType.TYPE)
public @interface FragmentDestination {
    String pageUrl();
    boolean needLogin() default false;
    /*=>startDestination*/
    boolean asStarter() default false;
}
