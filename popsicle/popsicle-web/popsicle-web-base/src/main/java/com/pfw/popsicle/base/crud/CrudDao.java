package com.pfw.popsicle.base.crud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CrudDao <E> {

	
	List<E> findPage(@Param(value = "e")E e,@Param(value = "start")int start, @Param(value = "pagesize")int pagesize, @Param(value = "orderName")String orderName,  @Param(value = "orderType")String orderType);
	long  findPageSize(@Param(value = "e")E e);
	E getById(@Param(value = "id")Long id);
	void save(E e);
	void update(@Param(value = "e")E e);
	void delete(Long id);
	
//void batchSave(@Param(value = "prices") List<Kschema> prices);
	
	
//	
//	@InsertProvider(type = KschemaMapperProvider.class, method = "batchSaveDuration")
////	@ResultMap
//    void batchSaveDuration(@Param(value = "list")List<Kschema> prices,@Param(value = "duration") String duration);
// 
//
////    @Select("select * from t_kschema where time>#{openTime} and time<#{closeTime} ")
////    @ResultMap("KSchemaMap")
////	List<Kschema> find(Date openTime, Date closeTime,Long limit);
//
//
//
//    @SelectProvider(type = KschemaMapperProvider.class, method = "findDuration")
//	List<Kschema> findDuration(@Param(value = "duration")String duration,@Param(value = "openTime") Date openTime,@Param(value = "closeTime") Date closeTime,@Param(value = "limit")Long limit);
//    
//    
//    
//    public static class KschemaMapperProvider {
//        @SuppressWarnings("unchecked")
//		public String batchSaveDuration(Map<String, Object> map) {
//            List<Kschema> list = (List<Kschema>)map.get("list");
//            String duration = (String)map.get("duration");
//            StringBuilder stringBuilder = new StringBuilder();
//            
//            if(StringUtils.isNotBlank(duration)){
//            	 stringBuilder.append("insert into t_kschema_"+duration+" (ticker,time,day_str,time_str,open,close,high,low,volume) VALUES "); 
//            }else{
//            	 stringBuilder.append("insert into t_kschema (ticker,time,day_str,time_str,open,close,high,low,volume) VALUES "); 
//            }
//            
//            MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].ticker},#'{'list[{0}].time},#'{'list[{0}].dayStr},#'{'list[{0}].timeStr},#'{'list[{0}].open},#'{'list[{0}].close},#'{'list[{0}].high},#'{'list[{0}].low},#'{'list[{0}].volume})");
//            for (int i = 0; i < list.size(); i++) {
//                stringBuilder.append(messageFormat.format(new Integer[]{i}));
//                stringBuilder.append(",");
//            }
//            stringBuilder.setLength(stringBuilder.length() - 1);
//            
//            return stringBuilder.toString();
//        }
//        
//        public String findDuration(Map<String, Object> map) {
//            String duration = (String)map.get("duration");
//            Date openTime = (Date)map.get("openTime");
//            Date closeTime = (Date)map.get("closeTime");
//            Long limit = (Long)map.get("limit");
////            BEGIN();
////            SELECT("*"); 
////            if(StringUtils.isNotBlank(duration)){
////            	FROM(" t_kschema_"+duration);  
////            }else{
////            	FROM(" t_kschema"); 
////            }
////            if(closeTime!=null){
////            	if(limit!=null){
////            		WHERE("time>#{openTime} and time<#{closeTime} limit 0,#{limit}"); 
////                }else{
////                	WHERE("time>#{openTime} and time<#{closeTime}"); 
////                }
////            }else{
////            	if(limit!=null){
////            		WHERE("time>#{openTime} limit 0,#{limit}"); 
////                }else{
////                	WHERE("time>#{openTime}");  
////                }
////            }
////            return SQL();
//        	
//        	
//        	 StringBuilder stringBuilder = new StringBuilder();
//             
//        	 
//             if(StringUtils.isNotBlank(duration)){
//             	 stringBuilder.append("select * from t_kschema_"+duration); 
//             }else{
//            	 stringBuilder.append("select * from t_kschema");
//             }
//             boolean hasWhere = false;
//             boolean hasOps = false;
//             if(openTime!=null){
//            	 if(!hasWhere){
//            		 stringBuilder.append(" where time>#{openTime}");
//            		 hasOps = true;
//            		 hasWhere = true;
//            	 }else{
//            		 stringBuilder.append(" time>#{openTime}");
//            		 hasOps = true;
//            	 }
//             }
//             if(closeTime!=null){
//            	 if(!hasWhere){
//            		 stringBuilder.append(" where time<#{closeTime}");
//            		 hasWhere = true;
//            	 }else{
//            		 if(hasOps){
//            			 stringBuilder.append(" and time<#{closeTime}");
//            			 hasOps = true;
//            		 }else{
//            			 stringBuilder.append(" time<#{closeTime}");
//            			 hasOps = true;
//            		 }
//            	 }
//             }
//             if(limit!=null){
//            	 stringBuilder.append(" limit 0,#{limit} ");
//             }
//            	 
//             return stringBuilder.toString();
//        }
//    }
}
