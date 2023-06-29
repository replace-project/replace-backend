package com.replace.re.place.store.service;

import com.replace.re.place.store.dao.StoreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreSercvice {

    private final StoreDao storeDao;

}
