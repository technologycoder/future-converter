package net.javacrumbs.futureconverter.java8common;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletionStage;

public class CompletionStageFactoryTest extends AbstractCompletionStageTest {
    private final CompletionStageFactory factory = new CompletionStageFactory();

    protected CompletionStage<String> createFinishedCompletionStage() {
        ListenableFuture<String> listenableFuture = Futures.immediateFuture(VALUE);
        return createCompletionStage(listenableFuture);
    }

    protected CompletionStage<String> createExceptionalCompletionStage() {
        ListenableFuture<String> listenableFuture = Futures.immediateFailedFuture(EXCEPTION);
        return createCompletionStage(listenableFuture);
    }


    private CompletionStage<String> createCompletionStage(ListenableFuture<String> listenableFuture) {
        return factory.createCompletableFuture((onSuccess, onError) -> {
            Futures.addCallback(listenableFuture, new FutureCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    onSuccess.accept(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    onError.accept(t);
                }
            });
        });
    }

}