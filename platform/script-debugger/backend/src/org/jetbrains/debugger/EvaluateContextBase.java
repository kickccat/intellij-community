package org.jetbrains.debugger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.concurrency.Promise;
import org.jetbrains.debugger.values.ValueManager;

import java.util.Map;

public abstract class EvaluateContextBase<VALUE_MANAGER extends ValueManager> implements EvaluateContext {
  protected final VALUE_MANAGER valueManager;

  protected EvaluateContextBase(@NotNull VALUE_MANAGER valueManager) {
    this.valueManager = valueManager;
  }

  @NotNull
  @Override
  public Promise<EvaluateResult> evaluate(@NotNull String expression) {
    return evaluate(expression, null);
  }

  @NotNull
  @Override
  public EvaluateContext withValueManager(@NotNull String objectGroup) {
    return this;
  }

  @Override
  public void releaseObjects() {
  }

  @NotNull
  @Override
  public abstract Promise<EvaluateResult> evaluate(@NotNull String expression, @Nullable Map<String, EvaluateContextAdditionalParameter> additionalContext);

  @NotNull
  public final VALUE_MANAGER getValueManager() {
    return valueManager;
  }

  @NotNull
  @Override
  public Promise<?> refreshOnDone(@NotNull Promise<?> promise) {
    return promise.then(valueManager.getClearCachesTask());
  }
}