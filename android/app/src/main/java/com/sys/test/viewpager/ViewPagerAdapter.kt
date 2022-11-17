package com.sys.test.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sys.test.R

class ViewPagerAdapter(adList: ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = adList
    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView_ad = itemView.findViewById<ImageView>(R.id.advertise_img)

        fun onBind(res: Int) {
            imageView_ad.setImageResource(res)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :PagerViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.advertise, parent, false)
        return PagerViewHolder(view)
    }
    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val viewholder = holder
        viewholder.onBind(item[position%item.size])
    }


}