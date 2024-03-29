package com.mestims.design_system.delegate

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class ActivityViewBindingDelegate<T : ViewBinding>(private val bindingClass: Class<T>) :
    ReadOnlyProperty<Activity, T> {

    private var binding: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        binding?.let { return it }

        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)
        val invokeLayout = inflateMethod.invoke(null, thisRef.layoutInflater) as T

        thisRef.setContentView(invokeLayout.root)

        return invokeLayout.also { this.binding = it }
    }

}