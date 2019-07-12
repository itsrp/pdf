package pdf;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextFragmentAbsorber;
import com.aspose.pdf.TextFragmentCollection;
import com.aspose.pdf.TextSearchOptions;

import java.util.HashMap;
import java.util.Map;

public class PdfProcessor {

    public static void replace(String find, String replace) {
        Document pdfDocument = new Document("6.pdf");

// Create TextAbsorber object to find all instances of the input search phrase
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(find);

        TextSearchOptions textSearchOptions = new TextSearchOptions(true);
        textFragmentAbsorber.setTextSearchOptions(textSearchOptions);
// Accept the absorber for first page of document
        pdfDocument.getPages().accept(textFragmentAbsorber);
// Get the extracted text fragments into collection
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();

// Loop through the fragments
        for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
            System.out.println("OK");
            // Update text and other properties
            textFragment.setText(replace);
            /*textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment.getTextState().setFontSize(22);
            textFragment.getTextState().setForegroundColor(Color.getBlue());
            textFragment.getTextState().setBackgroundColor(Color.getGray());*/
        }
// Save the updated PDF file
        pdfDocument.save("6.pdf");
        System.out.println("Saved successfully.");
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("559,754", "646,387");
        map.put("46,646", "53,866");
        map.put("1,301,752", "1,413,973");
        map.put("108,479", "117,831");
        map.put("65,088", "-");
        map.put("1,366,840", "1,413,973");
        map.put("23,800", "24,620");
        map.put("1,983", "2,052");
        map.put("59,360", "61,407");
        map.put("4,947", "5,117");
        map.put("83,160", "86,027");
        map.put("6,930", "7,169");
        map.put("1,450,000", "1,500,000");
        map.put("115,409", "125,000");

        map.forEach(PdfProcessor::replace);

    }
}
