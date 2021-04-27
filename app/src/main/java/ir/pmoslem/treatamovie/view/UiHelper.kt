package ir.pmoslem.treatamovie.view

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showBackButtonInToolbar(toolbar: Toolbar){
    val activity = activity as AppCompatActivity
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.setHomeButtonEnabled(true)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun Fragment.showSnackBar(text: String, colorIdResource: Int) {
    if (this.isVisible) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(activity!!, colorIdResource))
            .show()
    }
}
