@Grapes([
  @Grab('edu.stanford.nlp:stanford-corenlp:3.4.1')
])
 
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple
 
String textToExtract = "This is a sentence about " + 
  "President Obama in Washington DC"
 
//setup
Properties props = new Properties();
props.load(new FileInputStream(
    new File("all.3class.distsim.prop")));
 
AbstractSequenceClassifier namedEntityRecognizer = 
  (AbstractSequenceClassifier<CoreMap>) 
      CRFClassifier.
         getClassifier("all.3class.distsim.crf.ser.gz", props);
 
//extract
List<Triple<String, Integer, Integer>> extractedEntities = 
  namedEntityRecognizer.classifyToCharacterOffsets(textToExtract);
for (e in extractedEntities) {
  //may be person, location or organization 
  println "\ntype: " + e.first.toLowerCase() 
  println "word: " + textToExtract.substring(e.second(), e.third()) 
}
 
//output
//type: person
//word: Obama
 
//type: location
//word: Washington DC
