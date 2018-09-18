package com.github.zaplatynski.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodOrderRandomizerTest {

  private MethodOrderRandomizer testling;
  private List<FrameworkMethod> list;

  @Before
  public void setUp() throws Exception {
    list = new ArrayList<FrameworkMethod>(10);
    for (int i = 0; i < 10; i++) {
      FrameworkMethod test = mock(FrameworkMethod.class);
      when(test.getName()).thenReturn("test"+(i+1));
      list.add(test);
    }

    TestClass testClass = mock(TestClass.class);
    when(testClass.getAnnotatedMethods(Test.class)).thenReturn(list);

    testling = new MethodOrderRandomizer(testClass, 1, System.nanoTime());
  }

  @Test
  public void computeTestMethods() throws Exception {
    final FrameworkMethod[] expected = list.toArray(new FrameworkMethod[0]);

    final List<FrameworkMethod> result = testling.computeTestMethods();

    assertThat("Result is missing some items: " + result, result, containsInAnyOrder(expected));

    assertThat("Result is unexpectly ordered: " + result, result, not(contains(expected)));
  }

}