import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfReaderContentParser
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy
import com.itextpdf.text.pdf.parser.TextExtractionStrategy
import org.apache.poi.xwpf.usermodel.BreakType
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.FileOutputStream


fun main(args: Array<String>) {
    //Create the word document
    val doc = XWPFDocument()

    // Open the pdf file
    val pdf = args[0]
    val reader = PdfReader(pdf)
    val parser = PdfReaderContentParser(reader)

    // Read the PDF page by page
    for (i in 1..reader.numberOfPages) {
        val strategy: TextExtractionStrategy = parser.processContent(i, SimpleTextExtractionStrategy())
        // Extract the text
        val text: String = strategy.resultantText
        // Create a new paragraph in the word document, adding the extracted text
        val p: XWPFParagraph = doc.createParagraph()
        val run: XWPFRun = p.createRun()
        run.setText(text)
        // Adding a page break
        run.addBreak(BreakType.PAGE)
    }

    // Write the word document
    val out = FileOutputStream("myfile.docx")
    doc.write(out)

    // Close all open files
    out.close()
    reader.close()
}