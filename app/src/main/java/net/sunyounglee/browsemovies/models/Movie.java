package net.sunyounglee.browsemovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie implements Parcelable{

    @PrimaryKey
    @SerializedName("id")
    private long movieId;
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String poster_image;
    @SerializedName("backdrop_path")
    private String backDrop_image;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private float rating;
    @SerializedName("release_date")
    private String release_date;
    private boolean isFavorite;

    public Movie(long movieId, String title, String poster_image, String backDrop_image, String overview, float rating, String release_date,
                 boolean isFavorite) {
        this.movieId = movieId;
        this.title = title;
        this.poster_image = poster_image;
        this.backDrop_image = backDrop_image;
        this.overview = overview;
        this.rating = rating;
        this.release_date = release_date;
        this.isFavorite = isFavorite;
    }


    protected Movie(Parcel in) {
        movieId = in.readLong();
        title = in.readString();
        poster_image = in.readString();
        backDrop_image = in.readString();
        overview = in.readString();
        rating = in.readFloat();
        release_date = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public  long getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public String getBackDrop_image() {
        return backDrop_image;
    }

    public String getOverview() {
        return overview;
    }

    public float getRating() {
        return rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("Movie{")
                .append("movieId='" + movieId + '\'')
                .append("title='" + title + '\'')
                .append(", poster_image='" + poster_image + '\'')
                .append(", backdrop_image='" + backDrop_image + '\'')
                .append(", overview='" + overview + '\'')
                .append(", rating='" + rating + '\'')
                .append(", release_date='" + release_date + '\'')
                .append(", isFavorite = '" + isFavorite + '\'')
                .append('}').toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(title);
        dest.writeString(poster_image);
        dest.writeString(backDrop_image);
        dest.writeString(overview);
        dest.writeFloat(rating);
        dest.writeString(release_date);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
