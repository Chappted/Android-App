package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.ka.chappted.BR
import de.ka.chappted.R
import de.ka.chappted.main.screens.challenges.items.*

/**
 * Adapter class for displaying challenges.
 */
class ChallengesAdapter(
        private val fragment: Fragment,
        private val items: MutableList<ChallengeItem> = mutableListOf(),
        val challengesListListener: ChallengeListListener)
    : RecyclerView.Adapter<ChallengeViewHolder>() {

    private val loadingChallenge = ChallengeLoadingItem(R.layout.layout_item_loading)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                viewType, // this is a neat trick ;)
                parent,
                false)

        return ChallengeViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = items[position].layoutResId


    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {

        val adapterPosition = holder.adapterPosition

        val item = items[adapterPosition]

        when (item) {
            is ChallengeHeaderItem -> holder.let {

                val viewModel = ViewModelProviders.of(fragment).get(
                        "chall" + adapterPosition.toString(),
                        HeaderItemViewModel::class.java)

                viewModel.setup(item, challengesListListener)

                bind(it.dataBinding, viewModel)
            }
            is ChallengeContentItem -> holder.let {

                val viewModel = ViewModelProviders.of(fragment).get(
                        "chall" + adapterPosition.toString(),
                        ContentItemViewModel::class.java)

                viewModel.setup(item, challengesListListener)

                bind(it.dataBinding, viewModel)
            }
            is ChallengeNoConnectionItem -> holder.let {
                val viewModel = ViewModelProviders.of(fragment).get(
                        "chall",
                        NoConnectionItemViewModel::class.java)

                viewModel.setup(challengesListListener)

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
    fun addAll(list: List<ChallengeItem>) {

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
    fun showLoading(): ChallengesAdapter {
        if (items.contains(loadingChallenge)) return this

        items.add(loadingChallenge)
        notifyItemInserted(itemCount)

        return this
    }

    /**
     * Hides a loading indicator.
     */
    fun hideLoading(): ChallengesAdapter {
        if (!items.contains(loadingChallenge)) return this

        items.remove(loadingChallenge)
        notifyItemRemoved(itemCount)

        return this
    }

}

/**
 * A view holder for a challenge item.
 */
class ChallengeViewHolder(val dataBinding: ViewDataBinding)
    : RecyclerView.ViewHolder(dataBinding.root)


/**
 * Listens for challenge list events.
 */
interface ChallengeListListener {

    /**
     * Called on a challenge click.
     * @param challenge the clicked challenge
     */
    fun onChallengeClicked(challengeContent: ChallengeContentItem)

    /**
     * Called on a retry click.
     */
    fun onRetryClicked()

    /**
     * Called when clicked on more.
     * @param category the category clicked
     */
    fun onMoreClicked(category: String?)

}

