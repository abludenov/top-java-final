package com.abdddev.restaurantvoiting.web.user;

import com.abdddev.restaurantvoiting.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.abdddev.restaurantvoiting.util.validation.ValidationUtil.*;

@Tag(name = "Admin User Controller", description = "User management for administrators")
@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractUserController {
    static final String REST_URL = "/api/admin/users";

    @Operation(summary = "Get User by id")
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        return super.get(id);
    }

    @Operation(summary = "Delete User by id")
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Operation(summary = "Get all Users")
    @GetMapping
    public List<User> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @Operation(summary = "Create User")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        checkNew(user);
        User created = prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Update User by id")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    @Operation(summary = "Get User by email")
    @GetMapping("/by-email")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        return ResponseEntity.of(repository.findByEmailIgnoreCase(email));
    }

    @Operation(summary = "Enable / Disable User by id")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        User user = repository.getExisted(id);
        user.setEnabled(enabled);
    }
}