package com.e.occano.di.main

import com.e.occano.api.main.ApiMainService
import com.e.occano.persistence.AccountPropertiesDao
import com.e.occano.persistence.AppDatabase
import com.e.occano.persistence.BlogPostDao
import com.e.occano.repository.main.AccountRepository
import com.e.occano.repository.main.AccountRepositoryImpl
import com.e.occano.repository.main.BlogRepositoryImpl
import com.e.occano.repository.main.CreateBlogRepositoryImpl
import com.e.occano.session.SessionManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit

@FlowPreview
@Module
object MainModule {

    @JvmStatic
    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): ApiMainService {
        return retrofitBuilder
            .build()
            .create(ApiMainService::class.java)
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideAccountRepository(
        apiMainService: ApiMainService,
        accountPropertiesDao: AccountPropertiesDao,
        sessionManager: SessionManager
    ): AccountRepositoryImpl {
        return AccountRepositoryImpl(
            apiMainService,
            accountPropertiesDao,
            sessionManager
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideBlogPostDao(db: AppDatabase): BlogPostDao {
        return db.getBlogPostDao()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideBlogRepository(
        apiMainService: ApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): BlogRepositoryImpl {
        return BlogRepositoryImpl(
            apiMainService,
            blogPostDao,
            sessionManager
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideCreateBlogRepository(
        apiMainService: ApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): CreateBlogRepositoryImpl {
        return CreateBlogRepositoryImpl(
            apiMainService, blogPostDao,
            sessionManager
        )
    }
}



