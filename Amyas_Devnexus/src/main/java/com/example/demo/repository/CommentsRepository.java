package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long> {

}
