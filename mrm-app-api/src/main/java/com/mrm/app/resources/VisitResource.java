package com.mrm.app.resources;

import com.mrm.app.handlers.VisitsApi;
import com.mrm.app.models.InlineResponse200;
import com.mrm.app.models.Visit;
import com.mrm.app.models.Visits;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VisitResource implements VisitsApi {

    @Override
    public ResponseEntity<Visits> getUserVisits() {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Visit> getVisitDetails(String visitId) {
        return null;
    }
}
