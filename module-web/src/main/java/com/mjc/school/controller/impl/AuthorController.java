package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

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

    @PatchMapping(path = "/{id:\\d+}", consumes = "application/json-patch+json")
    public ResponseEntity<AuthorResponseDTO> updatePart(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        try {
            AuthorResponseDTO author = service.readById(id);
            AuthorRequestDTO request = new AuthorRequestDTO(author.id(), author.name());
            AuthorRequestDTO patchedAuthor = applyPatch(patch, request);

            return new ResponseEntity<>(service.update(patchedAuthor),HttpStatus.OK);
        }
        catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    private AuthorRequestDTO applyPatch(JsonPatch patch, AuthorRequestDTO dto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return objectMapper.treeToValue(patched, AuthorRequestDTO.class);
    }
}
