// Generated by view binder compiler. Do not edit!
package com.example.snaphunters.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.snaphunters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  @NonNull
  public final Button btnLogOut;

  @NonNull
  public final TextView correo;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView nombreUsuario;

  @NonNull
  public final TextView username;

  private ActivityProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView bottomNavigationView, @NonNull Button btnLogOut,
      @NonNull TextView correo, @NonNull ImageView imageView2, @NonNull ConstraintLayout main,
      @NonNull TextView nombreUsuario, @NonNull TextView username) {
    this.rootView = rootView;
    this.bottomNavigationView = bottomNavigationView;
    this.btnLogOut = btnLogOut;
    this.correo = correo;
    this.imageView2 = imageView2;
    this.main = main;
    this.nombreUsuario = nombreUsuario;
    this.username = username;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigationView;
      BottomNavigationView bottomNavigationView = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationView == null) {
        break missingId;
      }

      id = R.id.btnLogOut;
      Button btnLogOut = ViewBindings.findChildViewById(rootView, id);
      if (btnLogOut == null) {
        break missingId;
      }

      id = R.id.correo;
      TextView correo = ViewBindings.findChildViewById(rootView, id);
      if (correo == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.nombreUsuario;
      TextView nombreUsuario = ViewBindings.findChildViewById(rootView, id);
      if (nombreUsuario == null) {
        break missingId;
      }

      id = R.id.username;
      TextView username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivityProfileBinding((ConstraintLayout) rootView, bottomNavigationView,
          btnLogOut, correo, imageView2, main, nombreUsuario, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
