package com.lovable.boleta_service.service;

import com.lovable.boleta_service.dto.BoletaRequestDTO;
import com.lovable.boleta_service.dto.BoletaResponseDTO;
import java.util.List;

public interface IBoletaService {

    List<BoletaResponseDTO> obtenerTodos();
    BoletaResponseDTO obtenerPorId(Integer idBoleta);
    BoletaResponseDTO crear(BoletaRequestDTO request);

}
