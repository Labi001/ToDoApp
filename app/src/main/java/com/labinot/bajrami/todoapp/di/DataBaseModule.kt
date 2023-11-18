package com.labinot.bajrami.todoapp.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.labinot.bajrami.todoapp.data.ToDoDao
import com.labinot.bajrami.todoapp.data.ToDoDatabase
import com.labinot.bajrami.todoapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideToDoDao(toDoDatabase: ToDoDatabase): ToDoDao
            = toDoDatabase.todoDao()

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase
    = Room.databaseBuilder(

        context,
        ToDoDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

}