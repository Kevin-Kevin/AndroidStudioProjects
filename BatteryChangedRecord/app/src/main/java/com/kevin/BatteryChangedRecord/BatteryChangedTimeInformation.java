package com.kevin.BatteryChangedRecord;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BatteryChangedTimeInformation {
  @PrimaryKey
  public int id;

  @ColumnInfo(name = "current_time")
  long currentTime;
  @ColumnInfo(name = "battery_remained")
  long batteryRemained;

}