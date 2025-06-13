package com.example.provamendes.data.adappter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.provamendes.R
import com.example.provamendes.data.model.Aluno

class ResultAdapter (
    private val context: Context,
    private val alunos: List<Aluno>
    ): BaseAdapter() {
    override fun getCount(): Int = alunos.size

    override fun getItem(position: Int): Aluno = alunos[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_result_adapter, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        }else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val aluno = getItem(position)
        holder.name.text = aluno.nome
        holder.data.text = aluno.data
        holder.result.text = aluno.result



        return view
    }


    private class ViewHolder(view: View) {
        val name: TextView = view.findViewById<TextView>(R.id.txt_nameResult)
        val data: TextView = view.findViewById<TextView>(R.id.txt_dataResult)
        val result: TextView = view.findViewById<TextView>(R.id.txt_result)
    }

}