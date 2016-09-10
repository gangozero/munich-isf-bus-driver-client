package io.gangozero.isfdriver.di.modules;

import dagger.Module;
import dagger.Provides;
import io.gangozero.isfdriver.managers.RestService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

/**
 * Created by eleven on 10/09/2016.
 */
@Module
public class ProdRestServiceModule {
	@Provides @Singleton public RestService provideRestManager() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//		interceptor.setLevel(RestAdapter.LogLevel.FULL);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		return new Retrofit.Builder()
				.baseUrl("https://vlrbx87trk.execute-api.eu-west-1.amazonaws.com")
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build().create(RestService.class);
	}
}
