package com.example.demo.batch;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.batch.OnlMktBiz;

/**
 * 통신판매사업자 원본 파일 전체를 읽어서 
 * AWS RDS PostgreSQL DB 에 저장하는 클래스
 *  
 */
public class InsertFileToDB {

	private static Logger logger = LoggerFactory.getLogger(InsertFileToDB.class);

	public static void main(String[] args) {
		try {
			PostgresDAO dao = new PostgresDAO();
			String dir = "C:/data/trans";
			File idir = new File(dir);
			File files[] = idir.listFiles();

			for (File f : files) {
				int cnt = 0;

				String fileLocation = dir + "/" + f.getName();
				logger.info(fileLocation);
				Path path = Paths.get(fileLocation);

				List<OnlMktBiz> list = new ArrayList<>();
				BufferedReader reader = Files.newBufferedReader(path);

				String line = reader.readLine(); // 첫번째 헤더 라인 버림
				
				while (line != null) {
					line = reader.readLine();
					if (line != null) {
						try {
							// csv 파일 데이터 중 CR/LF 가 포함되어 한줄에 17개 컬럼을 만족하지 못하는 데이터는 Skip 한다.
							if(countChar(line, ',') == 16) {
								OnlMktBiz o = new OnlMktBiz(line);
								list.add(o);
								cnt++;
								
								// 1000 건씩 Batch 처리
								if (cnt % 1000 == 0) {
									dao.insertOnlMktBizList(list);
									logger.info(cnt + " rows inserted.");
									list = new ArrayList<>();
								}
							}
						} catch (Exception e) {
							logger.error(line, e);
						}
					}
				}

				dao.insertOnlMktBizList(list);
				logger.info(cnt + " rows inserted.");
			}

		} catch (Exception e) {
			logger.error("Main", e);
		}
	}

	public static int countChar(String str, char ch) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				count++;
			}
		}
		return count;
	}
}
