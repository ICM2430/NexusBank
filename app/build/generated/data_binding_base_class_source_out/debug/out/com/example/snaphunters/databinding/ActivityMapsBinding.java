// Generated by view binder compiler. Do not edit!
package com.example.snaphunters.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.snaphunters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMapsBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  @NonNull
  public final Button btnCompass;

  private ActivityMapsBinding(@NonNull FrameLayout rootView,
      @NonNull BottomNavigationView bottomNavigationView, @NonNull Button btnCompass) {
    this.rootView = rootView;
    this.bottomNavigationView = bottomNavigationView;
    this.btnCompass = btnCompass;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMapsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMapsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_maps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMapsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigationView;
      BottomNavigationView bottomNavigationView = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationView == null) {
        break missingId;
      }

      id = R.id.btnCompass;
      Button btnCompass = ViewBindings.findChildViewById(rootView, id);
      if (btnCompass == null) {
        break missingId;
      }

      return new ActivityMapsBinding((FrameLayout) rootView, bottomNavigationView, btnCompass);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
