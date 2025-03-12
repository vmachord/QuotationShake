package dadm.vamactor.quotationshake.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dadm.vamactor.quotationshake.databinding.QuotationItemBinding
import domain.model.Quotation

class QuotationListAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<Quotation, QuotationListAdapter.ViewHolder>(QuotationDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuotationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: QuotationItemBinding,
                     private val onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(binding.quoteAuthor.text.toString())
            }
        }
        fun bind(quotation: Quotation) {
            binding.quoteText.text = quotation.text
            binding.quoteAuthor.text = quotation.author.ifEmpty { "Anonymous" }
        }
    }

    object QuotationDiff : DiffUtil.ItemCallback<Quotation>() {
        override fun areItemsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quotation, newItem: Quotation): Boolean {
            return oldItem == newItem
        }
    }


}

