package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
         request = chain.request().newBuilder()
                .addHeader("X-User-Agent", "contentstack-management-java-"+Constants.SDK_VERSION)
                .addHeader("User-Agent", Util.defaultUserAgent()).build();
        return chain.proceed(request);
    }

}


//static final class ObserveOnMainCallAdapterFactory extends CallAdapter.Factory {
//    final Scheduler scheduler;
//
//    ObserveOnMainCallAdapterFactory(Scheduler scheduler) {
//        this.scheduler = scheduler;
//    }
//
//    @Override
//    public @Nullable CallAdapter<?, ?> get(
//            Type returnType, Annotation[] annotations, Retrofit retrofit) {
//        if (getRawType(returnType) != Observable.class) {
//            return null; // Ignore non-Observable types.
//        }
//
//        // Look up the next call adapter which would otherwise be used if this one was not present.
//        //noinspection unchecked returnType checked above to be Observable.
//        final CallAdapter<Object, Observable<?>> delegate =
//                (CallAdapter<Object, Observable<?>>)
//                        retrofit.nextCallAdapter(this, returnType, annotations);
//
//        return new CallAdapter<Object, Object>() {
//            @Override
//            public Object adapt(Call<Object> call) {
//                // Delegate to get the normal Observable...
//                Observable<?> o = delegate.adapt(call);
//                // ...and change it to send notifications to the observer on the specified scheduler.
//                return o.observeOn(scheduler);
//            }
//
//            @Override
//            public Type responseType() {
//                return delegate.responseType();
//            }
//        };
//    }
//}
