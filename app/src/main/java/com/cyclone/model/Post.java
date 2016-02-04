package com.cyclone.model;

/**
 * Created by gilang on 17/10/2015.
 */
public class Post {

	public static final int TYPE_POST = 100;
	public static final int TYPE_NEWS = 101;
	public String imgUrl;
	public String headerName;
	public String timestamp;
	public String playlistType;
	public String postImgUrl;
	public String postTitle;
	public String postContent;
	public String postInfo;
	public int likesCount;
	public int commentCount;
	public int type;
	public boolean isLiked;

	public String username;
	public String FeedId;
	public String TypeId;
	public String userId;

	public Post(String imgUrl, String headerName, String timestamp, String playlistType, String
			postImgUrl, String postTitle, String postContent, String postInfo, int likesCount,
				int commentCount, int type, boolean isLiked){
		this.imgUrl = imgUrl;
		this.headerName = headerName;
		this.timestamp = timestamp;
		this.playlistType =  playlistType;
		this.postImgUrl = postImgUrl;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postInfo =  postInfo;
		this.likesCount = likesCount;
		this.commentCount = commentCount;
		this.type = type;
		this.isLiked = isLiked;
	}
	public Post(String imgUrl, String headerName, String timestamp, String playlistType, String
			postImgUrl, String postTitle, String postContent, String postInfo, int likesCount,
				int commentCount, int type, boolean isLiked, String userId, String username, String FeedId, String TypeId) {
		this.imgUrl = imgUrl;
		this.headerName = headerName;
		this.timestamp = timestamp;
		this.playlistType =  playlistType;
		this.postImgUrl = postImgUrl;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postInfo =  postInfo;
		this.likesCount = likesCount;
		this.commentCount = commentCount;
		this.type = type;
		this.isLiked = isLiked;
		this.userId = userId;
		this.username = username;
		this.FeedId = FeedId;
		this.TypeId = TypeId;
	}
}
