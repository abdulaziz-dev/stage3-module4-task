package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController implements BaseController<NewsRequestDTO, NewsResponseDTO, Long> {
    private final NewsService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public NewsController(NewsService newsService) {
        this.service = newsService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(value = "sort_by", required = false, defaultValue = "title") String sortBy)
    {
        List<NewsResponseDTO> news = service.readAll(page, limit, sortBy);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<NewsResponseDTO> readById(@PathVariable("id") Long id) {
        NewsResponseDTO newsDTO = service.readById(id);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NewsResponseDTO> create(@RequestBody NewsRequestDTO createRequest) {
        NewsResponseDTO newsDTO = service.create(createRequest);
        return new ResponseEntity<>(newsDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<NewsResponseDTO> update(@PathVariable Long id, @RequestBody NewsRequestDTO updateRequest) {
        NewsResponseDTO newsDTO = service.update(updateRequest);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsResponseDTO>> readByParams(
            @RequestParam(name = "tag_id", required = false) Long tagId,
            @RequestParam(name = "tag_name", required = false) String tagName,
            @RequestParam(name = "author_name", required = false) String authorName,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "content", required = false) String content){
        return new ResponseEntity<>(service.readByParams(tagId, tagName, authorName, title, content), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id:\\d+}", consumes = "application/json-patch+json")
    public ResponseEntity<NewsResponseDTO> updatePart(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        try {
            NewsResponseDTO news = service.readById(id);
            NewsRequestDTO request = new NewsRequestDTO(news.id(), news.title(), news.content(), news.authorId(),
                                                        news.tagsSet().stream().map(x-> x.id()).collect(Collectors.toSet()));
            NewsRequestDTO patchedNews = applyPatch(patch, request);

            return new ResponseEntity<>(service.update(patchedNews),HttpStatus.OK);
        }
        catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private NewsRequestDTO applyPatch(JsonPatch patch, NewsRequestDTO dto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return objectMapper.treeToValue(patched, NewsRequestDTO.class);
    }

}
