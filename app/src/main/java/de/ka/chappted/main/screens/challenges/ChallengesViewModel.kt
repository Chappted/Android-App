package de.ka.chappted.main.screens.challenges

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import de.ka.chappted.App
import de.ka.chappted.R
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.commons.arch.base.BaseViewModel
import de.ka.chappted.commons.views.OffsetItemDecoration
import de.ka.chappted.main.screens.challenges.items.ChallengeContentItem
import de.ka.chappted.main.screens.challenges.items.ChallengeHeaderItem
import de.ka.chappted.main.screens.challenges.items.ChallengeItem
import de.ka.chappted.main.screens.challenges.items.ChallengeNoConnectionItem

/**
 * A view model for showing the challenges content.
 *
 * Created by Thomas Hofmann on 01.03.18.
 */
class ChallengesViewModel(application: Application) : BaseViewModel(application) {

    var challengesAdapter = MutableLiveData<ChallengesAdapter>()

    /**
     * Initializes the adapter.
     */
    fun initAdapter(adapter: ChallengesAdapter) {

        if (challengesAdapter.value == null) {
            challengesAdapter.value = adapter // note that we don't postValue(), as this is instant

            loadChallenges()
        }
    }

    /**
     * Retrieves a item decoration.
     */
    fun getItemDecoration() = OffsetItemDecoration(topOffset = getApplication<App>()
            .resources.getDimension(R.dimen.offset_top_challenges_list_item).toInt())

    /**
     * Loads challenges and immediately displays them.
     */
    fun loadChallenges() {

        challengesAdapter.value?.showLoading()

        val resources = getApplication<App>().resources

        val list = mutableListOf<ChallengeItem>()
                .apply {
                    add(ChallengeHeaderItem(
                            R.layout.layout_item_challenge_header,
                            resources.getString(R.string.challenge_recommended),
                            R.drawable.ic_recommended))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Jamit Labs Season 1",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_fifa),
                                    description = "You need to join the challenge to see the description.",
                                    isProtected = true)))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Friday Tournament",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_table_tennis),
                                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ac massa vestibulum, vestibulum nunc in …")))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Jamit Labs Open Challenge",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_mario_kart),
                                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ac massa vestibulum, vestibulum nunc in …",
                                    isProtected = true)))
                    add(ChallengeHeaderItem(
                            R.layout.layout_item_challenge_header,
                            resources.getString(R.string.challenge_nearby),
                            R.drawable.ic_nearby))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Jamit Labs Season 1",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_fifa),
                                    description = "You need to join the challenge to see the description.",
                                    isProtected = true)))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Friday Tournament",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_table_tennis),
                                    description = "You need to join the challenge to see the description.",
                                    isProtected = true)))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Jamit Labs Open Challenge",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_mario_kart),
                                    description = "You need to join the challenge to see the description.",
                                    isProtected = true)))
                    add(ChallengeHeaderItem(
                            R.layout.layout_item_challenge_header,
                            resources.getString(R.string.challenge_latest),
                            R.drawable.ic_latest))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Jamit Labs Season 1",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_fifa),
                                    description = "You need to join the challenge to see the description.",
                                    isProtected = true)))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Friday Tournament",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_table_tennis),
                                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ac massa vestibulum, vestibulum nunc in …")))
                    add(ChallengeContentItem(
                            R.layout.layout_item_challenge,
                            Challenge(title = "Jamit Labs Season 1",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_fifa),
                                    description = "You need to join the challenge to see the description.",
                                    isProtected = true)))
                    add(ChallengeNoConnectionItem(R.layout.layout_item_no_connection))
                }


        Handler().postDelayed({
            challengesAdapter.value?.hideLoading()?.addAll(list)
        }, 4_000)

    }

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

    /**
     * Retrieves a layout manager.
     */
    fun getChallengesLayoutManager() = LinearLayoutManager(getApplication())
}
