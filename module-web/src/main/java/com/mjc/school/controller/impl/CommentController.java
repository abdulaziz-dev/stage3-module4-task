package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentRequestDTO;
import com.mjc.school.service.dto.CommentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/comments")
public class CommentController implements BaseController<CommentRequestDTO, CommentResponseDTO, Long> {

    private final CommentService service;

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

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
