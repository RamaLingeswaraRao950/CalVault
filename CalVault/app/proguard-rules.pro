# Keep your MainActivity and Application class
-keep public class com.calvault.app.activities.MainActivity
-keep public class com.calvault.app.activities.SetupPasswordActivity
-keep public class com.calvault.app.activities.HiddenVaultActivity
-keep public class com.calvault.app.** { *; }

# Keep exp4j library since it's used for expression evaluation
-keep class net.objecthunter.exp4j.** { *; }
-dontwarn net.objecthunter.exp4j.**

# Keep Google Material components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# Keep Android X components
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep any classes with ViewBinding
-keep class com.calvault.app.databinding.** { *; }

# Keep any callback interfaces
-keep class com.calvault.app.callbacks.** { *; }
-keep interface com.calvault.app.callbacks.** { *; }

# Keep classes used for regex pattern matching
-keep class java.util.regex.** { *; }

# Keep annotation classes
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

# Keep Parcelable classes (might be needed for Intent extras)
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Keep FileManager classes since they work with storage permissions
-keep class com.calvault.app.utils.FileManager { *; }

# Keep DialogUtil since it's used for permission dialogs
-keep class com.calvault.app.utils.DialogUtil { *; }

# Keep PrefsUtil since it's used for password validation
-keep class com.calvault.app.utils.PrefsUtil { *; }

# General Android rules
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# Keep any classes that use reflection
-keepattributes InnerClasses

# Keep R classes and their fields
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep specific activities with special code in onCreate
-keepclassmembers class * extends android.app.Activity {
    public void onCreate(android.os.Bundle);
}
