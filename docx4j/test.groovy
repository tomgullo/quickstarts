@Grapes([
	@Grab('org.docx4j:docx4j:3.0.0')
])

import org.docx4j.XmlUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");
wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle", "This is a subtitle!");
def xml = XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true)

println xml

FileOutputStream fos = new FileOutputStream("testfile")
Docx4J.toPDF(wordMLPackage,fos)




        

