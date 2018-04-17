package de.ka.chappted.main.screens.accepted

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.ka.chappted.BR
import de.ka.chappted.R
import de.ka.chappted.main.screens.accepted.items.*

/**
 * Adapter class for displaying challenges.
 */
class AcceptedAdapter(
        private val fragment: Fragment,
        private val items: MutableList<AcceptedItem> = mutableListOf(),
        val acceptedListListener: AcceptedListListener)
    : RecyclerView.Adapter<AcceptedViewHolder>() {

    private val loadingAccepted = AcceptedLoadingItem(R.layout.layout_item_loading)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                viewType, // this is a neat trick ;)
                parent,
                false)

        return AcceptedViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = items[position].layoutResId


    override fun onBindViewHolder(holder: AcceptedViewHolder, position: Int) {

        val adapterPosition = holder.adapterPosition

        val item = items[adapterPosition]

        when (item) {
            is AcceptedHeaderItem -> holder.let {

                val viewModel = ViewModelProviders.of(fragment).get(
                        "chall" + adapterPosition.toString(),
                        AcceptedHeaderItemViewModel::class.java)

                viewModel.setup(item, acceptedListListener)

                bind(it.dataBinding, viewModel)
            }
            is AcceptedContentItem -> holder.let {

                val viewModel = ViewModelProviders.of(fragment).get(
                        "chall" + adapterPosition.toString(),
                        AcceptedContentItemViewModel::class.java)

                viewModel.setup(item, acceptedListListener)

                bind(it.dataBinding, viewModel)
            }
            is AcceptedNoConnectionItem -> holder.let {
                val viewModel = ViewModelProviders.of(fragment).get(
                        "chall",
                        AcceptedNoConnectionItemViewModel::class.java)

                viewModel.setup(acceptedListListener)

                bind(it.dataBinding, viewModel)
            }
        }
    }

    override fun getItemCount() = items.size

    /**
     * Binds the view model to the view data binding.
     */
    private fun bind(dataBinding: ViewDataBinding, viewModel: Any) {
        dataBinding.setVariable(BR.viewModel, viewModel)
        dataBinding.setLifecycleOwner(fragment)
        dataBinding.executePendingBindings()
    }

    /**
     * Adds the given items to the list of displayed items.
     */
    fun addAll(list: List<AcceptedItem>) {

        var was = items.size - 1

        if (was < 0) {
            was = 0
        }

        items.addAll(list)

        notifyItemRangeInserted(was, list.size)
    }

    /**
     * Shows a loading indicator.
     */
    fun showLoading(): AcceptedAdapter {
        if (items.contains(loadingAccepted)) return this

        items.add(loadingAccepted)
        notifyItemInserted(itemCount)

        return this
    }

    /**
     * Hides a loading indicator.
     */
    fun hideLoading(): AcceptedAdapter {
        if (!items.contains(loadingAccepted)) return this

        items.remove(loadingAccepted)
        notifyItemRemoved(itemCount)

        return this
    }

}

/**
 * A view holder for a challenge item.
 */
class AcceptedViewHolder(val dataBinding: ViewDataBinding)
    : RecyclerView.ViewHolder(dataBinding.root)


/**
 * Listens for challenge list events.
 */
interface AcceptedListListener {

    /**
     * Called on a challenge click.
     * @param challenge the clicked challenge
     */
    fun onChallengeClicked(acceptedContent: AcceptedContentItem)

    /**
     * Called on a retry click.
     */
    fun onRetryClicked()
}

