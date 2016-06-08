#-dontshrink
#-dontoptimize
-useuniqueclassmembernames
-keepattributes SourceFile,LineNumberTable
-allowaccessmodification
-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2

-keepclasseswithmembernames class * {
	native <methods>;
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

#-dontwarn com.google.code.**
#-dontwarn oauth.signpost.**
#-dontwarn twitter4j.**

#-keepattributes SourceFile,LineNumberTable
#-keep class com.parse.*{ *; }
#-dontwarn com.parse.**

-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class javax.ws.rs.** { *; }
-dontwarn org.immutables.gson.**

-dontwarn okio.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions