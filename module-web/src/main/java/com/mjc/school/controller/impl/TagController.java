package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;
import com.mjc.school.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController implements BaseController<TagRequestDTO, TagResponseDTO, Long> {

    private final BaseService<TagRequestDTO, TagResponseDTO, Long> service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<TagResponseDTO>> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(value = "sort_by", required = false, defaultValue = "name") String sortBy)
    {
        List<TagResponseDTO> tags = service.readAll(page, limit, sortBy);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<TagResponseDTO> readById(@PathVariable("id") Long id) {
        TagResponseDTO response = service.readById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<TagResponseDTO> create(@RequestBody TagRequestDTO createRequest) {
        TagResponseDTO response = service.create(createRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<TagResponseDTO> update(@PathVariable("id") Long id, @RequestBody TagRequestDTO updateRequest) {
        TagResponseDTO response = service.update(updateRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
