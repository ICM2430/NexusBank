package com.example.snaphunters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rH\u0002J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018H\u0002J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0012\u0010!\u001a\u00020\u001d2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0014J\b\u0010$\u001a\u00020\u001dH\u0014J\b\u0010%\u001a\u00020\u001dH\u0014J\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010\'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020\u001dH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/example/snaphunters/CompassActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/hardware/SensorEventListener;", "()V", "accelerometer", "Landroid/hardware/Sensor;", "accelerometerReading", "", "binding", "Lcom/example/snaphunters/databinding/ActivityCompassBinding;", "compassImageView", "Landroid/widget/ImageView;", "currentDegree", "", "directionStatusTextView", "Landroid/widget/TextView;", "directionTextView", "magnetometer", "magnetometerReading", "orientationValues", "rotationMatrix", "sensorManager", "Landroid/hardware/SensorManager;", "getDirection", "", "degree", "getDirectionStatus", "direction", "onAccuracyChanged", "", "sensor", "acurracy", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "updateCompass", "app_debug"})
public final class CompassActivity extends androidx.appcompat.app.AppCompatActivity implements android.hardware.SensorEventListener {
    private com.example.snaphunters.databinding.ActivityCompassBinding binding;
    private android.hardware.SensorManager sensorManager;
    @org.jetbrains.annotations.Nullable
    private android.hardware.Sensor accelerometer;
    @org.jetbrains.annotations.Nullable
    private android.hardware.Sensor magnetometer;
    private android.widget.ImageView compassImageView;
    private android.widget.TextView directionTextView;
    private android.widget.TextView directionStatusTextView;
    @org.jetbrains.annotations.NotNull
    private final float[] accelerometerReading = null;
    @org.jetbrains.annotations.NotNull
    private final float[] magnetometerReading = null;
    @org.jetbrains.annotations.NotNull
    private final float[] rotationMatrix = null;
    @org.jetbrains.annotations.NotNull
    private final float[] orientationValues = null;
    private float currentDegree = 0.0F;
    
    public CompassActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    @java.lang.Override
    protected void onPause() {
    }
    
    @java.lang.Override
    public void onSensorChanged(@org.jetbrains.annotations.NotNull
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override
    public void onAccuracyChanged(@org.jetbrains.annotations.NotNull
    android.hardware.Sensor sensor, int acurracy) {
    }
    
    private final void updateCompass() {
    }
    
    private final java.lang.String getDirection(float degree) {
        return null;
    }
    
    private final java.lang.String getDirectionStatus(java.lang.String direction) {
        return null;
    }
}