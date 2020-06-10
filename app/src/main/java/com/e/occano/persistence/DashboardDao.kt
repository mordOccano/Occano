package com.e.occano.persistence

import androidx.room.*
import com.e.occano.models.BlogPost
import com.e.occano.models.Cylinder
import com.e.occano.utils.Constants.Companion.PAGINATION_PAGE_SIZE

@Dao
interface DashboardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cylinder: Cylinder): Long

    @Delete
    suspend fun deleteCylinder(cylinder: Cylinder)

    @Query("""
        UPDATE gauge_for_calibration SET viewed_field = :viewed_field, viewed_value = :viewed_value, manual_corrected_value = :manual_corrected_value  
        WHERE id = :id
        """)
    suspend fun updateDashboard(id: Int, viewed_field: String, viewed_value: Float, manual_corrected_value: Float)

    @Query("SELECT * FROM cylinder")
    suspend fun getAllCylinders(): List<Cylinder>

//    @Query("""
//        SELECT * FROM cylinder
//        WHERE cylinder_num LIKE '%' || :query || '%'
////        ORDER BY date_updated DESC LIMIT (:page * :pageSize)
//        """)
//    suspend fun searchBlogPostsOrderByDateDESC(
//        query: String,
//        page: Int,
//        pageSize: Int = PAGINATION_PAGE_SIZE
//    ): List<Cylinder>

//    @Query("""
//        SELECT * FROM cylinder
//        WHERE title LIKE '%' || :query || '%'
//        OR body LIKE '%' || :query || '%'
//        OR username LIKE '%' || :query || '%'
//        ORDER BY date_updated  ASC LIMIT (:page * :pageSize)""")
//    suspend fun searchBlogPostsOrderByDateASC(
//        query: String,
//        page: Int,
//        pageSize: Int = PAGINATION_PAGE_SIZE
//    ): List<Cylinder>
//
//    @Query("""
//        SELECT * FROM cylinder
//        WHERE title LIKE '%' || :query || '%'
//        OR body LIKE '%' || :query || '%'
//        OR username LIKE '%' || :query || '%'
//        ORDER BY username DESC LIMIT (:page * :pageSize)""")
//    suspend fun searchBlogPostsOrderByAuthorDESC(
//        query: String,
//        page: Int,
//        pageSize: Int = PAGINATION_PAGE_SIZE
//    ): List<Cylinder>

//    @Query("""
//        SELECT * FROM cylinder
//        WHERE title LIKE '%' || :query || '%'
//        OR body LIKE '%' || :query || '%'
//        OR username LIKE '%' || :query || '%'
//        ORDER BY username  ASC LIMIT (:page * :pageSize)
//        """)
//    suspend fun searchBlogPostsOrderByAuthorASC(
//        query: String,
//        page: Int,
//        pageSize: Int = PAGINATION_PAGE_SIZE
//    ): List<Cylinder>


}

