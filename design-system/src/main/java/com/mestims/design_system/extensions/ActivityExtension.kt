package com.mestims.design_system.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mestims.design_system.delegate.ActivityViewBindingDelegate

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding() =
    ActivityViewBindingDelegate(T::class.java)