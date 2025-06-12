package com.example.provamendes.domain

class ListAlunos{
    private val alunos: MutableList<AlunosResponse> = mutableListOf()

    fun adicionarAlunos(aluno: AlunosResponse) {
        alunos.add(aluno)
    }
    fun listAlunos(): List<AlunosResponse> {
        return alunos
    }
}

