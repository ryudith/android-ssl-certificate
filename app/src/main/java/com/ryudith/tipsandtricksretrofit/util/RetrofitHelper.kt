package com.ryudith.tipsandtricksretrofit.util

import android.util.Log
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.ryudith.tipsandtricksretrofit.R
import com.ryudith.tipsandtricksretrofit.custom.CustomURL
import com.ryudith.tipsandtricksretrofit.custom.Movie
import com.ryudith.tipsandtricksretrofit.custom.MovieResponse
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


private const val TAG = "DEBUG_DATA"
class RetrofitHelper {
    companion object {
        var BASEURL = "https://10.0.2.2/simple_php/"
        private var INSTANCE : Retrofit? = null

        fun createInstance(newInstance : Boolean = false, client : OkHttpClient? = null) : Retrofit {
            var newClient : OkHttpClient? = client
            if (newClient == null) {
                newClient = OkHttpClient.Builder().build()
            }

            val movieRespConverter = GsonBuilder().registerTypeAdapter(MovieResponse::class.java, MovieResponseConverter()).create()
            val movieRespList = GsonBuilder().registerTypeAdapter(Movie::class.java, MovieListConverter()).create()
            if (newInstance) {
                return Retrofit.Builder()
                    .baseUrl(RetrofitHelper.BASEURL)
                    .client(newClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(movieRespList))
//                    .addConverterFactory(GsonConverterFactory.create(movieRespConverter))
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            if (INSTANCE != null) {
                return INSTANCE!!
            }

            INSTANCE = Retrofit.Builder()
                .baseUrl(RetrofitHelper.BASEURL)
                .client(newClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(movieRespList))
//                .addConverterFactory(GsonConverterFactory.create(movieRespConverter))
//                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return INSTANCE!!
        }

        fun getRetrofitRef() : CustomURL {
            val client = OkHttpClient.Builder()
            client.followRedirects(false)
            client.followSslRedirects(false)
            client.connectTimeout(2, TimeUnit.SECONDS)
            client.callTimeout(5, TimeUnit.SECONDS)

            // create self-signed server certificate
            val cf = CertificateFactory.getInstance("X.509")
            val cert = MainApplication.context?.resources?.openRawResource(R.raw.ryudith)
            try {
                val ca = cf.generateCertificate(cert)
                val keyStoreType = KeyStore.getDefaultType()
                val keyStore = KeyStore.getInstance(keyStoreType)
                keyStore.load(null, null)
                keyStore.setCertificateEntry("ca", ca)

                val tmfAlgo = TrustManagerFactory.getDefaultAlgorithm()
                val tmf = TrustManagerFactory.getInstance(tmfAlgo)
                tmf.init(keyStore)

                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, tmf.trustManagers, null)

                client.sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
            } finally {
                if (cert != null) {
                    cert.close()
                }
            }

            val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.context)

            val reqInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val req = chain.request().newBuilder()

                    // add custom header to request
                    req.addHeader("Authorization", "Basic 123")
                    req.addHeader("User-Agent", "Google Chrome")

                    // send back cookie
                    req.addHeader("Cookie", pref.getString("cookies", "")!!)

                    // change request url
                    val url = chain.request().url.newBuilder()
                    url.addQueryParameter("req_type", "register")
                    url.addQueryParameter("is_admin", "no")
                    url.addPathSegments("newpath/form-data")
//                    url.addPathSegment("newpath")
                    req.url(url.build())

                    // change method and add post data
                    val form = MultipartBody.Builder()
                    form.setType("multipart/form-data".toMediaType())
                    form.addFormDataPart("name", "Ryudith")
                    form.addFormDataPart("email", "tester1@example.com")
                    form.addFormDataPart("password", "12345")
                    form.addFormDataPart("password_confirmation", "12345")
                    req.method("POST", form.build())

                    return chain.proceed(req.build())
                }
            }
            client.addInterceptor(reqInterceptor)

            val respIntercepter = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val resp = chain.proceed(chain.request())
                    val respBody = resp.body!!.string()
                    val respBodyType = resp.body!!.contentType()
                    var newRespBody = respBody.toResponseBody(respBodyType)
                    
                    // get raw server response
                    Log.d(TAG, "raw response server : ${respBody}")

                    // change response string
                    if (resp.code != 200 || respBody.compareTo("") == 0) {
                        newRespBody = "{\"status\":0, \"message\":\"response not 200 or empty\", \"data\":{}}".toResponseBody("application/json".toMediaType())
                    }

                    // get cookie
                    val cookies = resp.header("Set-Cookie", null)
                    if (cookies != null) {
                        val editor = pref.edit()
                        editor.putString("cookies", cookies!!)
                        editor.apply()
                    }
                    
                    return resp.newBuilder().body(newRespBody).build()
                }
            }
            client.addInterceptor(respIntercepter)
            
            return RetrofitHelper.createInstance(false, client.build()).create(CustomURL::class.java)
        }
    }
}