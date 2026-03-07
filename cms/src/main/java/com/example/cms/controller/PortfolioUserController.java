//This is for the PortfolioUser's controller
//Only manage user fields

package com.example.cms.controller;

import com.example.cms.controller.dto.PortfolioUserDto;
import com.example.cms.controller.exceptions.ResourceNotFoundException;
import com.example.cms.model.entity.PortfolioUser;
import com.example.cms.model.repository.PortfolioUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class PortfolioUserController {

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @GetMapping
    public List<PortfolioUser> getAllUsers() {
        return portfolioUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public PortfolioUser getUserById(@PathVariable Long id) {
        return portfolioUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @PostMapping
    public PortfolioUser createUser(@Valid @RequestBody PortfolioUserDto userDto) {
        PortfolioUser user = new PortfolioUser();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        return portfolioUserRepository.save(user);
    }

    @PutMapping("/{id}")
    public PortfolioUser updateUser(@PathVariable Long id,
                                    @Valid @RequestBody PortfolioUserDto userDto) {
        PortfolioUser user = portfolioUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        return portfolioUserRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (!portfolioUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        portfolioUserRepository.deleteById(id);
    }
}