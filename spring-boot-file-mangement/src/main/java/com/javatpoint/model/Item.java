package com.javatpoint.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Item parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Item> children = new HashSet<>();

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    private Files files;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Permissions> permissions = new HashSet<>();

    public boolean hasAccess(String userEmail) {
        for (Permissions permission : permissions) {
            if (permission.getUserEmail().equals(userEmail)) {
                return true;
            }
        }
        return false;
    }

    // Constructors, getters, and setters

    public Item() {
    }

    public Item(String type, String name, Item parent) {
        this.type = type;
        this.name = name;
        // this.parent = parent;
    }

    public void addChild(Item child) {
        // children.add(child);
        child.setParent(this);
    }

    private void setParent(Item item) {
        // TODO Auto-generated method stub

    }

    public void addPermission(Permissions permission) {
        permissions.add(permission);
        permission.setItem(this);
    }

    public Files getFileEntity() {
        return files;
    }

    public void setFileEntity(Files fileEntity) {
        this.files = fileEntity;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}

