package de.ka.chappted.main

import android.app.Application
import android.support.design.widget.FloatingActionButton
import de.ka.chappted.App
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseViewModel

/**
 * A view model for showing and manipulating the main content.
 *
 * Created by Thomas Hofmann on 01.12.17.
 */
class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    fun onSearchFabClicked() {

    }

    fun onNewChallengeFabClicked() {

    }

    fun onShowMapFabClicked() {

    }

    fun getSmallFabSize() = FloatingActionButton.SIZE_MINI

    fun getBigFabSize() = FloatingActionButton.SIZE_NORMAL

    fun getSearchTooltip() = getApplication<App>().getString(R.string.challenge_fab_search)

    fun getNewChallengeTooltip() = getApplication<App>().getString(R.string.challenge_fab_newchallenge)

    fun getShowMapTooltip() = getApplication<App>().getString(R.string.challenge_fab_showmap)

    fun getSearchIcon() = getApplication<App>().applicationContext.getDrawable(R.drawable.ic_search)

    fun getNewChallengeIcon() = getApplication<App>().applicationContext.getDrawable(R.drawable.ic_plus)

    fun getMapIcon() = getApplication<App>().applicationContext.getDrawable(R.drawable.ic_map)

}