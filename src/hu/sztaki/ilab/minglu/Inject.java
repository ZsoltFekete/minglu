package hu.sztaki.ilab.minglu;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
  String value();
}

