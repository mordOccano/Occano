package com.e.occano.ui.main.alerts.status

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occano.R
import com.e.occano.models.Status
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_details
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_subtitle
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_timestamp
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_title
import kotlinx.android.synthetic.main.status_rv_item_yellow.view.*
import java.util.ArrayList

class NewStatusAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val llist: MutableList<Status> =
        ArrayList()

    override fun getItemViewType(position: Int): Int {
        return differ.currentList.get(position).statusKindOfDanger
    }


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Status>() {

        override fun areItemsTheSame(oldItem: Status, newItem: Status): Boolean {
            return oldItem.statusId == newItem.statusId
        }

        override fun areContentsTheSame(oldItem: Status, newItem: Status): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            0 -> {
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_blue,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            1->{
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_yellow,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            2 ->{
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_red,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            else -> {
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item,
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
            is NewStatusViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Status>) {
        for (i in list.indices) {
            if (list[i].kindOfAcknowledge == 0) {
                llist.add(list[i])
            }
        }
        differ.submitList(llist)
    }

    class NewStatusViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Status) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }


            itemView.tv_item_title.text = item.statusMainTitle
            itemView.tv_item_timestamp.text = item.timeStampOfstatus
            itemView.tv_item_subtitle.text = item.statusSubTitle
            itemView.btn_item_rv_status_more.setOnClickListener {
                tv_item_subtitle.visibility = View.GONE
                tv_item_details.visibility = View.VISIBLE
                tv_item_details.text = item.statusMoreContent
                btn_item_rv_status_more.visibility = View.GONE
                btn_item_rv_status_less.visibility = View.VISIBLE
            }

            itemView.btn_item_rv_status_less.setOnClickListener {
                tv_item_details.visibility = View.GONE
                btn_item_rv_status_less.visibility = View.GONE
                tv_item_subtitle.visibility = View.VISIBLE
                btn_item_rv_status_more.visibility = View.VISIBLE
                itemView.btn_item_rv_status_swipe.visibility = View.VISIBLE
            }
            itemView.btn_item_rv_status_swipe.setOnClickListener{
                interaction?.onItemSelected(adapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Status)
    }
}
