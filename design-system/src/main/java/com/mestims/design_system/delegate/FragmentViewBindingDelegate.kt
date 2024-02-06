package com.mestims.design_system.delegate

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    bindingClass: Class<T>,
    fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null
    private var bindingMethod = bindingClass.getMethod("bind", View::class.java)

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { owner ->
            owner.lifecycle.addObserver(LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    binding = null
                }
            })
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        binding ?: thisRef.view?.let { view ->
            (bindingMethod.invoke(null, view) as T).also {
                (it as? ViewDataBinding)?.apply {
                    lifecycleOwner = thisRef.viewLifecycleOwner
                }
                binding = it
            }
        }
        ?: throw Exception("Cannot Access view binding when view is null (before onCreateView or after onDestroyView")

}