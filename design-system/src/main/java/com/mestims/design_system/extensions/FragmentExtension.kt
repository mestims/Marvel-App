package com.mestims.design_system.extensions

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mestims.design_system.delegate.FragmentViewBindingDelegate

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(T::class.java, this)