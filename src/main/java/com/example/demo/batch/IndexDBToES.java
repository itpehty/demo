package com.example.demo.batch;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.batch.OnlMktBiz;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AWS RDS PostgreSQL 에서 데이터를 읽어서 
 * AWS ElasticSearch 에 인덱싱하는 클래스
 * 
 * @author paten
 *
 */
public class IndexDBToES {
	
	private static Logger logger = LoggerFactory.getLogger(IndexDBToES.class);

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		ObjectMapper objectMapper = new ObjectMapper();

		try (RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("search-search1-dlpgcpwswcgbfwu4artmbmyrde.ap-northeast-2.es.amazonaws.com", 443, "https")))) {

			PostgresDAO dao = new PostgresDAO();
			
			String lastBizregno = "0000000000";
			List<OnlMktBiz> list = dao.selectOnlMktBizs(lastBizregno);
			
			while(list.size() > 0) {
				int total = 0;			
				BulkRequest bulkRequest = new BulkRequest();			
				for (int i = 0; i < list.size(); i++) {				
					lastBizregno = String.valueOf(list.get(i).getBizregno());				
					Map<String, Object> documentMapper = objectMapper.convertValue(list.get(i), Map.class);
					bulkRequest.add(new IndexRequest("onl_mkt_biz_mst").source(documentMapper).id(lastBizregno));
					
					// 1000건씩 나눠서 Bulk Indexing
					if(i % 1000 == 0) {
						client.bulk(bulkRequest, RequestOptions.DEFAULT);
						total += bulkRequest.numberOfActions();
						logger.info(total + " documents are indexed.");
						bulkRequest = new BulkRequest();
					}
				}
				
				if(bulkRequest.numberOfActions() > 0) {
					client.bulk(bulkRequest, RequestOptions.DEFAULT);
					total += bulkRequest.numberOfActions();
					logger.info(total + " documents are indexed.");
				}
				
				logger.info("last bizregno : " + lastBizregno);
				list = dao.selectOnlMktBizs(lastBizregno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
