package net.sunyounglee.browsemovies.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "review")
public class Review {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String reveviewId;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String reviewUrl;
    private long review_movieId;

    public Review(@NonNull String reveviewId, String author, String content, String reviewUrl, long review_movieId) {
        this.reveviewId = reveviewId;
        this.author = author;
        this.content = content;
        this.reviewUrl = reviewUrl;
        this.review_movieId = review_movieId;
    }

    public String getReveviewId() {
        return reveviewId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public long getReview_movieId() {
        return review_movieId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReview_movieId(long review_movieId) {
        this.review_movieId = review_movieId;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("Review{")
                .append("reveviewId='" + reveviewId + '\'')
                .append(", author='" + author + '\'')
                .append(", content='" + content + '\'')
                .append(", reviewUrl='" + reviewUrl + '\'')
                .append('}').toString();
    }
}
