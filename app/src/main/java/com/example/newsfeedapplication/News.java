package com.example.newsfeedapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "news")
    public class News {

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        public int id;

        @ColumnInfo(name = "title")
        public String title;

        @ColumnInfo(name = "description")
        public String description;

        @ColumnInfo(name = "img")
        public int img;

        @Ignore
        public boolean exists;

    public static final Comparator<News> By_TITLE_ASCEDING = new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                return o1.title.compareTo(o2.title);
            }
        };

        public static final Comparator<News> By_TITLE_DESCENDING = new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                return o2.title.compareTo(o1.title);
            }
        };

}
