package com.kevin.BatteryChangedRecord;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BatteryChangedTimeInformation.class}, version = 1, exportSchema = false)
  public abstract class BatteryDatabase extends RoomDatabase {
    public abstract BatteryChangedTimeInformationDao getUserDao();
  }