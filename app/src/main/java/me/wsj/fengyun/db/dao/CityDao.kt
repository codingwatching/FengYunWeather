package me.wsj.fengyun.db.dao

import androidx.room.*
import me.wsj.fengyun.db.entity.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: CityEntity): Long

    @Query("select * from city")
    fun getCities(): List<CityEntity>

    @Delete
    fun deleteCache(city: CityEntity): Int

}