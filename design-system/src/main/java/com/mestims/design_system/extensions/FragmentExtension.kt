package com.mestims.design_system.extensions

import android.content.Intent
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mestims.design_system.delegate.FragmentViewBindingDelegate
import java.io.File


inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(T::class.java, this)

fun Fragment.openShare(file: File) {

    val imageUri = FileProvider.getUriForFile(
        requireContext(),
        "com.mestims.marvelapp.provider",  //(use your app signature + ".provider" )
        file
    )

    val share = Intent(Intent.ACTION_SEND)
    share.setType("image/*");
    share.putExtra(Intent.EXTRA_STREAM, imageUri);
    startActivity(Intent.createChooser(share, "Share via"));
}