package com.example.utils

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.UserAssignmentSubmission
import com.itextpdf.text.BaseColor
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.datetime.Instant
import org.h2.store.fs.FileUtils.R
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import kotlin.time.toJavaDuration
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable


object CsvUtils {
    fun generateCsv(assignments: List<Assignment>, userSubmissions: List<UserAssignmentSubmission>) {
        val headers = mutableListOf<Assignment>()
        val records = mutableListOf<Record>()
        assignments.forEachIndexed { index, assignment ->
            headers.add(assignment)
            var list = MutableList(assignments.size) { "" }
            userSubmissions.filter { it.assignmentId == assignment.id }.forEach { submission ->
                list = MutableList(assignments.size) { "" }
                list.set(index,submission.grade.toString())
                records.add(
                    Record(
                        submission.userName,
                        list
                    )
                )
            }
        }

        val csvFile = "outut.csv"
        println("userName,"+headers.joinToString(",") { it.title })
        records.forEach { record ->
            println("${record.userName} ,${record.grade.joinToString(",") { it }}")
        }
        FileWriter(csvFile).use { writer ->
            writer.append("User Name," +headers.joinToString(",") { it.title+" "+"[/${it.maxPoints}]" }+"\n") // Add header
            records.forEach { record ->
                writer.append("${record.userName} ,${record.grade.joinToString(",") { it }}\n")
            }
        }

    }
    fun convertCsvToPdf(csvFile: String, pdfFile: String) {
        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream(pdfFile))
        document.open()

        val reader = BufferedReader(FileReader(csvFile))
        var line: String?
        var table: PdfPTable? = null
        var rowIndex = 0

        while (reader.readLine().also { line = it } != null) {
            val cells = line?.split(",")
            if (table == null) {
                // Create table with the number of columns equal to the number of cells in the first row
                if (cells != null) {
                    table = PdfPTable(cells.size)
                }
            }
            if (cells != null) {
                for ((columnIndex, cell) in cells.withIndex()) {
                    val font = Font()
                    val paragraph = com.itextpdf.text.Paragraph(cell, font)
                    val pdfCell = PdfPCell(paragraph)
                    pdfCell.addElement(paragraph)
                    pdfCell.horizontalAlignment = Element.ALIGN_CENTER // Align cell content in the center
                    pdfCell.verticalAlignment = Element.ALIGN_MIDDLE // Align cell content in the middle
                    // Highlight the top row and the first column
                    if (rowIndex == 0 || columnIndex == 0) {
                        pdfCell.backgroundColor = BaseColor.DARK_GRAY
                        font.color = BaseColor.WHITE
                        font.style = Font.BOLD
                    }
                    if (cell.isEmpty()) {
                        pdfCell.backgroundColor = BaseColor.RED
                    }
                    table?.addCell(pdfCell)
                }
            }
            rowIndex++
        }

        if (table != null) {
            document.add(table)
        }

        document.close()
        reader.close()
    }
// ...


}

data class Record(val userName: String, val grade: List<String>)
