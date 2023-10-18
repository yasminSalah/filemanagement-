package com.javatpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.javatpoint.model.Files;
import com.javatpoint.model.Item;
import com.javatpoint.repository.ItemRepository;

@Service
public class DownloadFileService {

    @Autowired
    private ItemRepository itemRepository;

    public Resource downloadFile(Long fileId, String userEmail) {

        Resource resource = new ByteArrayResource(null);

        Item file = itemRepository.findById(fileId).orElse(null);

        if (file != null && file.getType().equals("File")) {
            boolean hasAccess = file.hasAccess(userEmail); // Implement the
                                                           // logic to check
                                                           // user's access
            if (hasAccess) {
                Files fileEntity = file.getFileEntity();
                resource = new ByteArrayResource(fileEntity.getBinary());
            }
        }
        return resource;
    }

}

