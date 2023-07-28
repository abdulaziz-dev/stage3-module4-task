package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/comments")
public class CommentController implements BaseController<CommentRequestDTO, CommentResponseDTO, Long> {

    private final CommentService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(name = "sort_by", required = false, defaultValue = "content") String sortBy) {
        List<CommentResponseDTO> comments = service.readAll(page, limit, sortBy);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CommentResponseDTO> readById(@PathVariable Long id) {
        CommentResponseDTO commentDTO = service.readById(id);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentResponseDTO>> readByNewsId(@RequestParam(name = "news_id") Long newsId) {
        List<CommentResponseDTO> commentsDTO = service.getCommentsByNewsId(newsId);
        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }


    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponseDTO> create(@RequestBody CommentRequestDTO createRequest) {
        CommentResponseDTO commentDTO = service.create(createRequest);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(@PathVariable("id") Long id,
                                                     @RequestBody CommentRequestDTO updateRequest) {
        CommentResponseDTO commentDTO = service.update(updateRequest);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id:\\d+}", consumes = "application/json-patch+json")
    public ResponseEntity<CommentResponseDTO> updatePart(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        try {
            CommentResponseDTO comment = service.readById(id);
            CommentRequestDTO request = new CommentRequestDTO(comment.id(), comment.content(), comment.newsId());
            CommentRequestDTO patchedComment = applyPatch(patch, request);

            return new ResponseEntity<>(service.update(patchedComment),HttpStatus.OK);
        }
        catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    private CommentRequestDTO applyPatch(JsonPatch patch, CommentRequestDTO dto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return objectMapper.treeToValue(patched, CommentRequestDTO.class);
    }
}
