package com.github.zaplatynski.testing;


import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.List;
import java.util.Objects;

/**
 * The type J unit test random runner.
 */
public class JUnitTestRandomRunner extends BlockJUnit4ClassRunner {

  private MethodOrderRandomizer randomizer;

  /**
   * Creates a BlockJUnit4ClassRunner to run {@code klass}
   *
   * @param klass the klass
   * @throws InitializationError if the test class is malformed.
   */
  public JUnitTestRandomRunner(final Class<?> klass) throws InitializationError {
    super(Objects.requireNonNull(klass, "Class is null!"));
  }

  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    if (randomizer == null) {
      int times = 1;
      long seed = Long.valueOf(
          System.getProperty("junit.random.seed",
              String.valueOf(System.nanoTime()))
      );
      if (getTestClass().getAnnotation(RandomRunnerParameters.class) != null) {
        final RandomRunnerParameters config = getTestClass()
            .getAnnotation(RandomRunnerParameters.class);
        if (config.parallel()) {
          System.out.println("run parallel");
          setScheduler(new ThreadPoolScheduler());
        }
        if (config.seed() > 0) {
          seed = config.seed();
        }
        times = config.times();
      }
      randomizer = new MethodOrderRandomizer(getTestClass(), times, seed);
    }
    return randomizer.computeTestMethods();
  }
}
