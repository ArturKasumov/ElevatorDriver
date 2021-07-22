package com.sytoss.edu2021.services;

import com.sytoss.edu2021.repo.EngineRepository;
import com.sytoss.edu2021.repo.dto.BuildingBOM;
import com.sytoss.edu2021.repo.dto.EngineBOM;
import com.sytoss.edu2021.repo.dto.EngineDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EngineServiceTest {

    @Mock
    private EngineRepository engineRepository;

    @InjectMocks
    private EngineService engineService;

   /* @Test
    public void creationTest() {
        EngineDTO engineDTO = getDefaultEngineDTO();
        when(engineRepository.save(any(EngineDTO.class))).thenReturn(engineDTO);
        EngineBOM response = engineService.create(1);
        assertEquals(1, (int)response.getId());
    }*/

    @Test
    public void getEngineTest() {
        EngineDTO engineDTO = getDefaultEngineDTO();
        when(engineRepository.findEngineDTById(1)).thenReturn(engineDTO);
        EngineBOM response = engineService.getEngine(1);
        assertEquals(1, (int)response.getId());

    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldRaiseExceptionByNotFound() {
        EngineBOM engineBOM = engineService.getEngine(1);
    }

    public EngineDTO getDefaultEngineDTO() {
        EngineDTO dto = new EngineDTO();
        dto.setId(1);
        return dto;
    }

}
