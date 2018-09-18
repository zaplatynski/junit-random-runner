package com.github.zaplatynski.testing;

import org.junit.runners.model.FrameworkMethod;

import java.util.Comparator;

/**
 * The type Framework method comparator.
 */
public class FrameworkMethodComparator implements Comparator<FrameworkMethod> {

  @Override
  public int compare(final FrameworkMethod first, final FrameworkMethod second) {
    return first.getName().compareTo(second.getName());
  }

}
