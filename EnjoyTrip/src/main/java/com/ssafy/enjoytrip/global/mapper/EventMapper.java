package com.ssafy.enjoytrip.global.mapper;

import com.ssafy.enjoytrip.global.dto.Item;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface EventMapper {

    void lockTable() throws SQLException;

    Item getRandom() throws SQLException;

    void removeItem(int id) throws SQLException;

    void unlockTable() throws SQLException;
}
