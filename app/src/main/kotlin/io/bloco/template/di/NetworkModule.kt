package io.bloco.template.di

import android.content.Context
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.bloco.template.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECTION_TIME = 20L
private const val CACHE_SIZE = (50 * 1024 * 1024).toLong()

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        JackSonModule::class
    ]
)
object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
                readTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
                writeTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
                followRedirects(true)
                followSslRedirects(true)
                cache(null)
                retryOnConnectionFailure(false)
                addInterceptor(httpLoggingInterceptor)
            }
            .cache(cache)
            .build()
    }

    @Provides
    fun provideCache(file: File): Cache {
        return Cache(file, CACHE_SIZE)
    }

    @Provides
    fun provideFile(
        @ApplicationContext context: Context
    ): File {
        return File(context.cacheDir, "workllama_api_cache")
    }

    @Singleton
    @Provides
    fun createRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        converterFactory: JacksonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(okHttpClient.get())
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun baseUrl() = BuildConfig.HOST
}
