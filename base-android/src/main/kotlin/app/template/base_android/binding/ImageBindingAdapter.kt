package app.template.base_android.binding

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import app.template.base_android.R
import app.template.base_android.extensions.drawableFromViewContext
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.Transformation

@BindingAdapter(
    value = [
        "imageUrl",
        "placeHolder",
        "errorDrawable",
        "dimension",
        "transformation"
    ],
    requireAll = false
)
fun imageLoad(
    view: ImageView,
    imageUrl: String?,
    placeHolder: Drawable?,
    @DrawableRes errorDrawable: Int?,
    dimension: String?,
    transformation: Transformation?
) {
    val errorDrawableValid = view.drawableFromViewContext(errorDrawable ?: R.drawable.ic_error)

    if (imageUrl.isNullOrBlank()) {
        val drawable = errorDrawableValid ?: placeHolder
        view.load(drawable)
    } else {

        val url = if (dimension.isNullOrEmpty())
            imageUrl
        else
            "$imageUrl?$dimension"

        view.load(url) {
            val placeHolderDrawable =
                placeHolder ?: view.drawableFromViewContext(R.drawable.ic_circles_loader)
            placeholder(placeHolderDrawable)

            if (transformation == null)
                transformations(CircleCropTransformation())
            else
                transformations(transformation)
            error(errorDrawableValid)
            scale(Scale.FILL)
        }
    }
}
