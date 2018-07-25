package com.example.macarus0.bakingapp.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface StepDao {

    @Insert
    void insertAll(Step... steps);

    @Query("SELECT * from step WHERE stepId = :stepId")
    LiveData<Step> getStepById(int stepId);
}
