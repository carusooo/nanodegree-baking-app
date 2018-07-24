package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface StepDao {

    @Insert
    void insertAll(Step... steps);

}
