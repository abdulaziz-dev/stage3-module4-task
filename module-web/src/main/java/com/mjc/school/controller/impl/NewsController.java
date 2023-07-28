package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;
import com.mjc.school.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/news")
public class NewsController implements BaseController<NewsRequestDTO, NewsResponseDTO, Long> {
    private final NewsServiceImpl newsService;

    @Autowired
    public NewsController(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(value = "sort_by", required = false, defaultValue = "title") String sortBy)
    {
        List<NewsResponseDTO> news = newsService.readAll(page, limit, sortBy);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<NewsResponseDTO> readById(@PathVariable("id") Long id) {
        NewsResponseDTO newsDTO = newsService.readById(id);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<NewsResponseDTO> create(@RequestBody NewsRequestDTO createRequest) {
        NewsResponseDTO newsDTO = newsService.create(createRequest);
        return new ResponseEntity<>(newsDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<NewsResponseDTO> update(@PathVariable Long id, @RequestBody NewsRequestDTO updateRequest) {
        NewsResponseDTO newsDTO = newsService.update(updateRequest);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        newsService.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsResponseDTO>> readByParams(
            @RequestParam(name = "tag_id", required = false) Long tagId,
            @RequestParam(name = "tag_name", required = false) String tagName,
            @RequestParam(name = "author_name", required = false) String authorName,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "content", required = false) String content){
        return new ResponseEntity<>(newsService.readByParams(tagId, tagName, authorName, title, content), HttpStatus.OK);
    }

}
