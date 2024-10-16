// Generated by view binder compiler. Do not edit!
package com.example.snaphunters.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.snaphunters.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ContactsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView idContacto;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final LinearLayout main;

  @NonNull
  public final TextView nombre;

  private ContactsBinding(@NonNull LinearLayout rootView, @NonNull TextView idContacto,
      @NonNull ImageView imageView, @NonNull LinearLayout main, @NonNull TextView nombre) {
    this.rootView = rootView;
    this.idContacto = idContacto;
    this.imageView = imageView;
    this.main = main;
    this.nombre = nombre;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContactsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContactsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.contacts, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContactsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.idContacto;
      TextView idContacto = ViewBindings.findChildViewById(rootView, id);
      if (idContacto == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      LinearLayout main = (LinearLayout) rootView;

      id = R.id.nombre;
      TextView nombre = ViewBindings.findChildViewById(rootView, id);
      if (nombre == null) {
        break missingId;
      }

      return new ContactsBinding((LinearLayout) rootView, idContacto, imageView, main, nombre);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
