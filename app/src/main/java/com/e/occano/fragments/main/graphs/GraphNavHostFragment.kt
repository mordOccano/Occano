package com.e.occano.fragments.main.graphs

import android.content.Context
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.e.occano.ui.main.MainActivity

class GraphNavHostFragment : NavHostFragment(){

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory =
            (activity as MainActivity).graphFragmentFactory
        super.onAttach(context)
    }

    companion object{

        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): GraphNavHostFragment {
            var bundle: Bundle? = null
            if(graphId != 0){
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result =
                GraphNavHostFragment()
            if(bundle != null){
                result.arguments = bundle
            }
            return result
        }
    }
}