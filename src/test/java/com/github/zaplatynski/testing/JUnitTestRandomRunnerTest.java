package com.github.zaplatynski.testing;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RandomRunnerParameters(parallel = true, times = 3)
@RunWith(JUnitTestRandomRunner.class)
public class JUnitTestRandomRunnerTest {

  private static AtomicInteger counter = new AtomicInteger();

  @Test
  public void test1() throws Exception {
    System.out.println("test1: " + Thread.currentThread().getName());
    counter.incrementAndGet();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    assertThat(counter.intValue(), is(3));
  }
}