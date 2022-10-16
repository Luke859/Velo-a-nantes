package com.formation.velo.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenDataNantesClient {
    
    @GET("/api/records/1.0/search/?dataset=244400404_stations-velos-libre-service-nantes-metropole-disponibilites&q=&facet=banking&facet=bonus&facet=status&facet=contract_name")
    Call<OpenDataVeloNantes> getRecords();
}
