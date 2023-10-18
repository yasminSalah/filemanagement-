package com.javatpoint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatpoint.model.Item;
import com.javatpoint.model.PermissionGroup;
import com.javatpoint.model.Permissions;
import com.javatpoint.repository.ItemRepository;
import com.javatpoint.repository.PermissionGroupRepository;
import com.javatpoint.repository.PermissionsRepository;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private PermissionsRepository permissionRepository;

    @Override
    public void createSpaceAndAssignPermissions() {
        // Create Space
        Item space = new Item("Space", "stc-assessments", null);
        itemRepository.save(space);

        // Create Admin Group
        PermissionGroup adminGroup = new PermissionGroup("admin");
        permissionGroupRepository.save(adminGroup);

        // Assign VIEW and EDIT permissions to users in admin group
        Permissions viewPermission = new Permissions("user1@example.com", "VIEW", adminGroup);
        Permissions editPermission = new Permissions("user2@example.com", "EDIT", adminGroup);

        permissionRepository.save(viewPermission);
        permissionRepository.save(editPermission);
    }

    @Override
    public void createFolderUnderSpace() {
        Item space = itemRepository.findByName("stc-assessments");

        // Find the admin group
        PermissionGroup adminGroup = permissionGroupRepository.findByGroupName("admin");

        if (adminGroup != null) {
            // Get the adminGroupId
            Long adminGroupId = adminGroup.getId();

            // Check if the adminGroup has EDIT permission
            Permissions editPermission = permissionRepository.findByUserEmailAndGroupId("user2@example.com",
                    adminGroupId);

            if (editPermission != null && editPermission.getPermissionLevel().equals("EDIT")) {
                // Create Folder under Space
                Item folder = new Item("Folder", "backend", space);
                itemRepository.save(folder);

                // Assign Edit Permission to user
                folder.addPermission(editPermission);
                itemRepository.save(folder);
            } else {
                // Handle case where user does not have EDIT permission in
                // admin group
                throw new RuntimeException("User does not have EDIT access.");
            }
        } else {
            // Handle case where admin group was not found
            // Check if the user has EDIT access on this folder and block
            // VIEW access
            Permissions viewPermission = permissionRepository.findByUserEmailAndItemId("user1@example.com",
                    space.getId());

            if (viewPermission != null && viewPermission.getPermissionLevel().equals("VIEW")) {
                // Block user with VIEW access
                throw new RuntimeException("User with VIEW access is blocked from creating folders.");
            }

            // Otherwise, proceed (assuming user has EDIT access on this
            // folder)
            Item folder = new Item("Folder", "backend", space);
            itemRepository.save(folder);

            // Assign Edit Permission to user
            folder.addPermission(viewPermission);
            itemRepository.save(folder);
        }
    }

    @Override
    public void createFileUnderFolder() {
        Item folder = itemRepository.findByNameAndType("backend", "Folder");

        if (folder != null) {
            // Check if the user has EDIT permission on the folder
            Permissions editPermission = permissionRepository.findByUserEmailAndItemId("user2@example.com",
                    folder.getId());

            if (editPermission != null && editPermission.getPermissionLevel().equals("EDIT")) {
                // Check if there are any users with VIEW access on the
                // folder
                List<Permissions> viewPermissions = permissionRepository.findByPermissionLevelAndItemId("VIEW",
                        folder.getId());

                if (!viewPermissions.isEmpty()) {
                    // Block users with VIEW access
                    throw new RuntimeException("Users with VIEW access are blocked from creating files.");
                }

                // Create File under Folder
                Item file = new Item("File", "assessment.pdf", folder);
                itemRepository.save(file);

                // Assign Edit Permission to user
                file.addPermission(editPermission);
                itemRepository.save(file);
            } else {
                // Handle case where user does not have EDIT permission on
                // the folder
                throw new RuntimeException("User does not have EDIT access on this folder.");
            }
        } else {
            // Handle case where folder was not found
            throw new RuntimeException("Folder 'backend' not found.");
        }
    }

}

