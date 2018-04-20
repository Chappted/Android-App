package de.ka.chappted.main.screens.accepted

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import de.ka.chappted.App
import de.ka.chappted.R
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.commons.arch.base.BaseViewModel
import de.ka.chappted.commons.views.OffsetItemDecoration
import de.ka.chappted.main.screens.accepted.items.*
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * Created by th on 20.12.17.
 */
class AcceptedViewModel(application: Application) : BaseViewModel(application) {

    var needsLogin: Boolean = false

    val progressVisibility = MutableLiveData<Int>()

    init {
        progressVisibility.postValue(View.INVISIBLE)
    }

    var acceptedAdapter = MutableLiveData<AcceptedAdapter>()

    /**
     * Initializes the adapter.
     */
    fun initAdapter(adapter: AcceptedAdapter) {

        if (acceptedAdapter.value == null) {
            acceptedAdapter.value = adapter // note that we don't postValue(), as this is instant

            loadChallenges()
        }
    }

    /**
     * Retrieves a item decoration.
     */
    fun getItemDecoration() = OffsetItemDecoration(topOffset = getApplication<App>()
            .resources.getDimension(R.dimen.offset_top_challenges_list_item).toInt())

    /**
     * Retrieves a layout manager.
     */
    fun getChallengesLayoutManager() = LinearLayoutManager(getApplication())

    fun loadChallenges() {

        acceptedAdapter.value?.showLoading()

        val resources = getApplication<App>().resources

        val list = mutableListOf<AcceptedItem>()
                .apply {
                    add(AcceptedHeaderItem(
                            R.layout.layout_item_accepted_header,
                            resources.getString(R.string.accepted_ongoing),
                            R.drawable.ic_ongoing))
                    add(AcceptedContentItem(
                            R.layout.layout_item_accepted,
                            Challenge(title = "Jamit Labs Season 2",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_fifa),
                                    userRank = "5th (72 points)",
                                    challengeLeader = "Maximilian Mustermann",
                                    challengeEndDate = "in 15 days (Mar, 22nd)",
                                    isProtected = true)))
                    add(AcceptedHeaderItem(
                            R.layout.layout_item_accepted_header,
                            resources.getString(R.string.accepted_completed),
                            R.drawable.ic_completed))
                    add(AcceptedContentItem(
                            R.layout.layout_item_accepted,
                            Challenge(title = "Karlsruhe Open Challenge",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_mario_kart),
                                    userRank = "2nd (12 points)",
                                    challengeLeader = "Larissa Huang",
                                    challengeEndDate = "Never",
                                    isProtected = false)))
                    add(AcceptedContentItem(
                            R.layout.layout_item_accepted,
                            Challenge(title = "Jamit Labs Season 1",
                                    category = getApplication<App>().resources.getString(R.string.challenge_category_table_tennis),
                                    userRank = "3rd (106 points)",
                                    challengeLeader = "Max Mobil",
                                    challengeEndDate = "2 months ago (Dec, 28th)",
                                    isProtected = true)))
                    add(AcceptedNoConnectionItem(R.layout.layout_item_accepted_no_connection))
                    add(AcceptedFooterItem(R.layout.layout_item_accepted_footer))
                }

        Handler().postDelayed({
            acceptedAdapter.value?.hideLoading()?.addAll(list)
        }, 4_000)
    }

    fun onSubmit() {
        repository.getUser(
                object : retrofit2.Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Timber.e("YAY!")

                        needsLogin = response.code() == 401

                        if (response.code() == 200) {
                            // getApplication<Application>().startActivity(Intent(getApplication(), TesterActivity::class.java))
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Timber.e(t, "YAY!")
                    }
                })

        Handler().postDelayed({ progressVisibility.postValue(View.INVISIBLE) }, 10000)
        progressVisibility.postValue(View.VISIBLE)
    }

    override fun onLoggedIn() {
        if (needsLogin) {
            onSubmit()
        }
    }

}