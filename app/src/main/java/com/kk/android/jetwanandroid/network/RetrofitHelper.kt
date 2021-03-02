package com.kk.android.jetwanandroid.network

import com.kk.android.jetwanandroid.utils.SingletonHelperArg0
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kuky.
 * @description
 */
class RetrofitHelper private constructor() {
    companion object : SingletonHelperArg0<RetrofitHelper>(::RetrofitHelper)

    @Volatile
    private var retrofit: Retrofit? = null
    private val mBaseUrl = "https://www.wanandroid.com"

    /**
     * default support gson converter
     */
    fun retrofitProvider(): Retrofit {
        check(mBaseUrl.matches(urlRegex)) { "Illegal url: $mBaseUrl" }

        return retrofit ?: synchronized(this) {
            retrofit ?: Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(generateOkHttpClient()).build()
        }
    }

    inline fun <reified T> createApiService(): T = retrofitProvider().create(T::class.java)
}

///////////////////////////////////
// Create Retrofit ApiService ////
/////////////////////////////////
inline fun <reified T> createService(): T {
    return RetrofitHelper.instance().createApiService()
}