package edu.arimanius.inator.data.dao

import androidx.room.Dao
import edu.arimanius.inator.data.entity.Group

@Dao
interface GroupDao: InsertableDao<Group>
