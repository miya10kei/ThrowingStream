package name.falgout.jeffrey.throwing;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U, X extends Throwable> {
  public void accept(T t, U u) throws X;

  default public BiConsumer<T, U> fallbackTo(BiConsumer<? super T, ? super U> fallback) {
    return fallbackTo(fallback, null);
  }

  default public BiConsumer<T, U> fallbackTo(BiConsumer<? super T, ? super U> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingBiConsumer<T, U, Nothing> t = fallback::accept;
    return orTry(t, thrown)::accept;
  }

  default public <Y extends Throwable> ThrowingBiConsumer<T, U, Y>
      orTry(ThrowingBiConsumer<? super T, ? super U, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingBiConsumer<T, U, Y> orTry(
      ThrowingBiConsumer<? super T, ? super U, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return (t, u) -> {
      ThrowingRunnable<X> s = () -> accept(t, u);
      s.orTry(() -> f.accept(t, u), thrown).run();
    };
  }

  default public <Y extends Throwable> ThrowingBiConsumer<T, U, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t, u) -> {
      ThrowingRunnable<X> s = () -> accept(t, u);
      s.rethrow(x, mapper).run();
    };
  }
}
