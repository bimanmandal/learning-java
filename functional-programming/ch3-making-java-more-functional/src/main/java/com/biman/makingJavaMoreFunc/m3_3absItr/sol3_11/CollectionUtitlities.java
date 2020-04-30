package com.biman.makingJavaMoreFunc.m3_3absItr.sol3_11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CollectionUtitlities {

  public static <T> List<T> list() {
    return Collections.emptyList();
  }

  public static <T> List<T> list(T t) {
    return Collections.singletonList(t);
  }

  public static <T> List<T> list(List<T> ts) {
    return Collections.unmodifiableList(new ArrayList<>(ts));
  }

  @SafeVarargs
  public static <T> List<T> list(T... t) {
    return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
  }

  public static <T> T head(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("head of empty list");
    }
    return list.get(0);
  }

  private static <T> List<T> copy(List<T> ts) {
    return new ArrayList<>(ts);
  }

  public static <T> List<T> tail(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("tail of empty list");
    }
    List<T> workList = copy(list);
    workList.remove(0);
    return Collections.unmodifiableList(workList);
  }

  public static <T> List<T> append(List<T> list, T t) {
    List<T> ts = copy(list);
    ts.add(t);
    return Collections.unmodifiableList(ts);
  }

  public static Integer fold(List<Integer> is, Integer identity, Function<Integer, Function<Integer, Integer>> f) {
    int result = identity;
    for (Integer i : is) {
      result = f.apply(result).apply(i);
    }
    return result;
  }

  public static <T, U> U foldLeft(List<T> ts, U identity, Function<U, Function<T, U>> f) {
    U result = identity;
    for (T t : ts) {
      result = f.apply(result).apply(t);
    }
    return result;
  }

  public static <T, U> U foldRight(List<T> ts, U identity, Function<T, Function<U, U>> f) {
    return ts.isEmpty()
        ? identity
        : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
  }

  public static <T> List<T> prepend(T t, List<T> list) {
    return foldLeft(list, list(t), a -> b -> append(a, b));
  }

  public static <T> List<T> reverse(List<T> list) {
    return foldLeft(list, list(), x -> y -> prepend(y, x));
  }

  public static <T, U> List<U> mapViaFoldLeft(List<T> list, Function<T, U> f) {
    return foldLeft(list, list(), x -> y -> append(x, f.apply(y)));
  }

  public static <T, U> List<U> mapViaFoldRight(List<T> list, Function<T, U> f) {
    return foldRight(list, list(), x -> y -> prepend(f.apply(x), y));
  }

  public static <T> List<T> unfold(T seed, Function<T, T> f, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    T temp = seed;
    while (p.test(temp)) {
      result = append(result, temp);
      f.apply(temp);
    }
    return result;
  }


}
