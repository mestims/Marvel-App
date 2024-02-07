package com.mestims.design_system.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mestims.design_system.delegate.ActivityViewBindingDelegate

inline fun <reified T : ViewBinding> Activity.viewBinding() =
    ActivityViewBindingDelegate(T::class.java)