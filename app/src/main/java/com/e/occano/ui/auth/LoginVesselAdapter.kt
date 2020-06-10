package com.e.occano.ui.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.occano.R
import com.e.occano.models.Ship
import kotlinx.android.synthetic.main.login_rv_item_not_selected.view.*

class LoginVesselAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ship>() {

        override fun areItemsTheSame(oldItem: Ship, newItem: Ship): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ship, newItem: Ship): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList.get(position).isSelected) {
            true -> 1
            false -> 0
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            0 -> {
                return LoginShupViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.login_rv_item_not_selected,
                        parent,
                        false
                    ),
                    interaction
                )
            }

            1 -> {
                return LoginShupViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.login_rv_item_selected,
                        parent,
                        false
                    ),
                    interaction
                )
            }

            else -> {
                return LoginShupViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.login_rv_item_not_selected,
                        parent,
                        false
                    ),
                    interaction
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoginShupViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Ship>) {
        differ.submitList(list)
    }

    class LoginShupViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Ship) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.tv_rv_login_shipname.text = item.vessel
            itemView.tv_rv_login_shipimo.text = item.IMO
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Ship)
    }
}
