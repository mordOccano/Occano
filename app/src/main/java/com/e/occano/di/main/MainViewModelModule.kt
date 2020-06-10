package com.e.occano.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.occano.di.main.keys.MainViewModelKey
import com.e.occano.ui.main.alerts.AccountViewModel
import com.e.occano.ui.main.dashboard.blogviewmodel.BlogViewModel
import com.e.occano.ui.main.graphs.CreateBlogViewModel
import com.e.occano.viewmodels.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MainViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accoutViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(CreateBlogViewModel::class)
    abstract fun bindCreateBlogViewModel(createBlogViewModel: CreateBlogViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @MainViewModelKey(AccountViewModel::class)
//    abstract fun bindAccountViewModel(accoutViewModel: AccountViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @MainViewModelKey(BlogViewModel::class)
//    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @MainViewModelKey(CreateBlogViewModel::class)
//    abstract fun bindCreateBlogViewModel(createBlogViewModel: CreateBlogViewModel): ViewModel
}


