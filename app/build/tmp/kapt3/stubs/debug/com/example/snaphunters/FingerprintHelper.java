package com.example.snaphunters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/snaphunters/FingerprintHelper;", "", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "(Landroidx/appcompat/app/AppCompatActivity;)V", "biometricPrompt", "Landroidx/biometric/BiometricPrompt;", "callback", "Lcom/example/snaphunters/FingerprintHelper$AuthenticationCallback;", "executor", "Ljava/util/concurrent/Executor;", "promptInfo", "Landroidx/biometric/BiometricPrompt$PromptInfo;", "authenticate", "", "showToast", "message", "", "AuthenticationCallback", "app_debug"})
public final class FingerprintHelper {
    @org.jetbrains.annotations.NotNull
    private final androidx.appcompat.app.AppCompatActivity activity = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.concurrent.Executor executor = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.biometric.BiometricPrompt biometricPrompt = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.biometric.BiometricPrompt.PromptInfo promptInfo = null;
    @org.jetbrains.annotations.Nullable
    private com.example.snaphunters.FingerprintHelper.AuthenticationCallback callback;
    
    public FingerprintHelper(@org.jetbrains.annotations.NotNull
    androidx.appcompat.app.AppCompatActivity activity) {
        super();
    }
    
    public final void authenticate(@org.jetbrains.annotations.NotNull
    com.example.snaphunters.FingerprintHelper.AuthenticationCallback callback) {
    }
    
    private final void showToast(java.lang.String message) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&\u00a8\u0006\u0007"}, d2 = {"Lcom/example/snaphunters/FingerprintHelper$AuthenticationCallback;", "", "onAuthenticationError", "", "errorMessage", "", "onAuthenticationSuccess", "app_debug"})
    public static abstract interface AuthenticationCallback {
        
        public abstract void onAuthenticationSuccess();
        
        public abstract void onAuthenticationError(@org.jetbrains.annotations.NotNull
        java.lang.String errorMessage);
    }
}