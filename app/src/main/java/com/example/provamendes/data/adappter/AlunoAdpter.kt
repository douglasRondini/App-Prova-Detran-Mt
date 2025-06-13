package com.example.provamendes.data.adappter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.provamendes.R
import com.example.provamendes.data.model.Aluno

class AlunoAdapter(
    private val context: Context,
    private val alunos: List<Aluno>,
    private val onResultUpdate: () -> Unit,
    private val showOnlyPending: Boolean = false
) : BaseAdapter() {

    private val filteredList: List<Aluno>
        get() = if (showOnlyPending) {
            alunos.filter { it.result.isEmpty() }
        } else {
            alunos
        }

    override fun getCount(): Int = filteredList.size
    override fun getItem(position: Int): Aluno = filteredList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_adapter, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val aluno = getItem(position)
        holder.nome.text = aluno.nome
        holder.data.text = aluno.data
        holder.btnAprovado.isEnabled = aluno.result != "Aprovado"
        holder.btnReprovado.isEnabled = aluno.result != "Reprovado"

        holder.btnAprovado.setOnClickListener {
            aluno.result = "Aprovado"
            notifyDataSetChanged()
            onResultUpdate()
        }

        holder.btnReprovado.setOnClickListener {
            aluno.result = "Reprovado"
            notifyDataSetChanged()
            onResultUpdate()
        }

        return view
    }

    private class ViewHolder(view: View) {
        val nome: TextView = view.findViewById(R.id.txt_name)
        val data: TextView = view.findViewById(R.id.txt_data)
        val btnAprovado: AppCompatButton = view.findViewById(R.id.btn_aprovado)
        val btnReprovado: AppCompatButton = view.findViewById(R.id.btn_reprovado)
    }
}
