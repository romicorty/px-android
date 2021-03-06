# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk/22.3/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-keep class com.mercadopago.android.px.model.** { *; } #dtos
-keep class com.mercadopago.android.px.viewmodel.** { *; } #dtos
-keep class com.mercadopago.android.px.preferences.** { *; } #dtos
-keep class com.mercadopago.android.px.constants.PaymentTypes { *; } #dtos
-keep class com.mercadopago.android.px.plugins.PaymentResultAction { *; } #dtos
-keep class com.mercadopago.android.px.plugins.model.** { *; } #dtos

-dontnote com.mercadopago.android.px.preferences.**
-dontnote com.mercadopago.android.px.model.**
-dontnote com.mercadopago.android.px.viewmodel.**
-dontnote com.mercadopago.android.px.plugins.PaymentResultAction
-dontnote com.mercadopago.android.px.constants.PaymentTypes
-dontnote com.mercadopago.android.px.plugins.model.**
-dontnote com.mercadopago.android.px.util.MercadoPagoESCImpl #se usa esta clase pero no se tiene la lib

######################## Picasso #########################
-dontwarn com.squareup.okhttp.**
-dontnote com.squareup.picasso.Utils
######################## Picasso #########################

######################## Gson ############################
-keep class com.google.gson.internal.LinkedTreeMap { *; }
-dontnote com.google.gson.JsonObject
-dontnote sun.misc.Unsafe
######################## Gson ############################


######################## okhttp ##########################
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontnote okhttp3.internal.platform.AndroidPlatform
-dontnote okhttp3.internal.platform.Platform
-dontnote okhttp3.internal.platform.AndroidPlatform$CloseGuard
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
######################## Okhttp ##########################


######################## Retrofit ########################
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
######################## Retrofit ########################