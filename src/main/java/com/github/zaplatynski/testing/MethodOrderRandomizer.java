package com.github.zaplatynski.testing;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The type Method order randomizer.
 */
public class MethodOrderRandomizer {
  private final TestClass testClass;
  private final int times;
  private final FrameworkMethodComparator methodComparator;
  private final Random random;

  /**
   * Instantiates a new Method order randomizer.
   *
   * @param testClass the test class
   * @param times     the times
   * @param seed      the seed
   */
  public MethodOrderRandomizer(final TestClass testClass, final int times, final long seed) {
    this.testClass = Objects.requireNonNull(testClass, "TestClass is null");
    this.times = times;
    random = new Random(seed);
    methodComparator = new FrameworkMethodComparator();
  }

  /**
   * Compute test methods list.
   *
   * @return the list
   */
  public List<FrameworkMethod> computeTestMethods() {
    List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Test.class);
    return computeTestMethods(methods);
  }

  /**
   * Compute test methods list.
   *
   * @param methods the methods
   * @return the list
   */
  public List<FrameworkMethod> computeTestMethods(final List<FrameworkMethod> methods) {
    Objects.requireNonNull(methods, "List of methods is null!");
    final List<FrameworkMethod> randomizedMethods = new ArrayList<>(methods.size() * times);
    for (int i = 0; i < times; i++) {
      randomizedMethods.addAll(methods);
    }
    Collections.sort(randomizedMethods, methodComparator);
    Collections.shuffle(randomizedMethods, random);
    return randomizedMethods;
  }

}
