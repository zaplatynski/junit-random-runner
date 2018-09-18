package com.github.zaplatynski.testing;

import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The type Thread pool scheduler.
 */
public class ThreadPoolScheduler implements RunnerScheduler {

  private final ExecutorService executor;

  /**
   * Instantiates a new Thread pool scheduler.
   */
  public ThreadPoolScheduler() {
    this(System.getProperty("junit.parallel.threads",
        String.valueOf(Runtime.getRuntime().availableProcessors())));
  }

  /**
   * Instantiates a new Thread pool scheduler.
   *
   * @param numThreads the num threads
   */
  public ThreadPoolScheduler(final String numThreads) {
    int numberOfThreads = Integer.valueOf(numThreads);
    executor = Executors.newFixedThreadPool(numberOfThreads);
  }

  /**
   * Schedule.
   *
   * @param childStatement the child statement
   */
  @Override
  public void schedule(final Runnable childStatement) {
    executor.submit(childStatement);
  }

  /**
   * Finished.
   */
  @Override
  public void finished() {
    executor.shutdown();
    try {
      executor.awaitTermination(10, TimeUnit.MINUTES);
    } catch (InterruptedException exc) {
      throw new RuntimeException(exc);
    }
  }
}
