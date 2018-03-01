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
import de.ka.chappted.api.model.Challenge

/**
 * Adapter class for displaying challenges.
 */
class ChallengesAdapter(
        val fragment: Fragment,
        val items: MutableList<Challenge> = mutableListOf(),
        val listener: (Challenge, ChallengesItemViewModel) -> Unit)
    : RecyclerView.Adapter<ChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChallengeViewHolder {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent?.context),
                R.layout.layout_item_challenge,
                parent,
                false)

        return ChallengeViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ChallengeViewHolder?, position: Int) {

        holder?.let {

            val viewModel = ViewModelProviders.of(fragment).get(
                    it.adapterPosition.toString(),
                    ChallengesItemViewModel::class.java)

            val item = items[it.adapterPosition]
            viewModel.challenge = item

            it.dataBinding.setVariable(BR.viewModel, viewModel)
            it.dataBinding.root.setOnClickListener { listener(item, viewModel) }
            it.dataBinding.setLifecycleOwner(fragment)
            it.dataBinding.executePendingBindings()
        }
    }

    /**
     * Adds the given items to the list of displayed items.
     */
    fun addAll(list: List<Challenge>) {
        items.addAll(list)
        notifyDataSetChanged()
    }
}

class ChallengeViewHolder(val dataBinding: ViewDataBinding)
    : RecyclerView.ViewHolder(dataBinding.root)