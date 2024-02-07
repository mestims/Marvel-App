@file:Suppress("DEPRECATION")

package com.mestims.design_system.extensions

import android.graphics.Bitmap
import android.os.Environment
import androidx.appcompat.widget.AppCompatImageView
import java.io.File
import java.io.FileOutputStream

fun AppCompatImageView.exportImage(): File {
    this.isDrawingCacheEnabled = true;

    val bitmap = this.drawingCache;
    val root = Environment.getExternalStorageDirectory();
    val cachePath = File(root.absolutePath + "/DCIM/Camera/image.jpg");
    try {
        cachePath.createNewFile();
        val ostream = FileOutputStream(cachePath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
        ostream.close()
    } catch (e: Exception) {
        e.printStackTrace();
    }

    return cachePath

}
