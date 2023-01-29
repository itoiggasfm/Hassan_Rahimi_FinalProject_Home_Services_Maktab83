package com.maktabsharif.repository;

import com.maktabsharif.entity.BaseEntity;
import com.maktabsharif.entity.Services;

public class ServiceRepository extends BaseRepository<Services> {

    public ServiceRepository() {
        super("Services", Services.class);
    }
}
