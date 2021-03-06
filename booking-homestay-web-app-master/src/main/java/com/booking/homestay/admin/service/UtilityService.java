package com.booking.homestay.admin.service;

import com.booking.homestay.admin.dto.UtilityRequest;
import com.booking.homestay.admin.dto.UtilityResponse;
import com.booking.homestay.admin.mapper.UtilityMapper;
import com.booking.homestay.exception.SpringException;
import com.booking.homestay.model.*;
import com.booking.homestay.repository.IDetailUtilityRepository;
import com.booking.homestay.repository.ITypeUtilityRepository;
import com.booking.homestay.repository.IUtilityRepositoty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class UtilityService {

    private final IUtilityRepositoty iUtilityRepositoty;
    private final IDetailUtilityRepository iDetailUtilityRepository;
    private final UtilityMapper utilityMapper;

    public void save(UtilityRequest utilityRequest) {
            iUtilityRepositoty.save(utilityMapper.map(utilityRequest));

    }

    @Transactional(readOnly = true)
    public List<UtilityResponse> getUtilityByType(Long id) {
        return iUtilityRepositoty.findByTypeUtility_Id(id)
                .stream()
                .map(utilityMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UtilityResponse> getAllUtility() {
        return iUtilityRepositoty.findAll()
                .stream()
                .map(utilityMapper::mapToDto)
                .collect(toList());
    }

    public void deleteUtility(Long id) {
        iUtilityRepositoty.deleteById(id);
    }


    public void editUtility(UtilityRequest utilityRequest) {
        Optional<Utility> utilityName = iUtilityRepositoty.findByUtilityName(utilityRequest.getUtilityName());
        if (!utilityName.isPresent()) {
            iUtilityRepositoty.save(utilityMapper.mapEditToDtoById(utilityRequest, utility));
        } else if(utilityName.isPresent() && utilityName.get().getUtilityName().equals(utilityRequest.getUtilityName()) && utilityName.get().getId().equals(utilityRequest.getId())){
            iUtilityRepositoty.save(utilityMapper.mapEditToDtoById(utilityRequest, utility));
        } else {
            throw new SpringException("Ti???n ??ch ???? t???n t???i");
        }
    }

    @Transactional(readOnly = true)
    public UtilityResponse getUtilityById(Long id) {
        Utility utility = iUtilityRepositoty.findById(id).orElseThrow(() -> new SpringException("Kh??ng t???n t???i ti???n ??ch ID  - " + id));
        return utilityMapper.mapToDto(utility);
    }

}
