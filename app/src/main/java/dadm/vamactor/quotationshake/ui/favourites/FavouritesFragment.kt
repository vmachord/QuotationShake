package dadm.vamactor.quotationshake.ui.favourites

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dadm.vamactor.quotationshake.R
import dadm.vamactor.quotationshake.databinding.FragmentFavouritesBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites), MenuProvider {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: QuotationListAdapter

    private val favouritesViewModel: FavouritesViewModel by activityViewModels()

    private val onItemClick: (String) -> Unit = { author ->
        if (author == "Anonymous") {
            Snackbar.make(binding.root, "It is not possible to display information for an anonymous author.", Snackbar.LENGTH_LONG).show()
        } else {
            val searchUrl = "https://en.wikipedia.org/wiki/Special:Search?search=$author"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(binding.root, "It is not possible to handle the requested action.", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        adapter = QuotationListAdapter(onItemClick)

        binding.recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            favouritesViewModel.favoriteQuotations.collect { quotations ->
                adapter.submitList(quotations)
            }
        }

        lifecycleScope.launchWhenStarted {
            favouritesViewModel.isDeleteAllMenuVisible.collect { isVisible ->
                requireActivity().invalidateMenu()
            }
        }

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_favourites, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteID -> {
                findNavController().navigate(R.id.action_favouritesFragment_to_deleteAllDialogFragment)
                true
            }
            else -> false
        }
    }

    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            favouritesViewModel.deleteQuotationAtPosition(position)
        }
    })

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        val isVisible = favouritesViewModel.isDeleteAllMenuVisible.value
        val deleteAllMenuItem = menu.findItem(R.id.deleteID)
        deleteAllMenuItem.isVisible = isVisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
