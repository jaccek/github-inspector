package com.github.jaccek.githubinspector.viper.userslist

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.jaccek.githubinspector.R
import com.github.jaccek.githubinspector.databinding.ActivityUsersListBinding
import com.github.jaccek.githubinspector.rdp.model.User
import com.github.jaccek.githubinspector.viper.userslist.view.UsersListAdapter
import com.github.jaccek.githubinspector.viper.userslist.view.model.UsersListViewModel
import com.mateuszkoslacz.moviper.base.view.activity.autoinject.passive.databinding.ViperDataBindingPassiveActivity
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

internal class UsersListActivity :
        ViperDataBindingPassiveActivity<UsersListContract.View, ActivityUsersListBinding>(),
        UsersListContract.View {

    override val itemSelections: Subject<User> = PublishSubject.create()

    override val endOfListScrolls: Subject<User> = PublishSubject.create()

    private val usersAdapter = UsersListAdapter()
    private lateinit var errorSnackbar: Snackbar
    private lateinit var loadingSnackbar: Snackbar

    override fun createPresenter(): ViperPresenter<UsersListContract.View> =
            UsersListPresenter()

    override fun getLayoutId(): Int =
            R.layout.activity_users_list

    override fun injectViews() {
        super.injectViews()

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mBinding.viewModel = UsersListViewModel()

        mBinding.usersListRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = linearLayoutManager
        }

        mBinding.usersListRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleElement = linearLayoutManager.findLastVisibleItemPosition()
                if (lastVisibleElement + 10 >= usersAdapter.itemCount) {
                    endOfListScrolls.onNext(usersAdapter.getLastItem())
                }
            }
        })

        errorSnackbar = Snackbar.make(mBinding.containerView, "error", Snackbar.LENGTH_INDEFINITE)
        loadingSnackbar = Snackbar.make(mBinding.containerView, "loading", Snackbar.LENGTH_INDEFINITE)
    }

    override fun showLoader() {
        loadingSnackbar.show()
    }

    override fun hideLoader() {
        loadingSnackbar.dismiss()
    }

    override fun showUsers(users: List<User>) {
        usersAdapter.addUsers(users)
    }

    override fun showError() {
        errorSnackbar.show()
    }
}
