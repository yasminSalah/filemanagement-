package com.javatpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.model.Files;
import com.javatpoint.model.Item;
import com.javatpoint.repository.ItemRepository;

@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, @RequestParam String userEmail) {
        Item file = itemRepository.findById(fileId).orElse(null);

        if (file != null && file.getType().equals("File")) {
            boolean hasAccess = file.hasAccess(userEmail); // Implement the
                                                           // logic to check
                                                           // user's access
            if (hasAccess) {
                Files fileEntity = file.getFileEntity();
                Resource resource = new ByteArrayResource(fileEntity.getBinary());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                        .contentLength(fileEntity.getBinary().length).contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}

