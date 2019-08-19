package com.lambdaschool.sprint2_challenge

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.animatedlayoutdemocode.R
import com.lambdaschool.animatedlayoutdemocode.model.ShoppingItem
import kotlinx.android.synthetic.main.shopping_item_layout.view.*

import java.util.ArrayList

class ItemListAdapter(private val dataList: List<ShoppingItem>) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return dataList.size
    }

    private var context: Context? = null
    private var lastPosition:Int = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.card_view as CardView
        val image = view.image as ImageView
        val name = view.name_text as TextView
    }





    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        context = viewGroup.context
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.shopping_item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val data = dataList[i]
        val re = Regex("[^A-Za-z_ ]")
        val formattedName = re.replace(data.name, "").replace("_", " ")
        //val formattedName = data.name.replace("_", " ").replace("[^A-Za-z ]", "").replace("2", "")
        //I believed the intention was to strip the numerals from the item names and allow differentiation based on the image.

        viewHolder.name.text = formattedName
        viewHolder.image.setImageDrawable(context?.getDrawable(data.drawableId))
        viewHolder.card.tag = data.selected

        if (data.selected) {
            viewHolder.card.setCardBackgroundColor(ContextCompat.getColor(viewHolder.card.context, R.color.colorAccent))
        } else {
            viewHolder.card.setCardBackgroundColor(
                ContextCompat.getColor(
                    viewHolder.card.context,
                    R.color.cardview_light_background
                )
            )
        }

        viewHolder.card.setOnClickListener { view ->
            if (view.tag as Boolean) {
                view.tag = false
                data.selected = false
                viewHolder.card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        viewHolder.card.context,
                        R.color.cardview_light_background
                    )
                )
            } else {
                view.tag = true
                data.selected = true
                viewHolder.card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        viewHolder.card.context,
                        R.color.colorAccent
                    )
                )
            }
        }
        fun setEnterAnimation (viewToAnimate: View, position: Int){
            if(position>lastPosition){
                val animation: Animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.abc_grow_fade_in_from_bottom)
                viewToAnimate.startAnimation(animation)
                lastPosition=position
            }
        }
        setEnterAnimation(viewHolder.card,i)
    }
}
