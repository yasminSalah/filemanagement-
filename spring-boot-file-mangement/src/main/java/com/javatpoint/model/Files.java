package com.javatpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "files")

public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private byte[] file_binary;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // Constructors, getters, and setters

    public Files() {
    }

    public Files(byte[] binary, Item item) {
        this.file_binary = binary;
        this.item = item;
    }

    public void setBinary(byte[] binary) {
        this.file_binary = binary;
    }

    public byte[] getBinary() {
        // TODO Auto-generated method stub
        return file_binary;
    }

}

