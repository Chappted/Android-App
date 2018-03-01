package de.ka.chappted.main.screens.challenges

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.ka.chappted.BR
import de.ka.chappted.R

class ChallengesAdapter(
        val items: List<ChallengesItemViewModel>,
        val listener: (ChallengesItemViewModel) -> Unit)
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
            val item = items[it.adapterPosition]
            it.dataBinding.setVariable(BR.viewModel, item)
            it.dataBinding.root.setOnClickListener { listener(item) }
            it.dataBinding.executePendingBindings()
        }
    }
}

class ChallengeViewHolder(val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root)