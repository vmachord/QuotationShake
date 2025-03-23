package dadm.vamactor.quotationshake.ui.newquotation

import retrofit2.HttpException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dadm.vamactor.quotationshake.R
import dadm.vamactor.quotationshake.data.newquotation.ConnectivityChecker
import dadm.vamactor.quotationshake.databinding.FragmentNewQuotationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


@AndroidEntryPoint
class NewQuotationFragment: Fragment(R.layout.fragment_new_quotation), MenuProvider {
    private val viewModel: NewQuotationViewModel by viewModels()
    @Inject
    lateinit var connectivityChecker: ConnectivityChecker
    private var _binding : FragmentNewQuotationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewQuotationBinding.bind(view);

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userName.collect { userName ->
                    binding.welcomeText.text = getString(
                        R.string.welcome_message,
                        userName.ifEmpty { getString(R.string.anonymous) }
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentQuotation.collect { quotation ->
                    if (quotation != null) {
                        binding.quoteText.text = quotation.text
                        binding.quoteAuthor.text = quotation.author.ifEmpty { getString(R.string.anonymous) }
                        binding.quoteGroup.isVisible = true
                        binding.welcomeText.isVisible = false
                    } else {
                        binding.quoteGroup.isVisible = false
                        binding.welcomeText.isVisible = true
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isAddToFavouritesVisible.collect { isVisible ->
                    binding.floatingActionButton.isVisible = isVisible
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    Log.d("SwipeRefresh", "Estado de carga: $isLoading")
                    binding.swipeRefreshLayout.isRefreshing = isLoading
                }
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getNewQuotation()
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.addToFavourites()
        }

       lifecycleScope.launch {
            viewModel.errorState.collect { error ->
                error?.let {
                    val errorMessage = getErrorMessage(this@NewQuotationFragment,error)
                    val isConnected = connectivityChecker.isConnectionAvailable()
                    if (!isConnected) {
                        Snackbar.make(requireView(), "No internet connection available", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
                    }
                    viewModel.resetError()
                }
            }
        }
    }

    fun getErrorMessage(fragment: Fragment, error: Throwable): String {
        val context = fragment.requireContext()

        return when (error) {
            is IOException -> context.getString(R.string.error_network)
            is TimeoutException -> context.getString(R.string.error_network)
            is HttpException -> context.getString(R.string.error_server)
            else -> context.getString(R.string.error_unexpected)
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_new_quotation, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.itemID -> {
                viewModel.getNewQuotation()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}