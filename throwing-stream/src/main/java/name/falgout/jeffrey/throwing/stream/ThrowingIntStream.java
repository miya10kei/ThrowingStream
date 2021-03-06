package name.falgout.jeffrey.throwing.stream;

import java.util.function.Function;
import java.util.function.IntFunction;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingIntFunction;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingIntStreamIntermediate;
import name.falgout.jeffrey.throwing.stream.terminal.ThrowingIntStreamTerminal;

public interface ThrowingIntStream<X extends Throwable>
    extends ThrowingBaseStream<Integer, X, ThrowingIntStream<X>>,
    ThrowingIntStreamIntermediate<X, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>>,
    ThrowingIntStreamTerminal<X> {
  @Override
  public ThrowingIterator.OfInt<X> iterator();

  @Override
  public ThrowingBaseSpliterator.OfInt<X> spliterator();

  @Override
  default public <U> ThrowingStream<U, X> normalMapToObj(IntFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends X> mapper);

  @Override
  public ThrowingStream<Integer, X> boxed();

  @Override
  public <Y extends Throwable> ThrowingIntStream<Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);
}
