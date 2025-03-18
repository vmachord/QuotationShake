package dadm.vamactor.quotationshake.ui.favourites

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

class DeleteAllDialogFragment: DialogFragment() {
    private val favouritesViewModel: FavouritesViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Delete all favourite quotations")
                .setMessage("Do you really want to delete all your quotations?")
                .setPositiveButton("Yes") { dialog, _ ->
                    favouritesViewModel.deleteAllQuotations()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}