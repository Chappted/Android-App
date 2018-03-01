package de.ka.chappted.commons.views

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


class testerino(){

    val list = mutableListOf<Item>().apply {
        add(Item("hallo"))
        add(Item("dffedge"))
        add(Item("btrhr"))
        add(Item("nrfbed"))
        add(Item("gdfgd"))
    }



    val adapter = MyAdapter(list) {
        it.name
    }


}


class MyAdapter(val items: List<Item>, val listener: (Item) -> Unit) : RecyclerView.Adapter<Holder>(){







    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {


    }
}




class Holder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {


}

data class Item(val name: String)
