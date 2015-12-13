package test;

import java.io.IOException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;

import crawler.YouTubeCrawler;
import model.Search;

public class Main {
	
	public static void main(String[] args) throws IOException {
		YouTubeCrawler youtube = new YouTubeCrawler();

		Search s = new Search();
	
		try {
			youtube.startSearch(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	}


