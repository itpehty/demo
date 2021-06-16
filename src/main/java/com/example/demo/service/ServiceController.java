package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.batch.IndexDBToES;
import com.example.demo.service.SearchResult;

/**
 * API Controller Class
 *
 */
@Controller
public class ServiceController {

	private static Logger logger = LoggerFactory.getLogger(IndexDBToES.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Health Check 용 API
	 * @return
	 */
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public ResponseEntity<String> health() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	/**
	 * 통신판매사업자 검색 API
	 * @param text
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ResponseEntity<SearchResult> search(@RequestParam("text") String text) {

		SearchResult searchResult = new SearchResult();

		try (RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(
				"search-search1-dlpgcpwswcgbfwu4artmbmyrde.ap-northeast-2.es.amazonaws.com", 443, "https")))) {
			SearchRequest request = new SearchRequest();
			SearchSourceBuilder builder = new SearchSourceBuilder();
			builder.query(QueryBuilders.multiMatchQuery(text, "bizname", "bizregno", "ceoname", "domain", "product"));
			builder.size(1000);
			request.source(builder);

			List<Map<String, Object>> items = new ArrayList<>();

			SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
			for (SearchHit s : searchResponse.getHits().getHits()) {
				Map<String, Object> sourceMap = s.getSourceAsMap();
				items.add(sourceMap);
			}

			TimeValue took = searchResponse.getTook();
			TotalHits totalHits = searchResponse.getHits().getTotalHits();
			
			searchResult.setText(text);
			searchResult.setTook(took.getMillis());
			searchResult.setTotalhits(totalHits.value);
			searchResult.setItems(items);
			
			logger.info(searchResult.toString());

		} catch (Exception e) {
			logger.error("search", e);
		}

		return new ResponseEntity<SearchResult>(searchResult, HttpStatus.OK);
	}

	/**
	 * 통신판매사업자 조회 API (사업자번호 기준 1건 조회)
	 * 
	 * @param bizregno
	 * @return
	 */
	@RequestMapping(value = "/biz/{bizregno}", method = { RequestMethod.GET })
	public ResponseEntity<List<Map<String, Object>>> biz(@PathVariable String bizregno) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			Map<String, Object> one = jdbcTemplate.queryForMap(
					"select bizregno,regno,orgname,bizname,crp_prv_cd,ceoname,tel,email,regdate,address,address_road,status,orgtel,salestype,product,domain,hostlocation from onl_mkt_biz_mst where bizregno = ?",
					bizregno);
			result.add(one);
			logger.info("BizResult [" + bizregno + ",O]");
			
		} catch (EmptyResultDataAccessException e) {
			logger.info("BizResult [" + bizregno + ",X]");
		}
		return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
	}
}