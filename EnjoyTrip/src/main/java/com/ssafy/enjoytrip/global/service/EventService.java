package com.ssafy.enjoytrip.global.service;

import com.ssafy.enjoytrip.global.dto.Item;
import com.ssafy.enjoytrip.global.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventMapper mapper;

    public Item play() throws Exception {
        mapper.lockTable();

        Item item = mapper.getRandom();

        mapper.removeItem(item.getId());

        mapper.unlockTable();

        return item;
    }
}
