package com.example.provamendes.presenter.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.provamendes.R
import com.example.provamendes.data.adappter.AlunoAdapter
import com.example.provamendes.data.adappter.ResultAdapter
import com.example.provamendes.data.model.Aluno
import com.example.provamendes.databinding.FragmentResultBinding
import com.example.provamendes.presenter.viewmodel.SharedViewModel
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        setupList()
        setupGenerateReportButton()
    }

    private fun setupList() {
        viewModel.itemList.observe(viewLifecycleOwner) {listaAlunos ->
            val aprovados = listaAlunos.filter { it.result == "Aprovado" }
            val reprovados = listaAlunos.filter { it.result == "Reprovado" }

            val adapterAprovados = ResultAdapter(requireContext(),aprovados)
            binding.listVAprovados.adapter = adapterAprovados

            val adapterReprovados = ResultAdapter(requireContext(), reprovados)
            binding.listVReprovados.adapter = adapterReprovados
        }
    }

    private fun setupGenerateReportButton() {
        binding.btnGenerateReport.setOnClickListener {
            generatePDFReport()
        }
    }

    private fun generatePDFReport() {
        try {
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val fileName = "Relatorio_Resultados_${currentDate.replace("/", "_")}.pdf"
            val file = File(requireContext().getExternalFilesDir(null), fileName)

            PdfWriter(file).use { writer ->
                val pdfDoc = PdfDocument(writer)
                Document(pdfDoc).use { document ->
                    // Add title
                    val title = Paragraph("Relatório de Resultados das Provas")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(20f)
                    document.add(title)

                    // Add date
                    val date = Paragraph("Data: $currentDate")
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setFontSize(12f)
                        .setMarginBottom(20f)
                    document.add(date)

                    // Get current data
                    val listaAlunos = viewModel.itemList.value ?: emptyList()
                    val aprovados = listaAlunos.filter { it.result == "Aprovado" }
                    val reprovados = listaAlunos.filter { it.result == "Reprovado" }

                    // Add approved students table
                    document.add(Paragraph("Alunos Aprovados").setFontSize(16f))
                    val tableAprovados = createResultsTable(aprovados)
                    document.add(tableAprovados)

                    // Add some space
                    document.add(Paragraph("\n"))

                    // Add failed students table
                    document.add(Paragraph("Alunos Reprovados").setFontSize(16f))
                    val tableReprovados = createResultsTable(reprovados)
                    document.add(tableReprovados)

                    // Add summary
                    document.add(Paragraph("\n"))
                    val summary = Paragraph("""
                        Resumo:
                        Total de Alunos: ${listaAlunos.size}
                        Aprovados: ${aprovados.size}
                        Reprovados: ${reprovados.size}
                    """.trimIndent())
                        .setFontSize(12f)
                    document.add(summary)
                }
            }

            // Open the PDF file
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Erro ao gerar relatório: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun createResultsTable(alunos: List<Aluno>): Table {
        val table = Table(UnitValue.createPercentArray(floatArrayOf(60f, 40f)))
            .useAllAvailableWidth()

        // Add header
        table.addHeaderCell(Cell().add(Paragraph("Nome").setBold()))
        table.addHeaderCell(Cell().add(Paragraph("Data").setBold()))

        // Add data
        alunos.forEach { aluno ->
            table.addCell(Cell().add(Paragraph(aluno.nome)))
            table.addCell(Cell().add(Paragraph(aluno.data)))
        }

        return table
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}