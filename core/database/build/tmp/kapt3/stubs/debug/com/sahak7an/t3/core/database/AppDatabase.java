package com.sahak7an.t3.core.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/sahak7an/t3/core/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "coursesDao", "Lcom/sahak7an/t3/core/database/CoursesDao;", "favoritesDao", "Lcom/sahak7an/t3/core/database/FavoritesDao;", "hiddenDao", "Lcom/sahak7an/t3/core/database/HiddenDao;", "database_debug"})
@androidx.room.Database(entities = {com.sahak7an.t3.core.database.FavoriteEntity.class, com.sahak7an.t3.core.database.CourseEntity.class, com.sahak7an.t3.core.database.HiddenEntity.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.sahak7an.t3.core.database.FavoritesDao favoritesDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.sahak7an.t3.core.database.CoursesDao coursesDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.sahak7an.t3.core.database.HiddenDao hiddenDao();
}