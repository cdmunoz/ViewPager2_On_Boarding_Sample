package co.cdmunoz.vp2onboarding.utils

import android.content.ContentResolver
import android.net.Uri
import android.widget.ImageView

fun ImageView.setImageFromRaw(rawId: Int) {
    val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + rawId)
    GlideApp.with(context).load(uri).into(this)
}