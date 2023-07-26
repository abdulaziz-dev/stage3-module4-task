package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import com.mjc.school.service.impl.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController implements BaseController<AuthorRequestDTO, AuthorResponseDTO, Long> {
    private final BaseService<AuthorRequestDTO, AuthorResponseDTO, Long> service;

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
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> readById(@PathVariable Long id) {
        AuthorResponseDTO authorDTO = service.readById(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@RequestBody AuthorRequestDTO createRequest) {
        AuthorResponseDTO authorDTO = service.create(createRequest);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody AuthorRequestDTO updateRequest) {
        AuthorResponseDTO authorDTO = service.update(updateRequest);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
