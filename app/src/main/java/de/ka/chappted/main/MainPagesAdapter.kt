package de.ka.chappted.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import de.ka.chappted.R
import de.ka.chappted.main.screens.accepted.AcceptedFragment
import de.ka.chappted.main.screens.challenges.ChallengesFragment

class MainPagesAdapter(fragmentManager: androidx.fragment.app.FragmentManager, context: Context)
    : FragmentPagerAdapter(fragmentManager) {

    var items: MutableList<MainItem> = mutableListOf<MainItem>().apply {
        add(MainItem(ChallengesFragment.newInstance(), context.getString(R.string.challenges_title)))
        add(MainItem(AcceptedFragment.newInstance(), context.getString(R.string.accepted_title)))
    }

    override fun getItem(position: Int) = items[position].fragment

    override fun getCount() = items.size

    override fun getPageTitle(position: Int) = items[position].title
}


data class MainItem(val fragment: androidx.fragment.app.Fragment, val title: String)