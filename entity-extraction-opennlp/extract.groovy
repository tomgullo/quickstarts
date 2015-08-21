@Grapes([
    @Grab(group='org.apache.opennlp', module='opennlp-tools', version='1.5.3'),
])
  
import opennlp.tools.tokenize.*
import opennlp.tools.namefind.*
import opennlp.tools.sentdetect.*
import opennlp.tools.util.*
  
String sentenceModelPath = "/home/user1/Downloads/en-sent.bin"
String personModelPath = "/home/user1/Downloads/en-ner-person.bin"
String locationModelPath = "/home/user1/Downloads/en-ner-location.bin"
String organizationModelPath = "/home/user1/Downloads/en-ner-organization.bin"
  
SentenceDetectorME sentDetector = new SentenceDetectorME(new SentenceModel(
    new FileInputStream(sentenceModelPath)));
NameFinderME personFinder = new NameFinderME(new TokenNameFinderModel(
    new FileInputStream(personModelPath)));
NameFinderME locationFinder = new NameFinderME(new TokenNameFinderModel(
    new FileInputStream(locationModelPath)));
NameFinderME organizationFinder = new NameFinderME(new TokenNameFinderModel(
    new FileInputStream(organizationModelPath)));
Tokenizer tokenizer = SimpleTokenizer.INSTANCE
  
def doc = """
   House and Senate lawmakers agreed to a bipartisan compromise to fund the U.S. government through Sept. 30,
   unveiling the measure days before financing for federal agencies is scheduled to lapse.
   The \$1.01 trillion measure would fund the troubled health care law and individual agencies, plus add separate
   war financing to bring the total to \$1.1 trillion. Republican efforts to derail some regulatory initiatives were
   left out of the measure to ensure passage and avoid a repeat of the 16-day partial government shutdown in October.
   The bill, announced last night by lawmakers including House Appropriations Committee Chairman Hal Rogers, a
   Kentucky Republican, and Senate Appropriations Chairman Barbara Mikulski, a Maryland Democrat, probably will reach
   the House floor tomorrow, Rogers said.
"""
  
  
def sentences = sentDetector.sentDetect(doc)
  
def foundPeople = new HashSet()
def foundPlaces = new HashSet()
def foundOrgs = new HashSet()
sentences.each { sentence -&amp;amp;gt;
  
    String[] tokens = tokenizer.tokenize(sentence)
  
    Span.spansToStrings(personFinder.find(tokens), tokens).each { foundPeople << it }
    Span.spansToStrings(locationFinder.find(tokens), tokens).each { foundPlaces << it }
    Span.spansToStrings(organizationFinder.find(tokens), tokens).each { foundOrgs << it }
}
  
println foundPeople
//[Barbara Mikulski, Rogers, Hal Rogers]
  
println foundPlaces
//[U . S ., Kentucky, Maryland]
  
println foundOrgs
//[Senate, Senate Appropriations Chairman Barbara Mikulski, House, House Appropriations Committee]

