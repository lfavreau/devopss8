/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vehiculos.VehiculosRest.controllers;

import com.vehiculos.VehiculosRest.models.VehiculoModel;
import com.vehiculos.VehiculosRest.services.VehiculoService;
import com.vehiculos.VehiculosRest.services.VehiculoService.UserNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Villacura
 */
@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    
    
    @Autowired
    private VehiculoService vehiculoService;
    
    @GetMapping
    public ArrayList<VehiculoModel> getAutos(){
        return this.vehiculoService.getVehiculos();
    }
    
    // C
    @PostMapping
    public ResponseEntity<VehiculoModel> createVehiculo(@RequestBody VehiculoModel nuevo) {
        VehiculoModel creado = vehiculoService.saveAuto(nuevo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }

    // R
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoModel> getVehiculo(@PathVariable Long id) {
        return vehiculoService.getbyId(id)
            .map(v -> ResponseEntity.ok(v))
            .orElse(ResponseEntity.notFound().build());
    }
    
    // U
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoModel> updateVehiculo(
            @PathVariable Long id,
            @RequestBody VehiculoModel cambios) {
        try {
            VehiculoModel actualizado = vehiculoService.updateById(cambios, id);
            return ResponseEntity.ok(actualizado);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // D
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
        try {
            vehiculoService.deleteAuto(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
}


   
}
