package com.backend.assignment.services;

import com.backend.assignment.entities.User;
import com.backend.assignment.entities.UserResponse;
import com.backend.assignment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User addUser(User user){
         return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //method for add paging and sorting api
    public UserResponse getAllUsersByPagingSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) throws Exception{
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        else if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            throw new Exception("enter wrong Char!!");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> pagePost = this.userRepository.findAll(pageable);
        List<User> allUsers = pagePost.getContent();

        UserResponse userResponse = new UserResponse();
        userResponse.setName(allUsers);
        userResponse.setPageNumber(pagePost.getNumber());
        userResponse.setPageSize(pagePost.getSize());
        userResponse.setTotalElements(pagePost.getTotalElements());
        userResponse.setTotalPages(pagePost.getTotalPages());
        userResponse.setFirstPage(pagePost.isFirst());
        userResponse.setLastPage(pagePost.isLast());
        return userResponse;
    }
}
