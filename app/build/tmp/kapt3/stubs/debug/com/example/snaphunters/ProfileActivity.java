package com.example.snaphunters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0002J\u0012\u0010\u0013\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u0006\u0010\u0016\u001a\u00020\u0011J\b\u0010\u0017\u001a\u00020\u0011H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0002J\u0010\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u000bH\u0002J\b\u0010\u001e\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/snaphunters/ProfileActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/example/snaphunters/databinding/ActivityProfileBinding;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "getImage", "Landroidx/activity/result/ActivityResultLauncher;", "", "myRef", "Lcom/google/firebase/database/DatabaseReference;", "selectedImageUri", "Landroid/net/Uri;", "loadImage", "", "uri", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "readOnce", "redirectToLogin", "setupBottomNavigation", "updateUI", "user", "Lcom/google/firebase/auth/FirebaseUser;", "updateUserProfilePicture", "imageUrl", "uploadImageToFirebaseStorage", "Companion", "app_debug"})
public final class ProfileActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.snaphunters.databinding.ActivityProfileBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    @org.jetbrains.annotations.Nullable
    private android.net.Uri selectedImageUri;
    private com.google.firebase.database.FirebaseDatabase database;
    private com.google.firebase.database.DatabaseReference myRef;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> getImage = null;
    private static final int REQUEST_CODE = 101;
    @org.jetbrains.annotations.NotNull
    public static final com.example.snaphunters.ProfileActivity.Companion Companion = null;
    
    public ProfileActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateUI(com.google.firebase.auth.FirebaseUser user) {
    }
    
    private final void redirectToLogin() {
    }
    
    private final void setupBottomNavigation() {
    }
    
    private final void uploadImageToFirebaseStorage() {
    }
    
    private final void updateUserProfilePicture(java.lang.String imageUrl) {
    }
    
    public final void readOnce() {
    }
    
    private final void loadImage(android.net.Uri uri) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/snaphunters/ProfileActivity$Companion;", "", "()V", "REQUEST_CODE", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}