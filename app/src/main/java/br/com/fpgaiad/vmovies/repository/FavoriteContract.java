package br.com.fpgaiad.vmovies.repository;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

    public static final String AUTHORITY = "br.com.fpgaiad.vmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";

        //The id column will be automatically included since this class implements "BaseColumns"
        public static final String MOVIE_ID = "movie_id";

    }

}
