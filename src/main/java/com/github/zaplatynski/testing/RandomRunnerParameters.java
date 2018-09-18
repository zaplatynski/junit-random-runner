package com.github.zaplatynski.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Random runner parameters.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RandomRunnerParameters {
  /**
   * Run in parallel if true.
   *
   * @return the boolean
   */
  boolean parallel() default false;

  /**
   * Seed for random.
   *
   * @return the long
   */
  long seed() default 0;

  /**
   * How many times to run the same test.
   *
   * @return the int
   */
  int times() default 1;
}
