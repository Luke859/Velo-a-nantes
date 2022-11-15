package com.formation.velo.service.impl;

import com.formation.velo.api.OpenData;
import com.formation.velo.api.client.parking.OpenDataNantesParking;
import com.formation.velo.model.Parking;
import com.formation.velo.repository.ParkingRepository;
import com.formation.velo.service.ParkingService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log
public class ParkingServiceImpl implements ParkingService{
    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(ParkingRepository repository) {
        this.parkingRepository = repository;
    }

    @Override
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Override
    public Optional<Parking> findById(Integer id) {
        return parkingRepository.findById(id);
    }

    @Override
    public Parking save(Parking parking) {
        return parkingRepository.save(parking);
    }

    @Override
    public void deleteById(Integer id) {
        parkingRepository.deleteById(id);
    }

    @Override
    public void delete(Parking parking) {
        parkingRepository.delete(parking);
    }

    @Override
    public void getDatas() {
        // 1- appel a openData
        String baseUrl = "https://data.nantesmetropole.fr/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenDataNantesParking client = retrofit.create(OpenDataNantesParking.class);
        Call<OpenData> openDataVeloNantesCall = client.getDatas();

        try{
            OpenData openDataVeloNantes = openDataVeloNantesCall.execute().body();
            log.info(openDataVeloNantes.toString());

            Arrays.stream(openDataVeloNantes.getRecords()).forEach(record -> {
                Optional<Parking> parkingToUpdate = findByRecordId(record.getRecordId());

                if(parkingToUpdate.isPresent()){
                    //On update la station
                    parkingToUpdate.get()
                            .setGrpDisponible(record.getField().getGrpDisponible());
                    parkingToUpdate.get()
                            .setGrpStatut(record.getField().getGrpStatut());
                    parkingToUpdate.get()
                            .setGrpExploitation(record.getField().getGrpExploitation());
                    parkingToUpdate.get()
                            .setGrpComplet(record.getField().getGrpComplet());

                    //on save
                    save(parkingToUpdate.get());
                }else {
                    // on crée le parking
                    Parking newParking = Parking.builder()
                            .recordId(record.getRecordId())
                            .grpDisponible(record.getField().getGrpDisponible())
                            .grpNom(record.getField().getGrpNom())
                            .grpStatut(record.getField().getGrpStatut())
                            .grpIdentifiant(record.getField().getGrpIdentifiant())
                            .disponibilite(record.getField().getDisponibilite())
                            .idobj(record.getField().getIdobj())
                            .grpComplet(record.getField().getGrpComplet())
                            .grpExploitation(record.getField().getGrpExploitation())
                            .build();

                            if (record.getField().getLocation() !=null) {
                                newParking.setLatitude(record.getField().getLocation()[0]);
                                newParking.setLongitude(record.getField().getLocation()[1]);
                                save(newParking);
                            } else {
                                newParking.setLatitude(0.0);
                                newParking.setLongitude(0.0);
                                save(newParking);
                            }
                    //on save
                    save(newParking);
                }
            });
            // 2- Save records dans notre table station

        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Parking> findByRecordId(String recordId) {
        return parkingRepository.findByRecordId(recordId);
    }

}
