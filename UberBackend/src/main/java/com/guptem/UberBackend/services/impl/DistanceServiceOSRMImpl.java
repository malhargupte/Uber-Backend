package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.services.DistanceService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    @Override
    public double calculateDistance(Point source, Point destination) {
        return 0;
    }
}
