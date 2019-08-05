package br.com.usemobile.baseactivity.kotlin.core.platform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/** Created by Gabriel
 * on 05/08/2019
 * at 10:28
 * at Usemobile
 **/

abstract class BaseRVAdapter(
    var recyclerList: ArrayList<Any>,
    val context: Context,
    val activity: BaseActivity
) : RecyclerView.Adapter<BaseRVAdapter.BaseViewHolder>() {

    var query: String? = null

    var selected: Boolean = true
    var filterText: Boolean = false

//    abstract fun layoutId(): Int

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(layoutId(), parent, false)
//        return BaseViewHolder(view)
//    }

    fun addAll(list: ArrayList<Any>, query: String?) {
        if (!query.isNullOrBlank()) {
            this.query = query
            filterText = true
        } else {
            this.query = null
            filterText = false
        }
        recyclerList = arrayListOf<Any>()
        recyclerList = list
        notifyDataSetChanged()
    }

    fun appendAll(list: ArrayList<*>) {
        val startindex = recyclerList.size
        recyclerList.addAll(startindex, list)
        notifyItemRangeInserted(startindex, list.size)
    }

    fun appendAllQueried(list: ArrayList<Any>) {
        val startindex = recyclerList.size
        recyclerList.addAll(startindex, list)
        notifyItemRangeInserted(startindex, list.size)
    }


    fun getItem(position: Int): Any? {
        return recyclerList[position]
    }

    override fun getItemCount(): Int {

        return recyclerList.size

    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var view: View = itemView
        open fun bindView() {
            with(view) {
                setIsRecyclable(false)

            }
        }

    }

}