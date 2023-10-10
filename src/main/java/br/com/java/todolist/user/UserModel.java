package br.com.java.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity(name = "usuarios")
public class UserModel {
  
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  
  @Column(unique = true)
  public String username;
  public String name;
  public String password;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
