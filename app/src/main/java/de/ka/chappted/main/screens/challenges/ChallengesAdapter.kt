package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.ka.chappted.BR
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.api.model.Type

/**
 * Adapter class for displaying challenges.
 */
class ChallengesAdapter(
        private val fragment: Fragment,
        private val items: MutableList<Challenge> = mutableListOf(),
        val challengesListListener: ChallengeListListener)
    : RecyclerView.Adapter<ChallengeViewHolder>() {

    private val loadingChallenge = Challenge(Type.LOADING)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChallengeViewHolder {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent?.context),
                Type.values()[viewType].layoutResId,
                parent,
                false)

        return ChallengeViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = items[position].type.ordinal

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ChallengeViewHolder?, position: Int) {

        val adapterPosition = holder?.adapterPosition ?: return

        if (getItemViewType(adapterPosition) == Type.DEFAULT.ordinal) {

            holder.let {

                val viewModel = ViewModelProviders.of(fragment).get(
                        it.adapterPosition.toString(),
                        ChallengesItemViewModel::class.java)

                val item = items[it.adapterPosition]
                viewModel.challenge = item

                it.dataBinding.setVariable(BR.viewModel, viewModel)
                it.dataBinding.root.setOnClickListener {
                    viewModel.play()
                    challengesListListener.onChallengeClicked(item)
                }
                it.dataBinding.setLifecycleOwner(fragment)
                it.dataBinding.executePendingBindings()
            }
        } else if (getItemViewType(adapterPosition) == Type.NO_CONNECTION.ordinal) {

            holder.let {

                val viewModel = ViewModelProviders.of(fragment).get(
                        NoConnectionItemViewModel::class.java)

                viewModel.listener = challengesListListener

                it.dataBinding.setVariable(BR.viewModel, viewModel)
                it.dataBinding.executePendingBindings()
            }
        }
    }

    /**
     * Adds the given items to the list of displayed items.
     */
    fun addAll(list: List<Challenge>) {

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
    fun onChallengeClicked(challenge: Challenge)

    /**
     * Called on a retry click.
     */
    fun onRetryClicked()

}

