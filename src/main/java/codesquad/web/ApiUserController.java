package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody User user) {
        User savedUser = userService.add(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + savedUser.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id:\\d+}")
    public User show(@LoginUser User loginUser, @PathVariable long id) {
        return userService.findById(loginUser, id);
    }

    @PutMapping("/{id:\\d+}")
    public User update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody User updatedUser) {
        return userService.update(loginUser, id, updatedUser);
    }

}
