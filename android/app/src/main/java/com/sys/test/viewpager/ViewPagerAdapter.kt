package com.sys.test.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sys.test.R

/*뷰페이저 어뎁터 구현
  광고 리스너를 만들지 않으므로 광고에 tag 붙이지 않음
 */
class ViewPagerAdapter(adList: ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = adList
    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewAd = itemView.findViewById<ImageView>(R.id.advertise_img)
        fun onBind(res: Int) {
            //이미지뷰 리소스와 tag 변경
            imageViewAd.setImageResource(res)
            
            //이미지뷰에 tag 달기(이미지 구분)
//            when(res){
//                R.drawable.ad1->{
//                    imageViewAd.tag = "볼거리"
//                }
//                R.drawable.ad2->{
//                    imageViewAd.tag = "놀멍"
//                }
//                R.drawable.ad3->{
//                    imageViewAd.tag = "먹거리"
//                }
//                R.drawable.ad4->{
//                    imageViewAd.tag = "쉴멍"
//                }
//            }
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