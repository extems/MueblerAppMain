package com.example.mueblerapp20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mueblerapp20.R
import com.example.mueblerapp20.classes.Mueble

class MuebleListAdapter (private var muebleList : MutableList<Mueble>,  var context : Context ,val onItemClick : (Int) -> Unit) : RecyclerView.Adapter<MuebleListAdapter.MuebleHolder>() {

    companion object {

        private val TAG = "MuebleListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuebleHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_mueble,parent,false)
        return (MuebleHolder(
            view
        ))
    }

    override fun getItemCount(): Int {

        return muebleList.size
    }

    fun setData(newData: ArrayList<Mueble>) {
        this.muebleList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MuebleHolder, position: Int) {
        holder.setName(muebleList[position].nombre)
        holder.setPrice(muebleList[position].precio)
        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }

        Glide
            .with(context)
            .load(muebleList[position].urlImagen)

            .centerInside()
            .into(holder.getImageView());

    }

    class MuebleHolder (v: View) : RecyclerView.ViewHolder(v){

        private var view : View

        init {
            this.view = v
        }

        fun setName(name : String) {
            val txt : TextView = view.findViewById(R.id.muebleTitle)
            txt.text = name
        }

        fun setPrice(price : Double) {
            val txt : TextView = view.findViewById(R.id.priceTitle)
            txt.text = "$" + price.toString()
        }

        fun getCardLayout ():CardView{
            return view.findViewById(R.id.btn_card_view)
        }

        fun getImageView () : ImageView {
            return view.findViewById(R.id.urlPhotoFavoritos)
        }

    }

}