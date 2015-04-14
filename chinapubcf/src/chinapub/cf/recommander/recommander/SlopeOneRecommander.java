package chinapub.cf.recommander.recommander;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.example.grouplens.GroupLensDataModel;
import org.apache.mahout.cf.taste.example.grouplens.GroupLensRecommender;
import org.apache.mahout.cf.taste.impl.recommender.AbstractRecommender;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
//import org.apache.mahout.cf.taste.recommender.Rescorer;

/**
 *  SlopeOne Recommeder 的实现
 *  @date Jul 30,2014
 * @author Fred
 * 
 */
public final  class SlopeOneRecommander implements Recommender{
	
	 private final Recommender recommender; 
	    
	    /**
	     * @throws IOException if an error occurs while creating the {@link GroupLensDataModel}
	     * @throws TasteException if an error occurs while initializing this {@link GroupLensRecommender}
	     */
	    public SlopeOneRecommander() throws IOException, TasteException {
	  	  this(new BookDataModel());
	    }

	    /**
	     * <p>Alternate constructor that takes a {@link DataModel} argument, which allows this {@link Recommender}
	     * to be used with the {@link org.apache.mahout.cf.taste.eval.RecommenderEvaluator} framework.</p>
	     *
	     * @param dataModel data model
	     * @throws TasteException if an error occurs while initializing this {@link GroupLensRecommender}
	     */
	    @SuppressWarnings("deprecation")
		public SlopeOneRecommander(DataModel dataModel) throws TasteException {
	    	// 创建一个 SlopeOneRecommender 的实例
	      recommender = new CachingRecommender(new SlopeOneRecommender(dataModel));
	    }

	 // 对外提供的推荐的接口，参数为用户标识和推荐项的个数
	    public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
	      return recommender.recommend(userID, howMany);
	    }

	    																	//Rescorer<Long>  IDRescorer  rescorer			
	  /*  public List<RecommendedItem> recommend(long userID, int howMany, Rescorer<Long> rescorer)
	            throws TasteException {
	      return recommender.recommend(userID, howMany, rescorer);
	    }*/

		@Override
		public List<RecommendedItem> recommend(long userID, int howMany,IDRescorer rescorer) throws TasteException {
			 return recommender.recommend(userID, howMany, rescorer);
		}
	    
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
	      return "SlopeOneRecommender[slopeonerecommender:" + recommender + ']';
	    }

}
