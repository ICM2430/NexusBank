package com.example.snaphunters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0012H\u0014J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0004H\u0002J\u0018\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004H\u0002J\u0012\u0010 \u001a\u00020\u00122\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002J\u0010\u0010#\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0004H\u0002J\u0018\u0010$\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/example/snaphunters/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "Users", "", "getUsers", "()Ljava/lang/String;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/example/snaphunters/databinding/ActivityLoginBinding;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "fingerprintHelper", "Lcom/example/snaphunters/FingerprintHelper;", "myRef", "Lcom/google/firebase/database/DatabaseReference;", "authenticateWithFingerprint", "", "checkBiometricSupport", "isUserLoggedIn", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "setupClickListeners", "showToast", "message", "signIn", "email", "password", "updateUI", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "validEmailAddress", "validateForm", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.snaphunters.databinding.ActivityLoginBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.example.snaphunters.FingerprintHelper fingerprintHelper;
    private com.google.firebase.database.FirebaseDatabase database;
    private com.google.firebase.database.DatabaseReference myRef;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String Users = "users/";
    
    public LoginActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUsers() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void checkBiometricSupport() {
    }
    
    private final void authenticateWithFingerprint() {
    }
    
    private final void updateUI(com.google.firebase.auth.FirebaseUser currentUser) {
    }
    
    @java.lang.Override
    protected void onStart() {
    }
    
    private final void signIn(java.lang.String email, java.lang.String password) {
    }
    
    private final boolean validateForm(java.lang.String email, java.lang.String password) {
        return false;
    }
    
    private final boolean validEmailAddress(java.lang.String email) {
        return false;
    }
    
    private final void showToast(java.lang.String message) {
    }
    
    private final boolean isUserLoggedIn() {
        return false;
    }
}