package com.javatpoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javatpoint.model.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

    // For example, if you want to find permissions by user email
    List<Permissions> findByUserEmail(String userEmail);

    // For example, if you want to find permissions by user email and group id
    Permissions findByUserEmailAndGroupId(String userEmail, Long groupId);

    // For example, if you want to find permissions by user email and item id
    Permissions findByUserEmailAndItemId(String userEmail, Long itemId);

    List<Permissions> findByPermissionLevelAndItemId(String string, Long id);
}
