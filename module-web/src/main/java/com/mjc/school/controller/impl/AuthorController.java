package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController implements BaseController<AuthorRequestDTO, AuthorResponseDTO, Long> {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(value = "sort_by", required = false, defaultValue = "name") String sortBy)
    {
        List<AuthorResponseDTO> authors = service.readAll(page, limit, sortBy);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<AuthorResponseDTO> readById(@PathVariable Long id) {
        AuthorResponseDTO authorDTO = service.readById(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<AuthorResponseDTO> readByNewsId(
                                                @RequestParam(name = "news_id") Long newsId)
    {
        AuthorResponseDTO authorDTO = service.getAuthorByNewsId(newsId);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@RequestBody AuthorRequestDTO createRequest) {
        AuthorResponseDTO authorDTO = service.create(createRequest);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<AuthorResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody AuthorRequestDTO updateRequest) {
        AuthorResponseDTO authorDTO = service.update(updateRequest);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
