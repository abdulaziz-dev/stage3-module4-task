package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController implements BaseController<TagRequestDTO, TagResponseDTO, Long> {

    private final TagService service;

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

    @GetMapping("/search")
    public ResponseEntity<List<TagResponseDTO>> readByNewsId(@RequestParam(name = "news_id") Long newsId) {
        List<TagResponseDTO> tags = service.getTagsByNewsId(newsId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
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
