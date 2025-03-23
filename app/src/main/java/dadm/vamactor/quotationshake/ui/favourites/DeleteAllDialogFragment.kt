package dadm.vamactor.quotationshake.ui.favourites

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dadm.vamactor.quotationshake.R

class DeleteAllDialogFragment: DialogFragment() {
    private val favouritesViewModel: FavouritesViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val titleString = it.getString(R.string.delete_all_favourite)
            val messageString = it.getString(R.string.messageString)
            val yes = it.getString(R.string.yes)
            builder.setTitle(titleString)
                .setMessage(messageString)
                .setPositiveButton(yes) { dialog, _ ->
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