package chinapub.cf.recommander.recommander;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;

import chinapub.cf.recommander.utils.DBUtil;

public class BookDataModel extends MySQLJDBCDataModel{

    /**
	 * @date Jul 30,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -2140687386155331287L;
	// 保存用户对图书评分的数据库表名
    public final static String RATINGTABLE = "rating";  
    public final static String USERID_COLUMN = "userId";   // 表中用户标识的列名
    public final static String ITEMID_COLUMN = "bookId";  // 表中图书标识的列名
    public final static String RATING_COLUMN = "rating";  // 表中评分的列名

    public BookDataModel(String dataSourceName) throws TasteException { 		
        super(lookupDataSource(dataSourceName), RATINGTABLE, USERID_COLUMN, ITEMID_COLUMN, RATING_COLUMN,""); 
    } 
    
    public BookDataModel() { 
        //DBUtil.getDataSource() 将返回应用的数据源
        // 此应用是 J2EE 应用，所以这里会采用 JDNI 的方式创建数据库链接。
        super(DBUtil.getDataSource(), RATINGTABLE, USERID_COLUMN, ITEMID_COLUMN, RATING_COLUMN,""); 
    } 
}
