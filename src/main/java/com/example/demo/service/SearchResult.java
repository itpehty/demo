
package com.example.demo.service;

import java.util.List;
import java.util.Map;

/**
 * 통신판매사업자 검색결과 DTO
 * @author paten
 *
 */
public class SearchResult {

	private String text;
	private long took = 0;
	private long hits = 0;
	private long totalhits = 0;
	private List<Map<String, Object>> items;

	public SearchResult() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTook() {
		return took;
	}

	public void setTook(long took) {
		this.took = took;
	}

	public long getHits() {
		return hits;
	}

	public long getTotalhits() {
		return totalhits;
	}

	public void setTotalhits(long totalhits) {
		this.totalhits = totalhits;
	}

	public List<Map<String, Object>> getItems() {
		return items;
	}

	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
		this.hits = (items != null) ? items.size() : 0;
	}

	@Override
	public String toString() {
		return "SearchResult [text=" + text + ", took=" + took + ", totalhits=" + totalhits + ", hits=" + hits + "]";
	}
}
