package com.github.zaplatynski.testing;

import org.junit.experimental.theories.Theories;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.List;
import java.util.Objects;


/**
 * The type J unit theories random runner.
 */
public class JUnitTheoriesRandomRunner extends Theories {

  private MethodOrderRandomizer randomizer;

  /**
   * Instantiates a new J unit theories random runner.
   *
   * @param klass the klass
   * @throws InitializationError the initialization error
   */
  public JUnitTheoriesRandomRunner(final Class<?> klass) throws InitializationError {
    super(Objects.requireNonNull(klass, "Class is null!"));
  }

  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    final List<FrameworkMethod> frameworkMethods = super.computeTestMethods();
    if (randomizer == null) {
      long seed = Long.valueOf(
          System.getProperty("junit.random.seed",
              String.valueOf(System.nanoTime()))
      );
      int times = 1;
      if (getTestClass().getAnnotation(RandomRunnerParameters.class) != null) {
        final RandomRunnerParameters config = getTestClass()
            .getAnnotation(RandomRunnerParameters.class);
        if (config.parallel()) {
          setScheduler(new ThreadPoolScheduler());
        }
        if (config.seed() > 0) {
          seed = config.seed();
        }
        times = config.times();
      }
      randomizer = new MethodOrderRandomizer(getTestClass(), times, seed);
    }
    return randomizer.computeTestMethods(frameworkMethods);
  }
}
