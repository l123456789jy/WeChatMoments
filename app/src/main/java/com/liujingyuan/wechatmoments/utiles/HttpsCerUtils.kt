package com.liujingyuan.wechatmoments.utiles

import android.content.Context

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

import okhttp3.OkHttpClient

/**
 * 证书逻辑处理相关
 */
object HttpsCerUtils {
    /**
     * 信任所有证书
     * @param okHttpClientBuilder Builder
     */
    fun setTrustAllCertificate(okHttpClientBuilder: OkHttpClient.Builder) {
        try {
            val sc = SSLContext.getInstance("TLS")
            val trustAllManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
            }
            sc.init(null, arrayOf<TrustManager>(trustAllManager), SecureRandom())
            okHttpClientBuilder.sslSocketFactory(sc.socketFactory, trustAllManager)
            okHttpClientBuilder.hostnameVerifier { _, _ -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
