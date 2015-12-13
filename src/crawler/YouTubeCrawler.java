package crawler;

import java.io.IOException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.common.collect.Lists;
import com.google.api.services.youtube.model.SearchResult;
import model.Search;
import test.Auth;


public class YouTubeCrawler implements Crawler{
	 private static YouTube youtube;
	// private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
	 @Override
	public void startSearch(Search s) throws Exception {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.force-ssl");
		try {
	            // Authorize the request.
	            Credential credential = Auth.authorize(scopes, "commentthreads");

	            // This object is used to make YouTube Data API requests.
	            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
	                    .setApplicationName("youtube-cmdline-commentthreads-sample").build();

	        
	            // Retrieve the order that the user is commenting to.
	            String order = s.getOrder();
	            System.out.println("You chose " + order + " to subscribe.");
	            
	            
	           //Scrivo la parola chiave e fa la ricerca del primo video riferito a quella parola chiave
	            String videoId = "";
	            String TitleVid;
	            YouTube.Search.List search = youtube.search().list("id,snippet");
	            search.setOrder(order);
	          //  search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
	            SearchListResponse searchResponse = search.execute();
	            List<SearchResult> searchResultList = searchResponse.getItems();
	            if (searchResultList != null) {
	            	videoId = searchResultList.get(0).getId().getVideoId(); 
	            	TitleVid = searchResultList.get(0).getSnippet().getTitle();
	            	System.out.println("  - VideoId: " + videoId);
	            	System.out.println("  - Title: " + TitleVid);
	            }
	            
	            // Call the YouTube Data API's commentThreads.list method to
	            // retrieve video comment threads.
	           
	            CommentThreadListResponse videoCommentsListResponse = youtube.commentThreads()
	                    .list("snippet").setVideoId(videoId).setOrder("relevance").setTextFormat("plainText").execute();
	            List<CommentThread> videoComments = videoCommentsListResponse.getItems();
	            if (videoComments.isEmpty()) {
	                System.out.println("Can't get video comments.");
	            } else {
	                // Print information from the API response.
	                System.out
	                        .println("\n================== Returned Video Comments ==================\n");
	                for (CommentThread videoComment : videoComments) {
	                    CommentSnippet snippet = videoComment.getSnippet().getTopLevelComment()
	                            .getSnippet();
	                    System.out.println("  - Author: " + snippet.getAuthorDisplayName());
	                    System.out.println("  - Comment: " + snippet.getTextDisplay());
	                    System.out.println("  - Date: " + snippet.getPublishedAt());
	                    System.out.println("  - LikeCount: " + snippet.getLikeCount());
	                    System.out
	                            .println("\n-------------------------------------------------------------\n");
	                }
	            }
	        } catch (GoogleJsonResponseException e) {
	            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode()
	                    + " : " + e.getDetails().getMessage());
	            e.printStackTrace();

	        } catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	            e.printStackTrace();
	        } catch (Throwable t) {
	            System.err.println("Throwable: " + t.getMessage());
	            t.printStackTrace();
	        }
	    }
		
	}
