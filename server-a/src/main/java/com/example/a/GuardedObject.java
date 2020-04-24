package com.example.a;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import org.springframework.web.client.RestTemplate;

/**
 * @author dxd
 * @date 2020/4/21
 */
public class GuardedObject<T> {

  private T data;

  final private Lock lock = new ReentrantLock();

  final private Condition hasDone = lock.newCondition();

  private final static int TIMEOUT = 10;

  private final static Map<Object, GuardedObject> GOS = new ConcurrentHashMap<>();

  public static <T> GuardedObject<T> create(T key) {
    GuardedObject<T> guardedObject = new GuardedObject<>();
    GOS.put(key, guardedObject);
    return guardedObject;
  }

  public static <K, V> void onEvent(K key, V data) {
    GuardedObject guardedObject = GOS.remove(key);
    if (Objects.nonNull(guardedObject)) {
      guardedObject.onChange(data);
    }
  }

  public T get(Predicate<T> predicate) {
    lock.lock();
    try {
      while (!predicate.test(data)) {
        hasDone.await(TIMEOUT, TimeUnit.SECONDS);
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }

    return data;
  }

  private void onChange(T data) {
    lock.lock();
    try {
      this.data = data;
      hasDone.signalAll();
    } finally {
      lock.unlock();
    }
  }

}
