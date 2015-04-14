package chinapub.cf.recommander.recommander;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
//import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
//import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import chinapub.cf.recommander.model.table.BookSimilarityTable;

public final class ItemBasedRecommander implements Recommender { 

    private final Recommender recommender; 
	  /**
	   * @throws IOException if an error occurs while creating the {@link GroupLensDataModel}
	   * @throws TasteException if an error occurs while initializing this {@link GroupLensRecommender}
	   */
	  public ItemBasedRecommander() throws IOException, TasteException {
		  this(new BookDataModel());
	  }

	  /**
	   * <p>Alternate constructor that takes a {@link DataModel} argument, which allows this {@link Recommender}
	   * to be used with the {@link org.apache.mahout.cf.taste.eval.RecommenderEvaluator} framework.</p>
	   *
	   * @param dataModel data model
	   * @throws TasteException if an error occurs while initializing this {@link GroupLensRecommender}
	   */
	  public ItemBasedRecommander(DataModel dataModel) throws TasteException {
		  Collection<GenericItemSimilarity.ItemItemSimilarity> correlations = BookSimilarityTable.getAllBookSimilarities();
		  ItemSimilarity itemSimilarity = new GenericItemSimilarity(correlations);
		  recommender = new CachingRecommender(new EmbededItemBasedRecommender(new GenericItemBasedRecommender(dataModel, itemSimilarity)));
	  }

	  
	  public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		  return recommender.recommend(userID, howMany);
	  }

	  																						//Rescorer<Long> rescorer
	  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer)
	          throws TasteException {
	    return recommender.recommend(userID, howMany, rescorer);
	  }

	  //推荐算法计算相似度评分
	  public float estimatePreference(long userID, long itemID) throws TasteException {
	    return recommender.estimatePreference(userID, itemID);
	  }

	  
	  public void setPreference(long userID, long itemID, float value) throws TasteException {
	    recommender.setPreference(userID, itemID, value);
	  }

	  
	  public void removePreference(long userID, long itemID) throws TasteException {
	    recommender.removePreference(userID, itemID);
	  }

	  
	  public DataModel getDataModel() {
	    return recommender.getDataModel();
	  }

	  
	  public void refresh(Collection<Refreshable> alreadyRefreshed) {
	    recommender.refresh(alreadyRefreshed);
	  }

	  
	  public String toString() {
	    return "ItemBasedRecommender[itembasedrecommender:" + recommender + ']';
	  }
	  
	  private static final class EmbededItemBasedRecommender implements Recommender {

		    private final GenericItemBasedRecommender recommender;
		    

		    private EmbededItemBasedRecommender(GenericItemBasedRecommender recommender) {
		      this.recommender = recommender;
		    }

			public float estimatePreference(long userID, long itemID)
					throws TasteException {
				return recommender.estimatePreference(userID, itemID);
			}

			public DataModel getDataModel() {
				return recommender.getDataModel();
			}

			public List<RecommendedItem> recommend(long userID, int howMany)
					throws TasteException {
				//return recommend(userID, howMany);
				return recommend(userID, howMany, null);
			}
			
			/*public List<RecommendedItem> recommend(long userID,int howMany, Rescorer<Long> rescorer)
					throws TasteException {
				FastIDSet itemIDs = recommender.getDataModel().getItemIDsFromUser(userID);
				return recommender.mostSimilarItems(itemIDs.toArray(), howMany, null);
			}*/
			@Override
			public List<RecommendedItem> recommend(long userID, int howMany,IDRescorer rescorer) throws TasteException {
				return recommender.recommend(userID, howMany, rescorer);
			}
			
			public void removePreference(long userID, long itemID)
					throws TasteException {
				recommender.removePreference(userID, itemID);
				
			}

			public void setPreference(long userID, long itemID, float value)
					throws TasteException {
				recommender.setPreference(userID, itemID, value);
				
			}

			public void refresh(Collection<Refreshable> alreadyRefreshed) {
				recommender.refresh(alreadyRefreshed);
			}

		  }

/*	@Override
	public List<RecommendedItem> recommend(long userID, int howMany,
			IDRescorer rescorer) throws TasteException {
		// TODO Auto-generated method stub
		
		return null;
	}*/	  
 }
