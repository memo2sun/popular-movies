package net.sunyounglee.browsemovies.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trailer")
public class Trailer {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String trailerId;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("key")
    private String trailerKey;
    @SerializedName("name")
    private String trailerName;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private String screenResolution;
    @SerializedName("type")
    private String trailerType;
    private long trailer_movieId;

    public Trailer(@NonNull String trailerId, String iso_639_1, String iso_3166_1, String trailerKey, String trailerName, String site, String screenResolution, String trailerType, long trailer_movieId) {
        this.trailerId = trailerId;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.trailerKey = trailerKey;
        this.trailerName = trailerName;
        this.site = site;
        this.screenResolution = screenResolution;
        this.trailerType = trailerType;
        this.trailer_movieId = trailer_movieId;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public String getSite() {
        return site;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public long getTrailer_movieId() {
        return trailer_movieId;
    }

    public void setTrailer_movieId(long trailer_movieId) {
        this.trailer_movieId = trailer_movieId;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("Trailer{")
                .append("trailerId='" + trailerId + '\'')
                .append(", iso_639_1='" + iso_639_1 + '\'')
                .append(", iso_3166_1='" + iso_3166_1 + '\'')
                .append(", trailerKey='" + trailerKey + '\'')
                .append(", trailerName='" + trailerName + '\'')
                .append(", site='" + site + '\'')
                .append(", screenResolution='" + screenResolution + '\'')
                .append(", trailerType='" + trailerType + '\'')
                .append('}').toString();
    }
}
