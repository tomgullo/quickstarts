@Grapes([
    @Grab('org.apache.mahout:mahout-core:0.8'),
])
 
import org.apache.mahout.cf.taste.model.DataModel
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.similarity.UserSimilarity
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.recommender.*
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender
 
def itemId = 23
def numberOfRecommendations = 5
def inputFileName = "ua.base"
DataModel model = new FileDataModel(new File(inputFileName)) //get data (users, items, preferences)
def similarity = new LogLikelihoodSimilarity(model)
Recommender recommender = new GenericItemBasedRecommender( model, similarity) //new recommendation engine
List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, numberOfRecommendations) //get recommendation(s) based on item selected
 
recommendations.each {  println it } //print the recommendations and level of confidence
