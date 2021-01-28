package com.kevin.BatteryChangedRecord;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
  public interface BatteryChangedTimeInformationDao {
    @Query("SELECT * FROM BatteryChangedTimeInformation")
    List<BatteryChangedTimeInformation> getAll();

    @Query("SELECT * FROM BatteryChangedTimeInformation WHERE id IN (:BatteryChangedTimeInformationIds)")
    List<BatteryChangedTimeInformation> loadAllByIds(int[] BatteryChangedTimeInformationIds);

    @Query("SELECT * FROM BatteryChangedTimeInformation WHERE current_time LIKE :current_time AND " +
            "battery_remained LIKE :battery_remained LIMIT 1")
    BatteryChangedTimeInformation findByName(String current_time, String battery_remained);

    @Insert
    void insertAll(BatteryChangedTimeInformation... BatteryChangedTimeInformations);

    @Insert
    public void insertBothBatteryChangedTimeInformations(BatteryChangedTimeInformation BatteryChangedTimeInformation1, BatteryChangedTimeInformation BatteryChangedTimeInformation2);

    @Insert
    public void insertBatteryChangedTimeInformationsAndFriends(BatteryChangedTimeInformation BatteryChangedTimeInformation, List<BatteryChangedTimeInformation> friends);

    @Delete
    void delete(BatteryChangedTimeInformation BatteryChangedTimeInformation);
  }