// Generated by view binder compiler. Do not edit!
package com.example.snaphunters.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.snaphunters.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFriendsBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  @NonNull
  public final ListView listContacts;

  @NonNull
  public final FrameLayout main;

  private ActivityFriendsBinding(@NonNull FrameLayout rootView,
      @NonNull BottomNavigationView bottomNavigationView, @NonNull ListView listContacts,
      @NonNull FrameLayout main) {
    this.rootView = rootView;
    this.bottomNavigationView = bottomNavigationView;
    this.listContacts = listContacts;
    this.main = main;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFriendsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_friends, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFriendsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigationView;
      BottomNavigationView bottomNavigationView = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationView == null) {
        break missingId;
      }

      id = R.id.listContacts;
      ListView listContacts = ViewBindings.findChildViewById(rootView, id);
      if (listContacts == null) {
        break missingId;
      }

      FrameLayout main = (FrameLayout) rootView;

      return new ActivityFriendsBinding((FrameLayout) rootView, bottomNavigationView, listContacts,
          main);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
