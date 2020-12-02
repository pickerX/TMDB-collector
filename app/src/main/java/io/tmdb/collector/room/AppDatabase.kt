package io.tmdb.collector.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.tmdb.collector.data.Movie
import io.tmdb.collector.data.Person
import io.tmdb.collector.room.conveter.*

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:33 AM
 */
@Database(
    entities = [Movie::class, Person::class],
    version = 1, exportSchema = false
)
@TypeConverters(
    IntListConverter::class, VideoListConverter::class,
    ReviewListConverter::class, StringListConverter::class,
    KeywordListConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun peopleDao(): PeopleDao
}
