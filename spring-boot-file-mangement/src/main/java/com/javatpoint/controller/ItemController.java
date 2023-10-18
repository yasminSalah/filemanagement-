package com.javatpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/create")
    public String create() {
        // itemService.createSpaceAndAssignPermissions();
        return "success";
    }

    @GetMapping("/createSpaceAndAssignPermissions")
    public ResponseEntity<String> createSpaceAndAssignPermissions() {
        itemService.createSpaceAndAssignPermissions();
        return ResponseEntity.ok("success");
    }

    @GetMapping("/createFolderUnderSpace")
    public ResponseEntity<String> createFolderUnderSpace() {
        itemService.createFolderUnderSpace();
        return ResponseEntity.ok("success");
    }

    @GetMapping("/createFileUnderFolder")
    public ResponseEntity<String> createFileUnderFolder() {
        itemService.createFileUnderFolder();
        return ResponseEntity.ok("success");
    }

    // Add other methods as needed (update, delete, etc.)
}
