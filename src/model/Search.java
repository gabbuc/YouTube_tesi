package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Search {
	/*The order parameter specifies the method that will be used to order resources in 
	 * the API response. The default value is relevance.
	 *Acceptable values are: date,rating,relevance,title,videoCount, viewCount. */
	String order;

	public Search() {}

	public Search(String order) {
		this.order = order;
	}

	public String getOrder() throws IOException {
		System.out.print("Please enter a order (date,rating,relevance,title,videoCount, viewCount): ");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		order = bReader.readLine();
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
