package com.example.snaphunters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u0012\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\u0012\u0010\u0013\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/snaphunters/RegisterActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/example/snaphunters/databinding/ActivityRegisterBinding;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "myRef", "Lcom/google/firebase/database/DatabaseReference;", "crearUsuario", "", "email", "", "password", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "updateUI", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "validEmailAddress", "", "validateForm", "app_debug"})
public final class RegisterActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.snaphunters.databinding.ActivityRegisterBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.firebase.database.FirebaseDatabase database;
    private com.google.firebase.database.DatabaseReference myRef;
    
    public RegisterActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void crearUsuario(java.lang.String email, java.lang.String password) {
    }
    
    private final boolean validEmailAddress(java.lang.String email) {
        return false;
    }
    
    private final boolean validateForm(java.lang.String email, java.lang.String password) {
        return false;
    }
    
    private final void updateUI(com.google.firebase.auth.FirebaseUser currentUser) {
    }
}