@Grapes([
    @Grab('org.apache.mahout:mahout-core:0.8'),
])
 
import org.apache.mahout.cf.taste.model.DataModel
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.similarity.UserSimilarity
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood
import org.apache.mahout.cf.taste.recommender.*
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender
 
def userId = 452
def neighborhoodSize = 5
def numberOfRecommendations = 5
 
def inputFileName = "ua.base"
DataModel model = new FileDataModel(new File(inputFileName)) //get data (users, items, preferences)
UserSimilarity similarity = new PearsonCorrelationSimilarity(model) //identify similar users
UserNeighborhood neighborhood = new NearestNUserNeighborhood(neighborhoodSize, similarity, model) //establish neighborhoods of N similar users
Recommender recommender = new GenericUserBasedRecommender( model, neighborhood, similarity) //instantiate a new recommendation engine
List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations) //get recommendation(s) for user
 
println recommendations
