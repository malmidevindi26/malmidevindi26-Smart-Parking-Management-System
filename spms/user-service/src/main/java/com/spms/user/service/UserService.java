package com.spms.user.service;

import com.spms.user.dto.LoginRequest;
import com.spms.user.dto.RegisterRequest;
import com.spms.user.exception.DuplicateResourceException;
import com.spms.user.exception.ResourceNotFoundException;
import com.spms.user.model.BookingLog;
import com.spms.user.model.User;
import com.spms.user.repository.BookingLogRepository;
import com.spms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookingLogRepository bookingLogRepository;

    @Autowired
    public UserService(UserRepository userRepository, BookingLogRepository bookingLogRepository) {
        this.userRepository = userRepository;
        this.bookingLogRepository = bookingLogRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("A user with email '" + request.getEmail() + "' already exists");
        }
        User user = new User(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone(),
                request.getRole()
        );
        User saved = userRepository.save(user);
        bookingLogRepository.save(new BookingLog(saved.getId(), "Account registered"));
        return saved;
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        bookingLogRepository.save(new BookingLog(user.getId(), "User logged in"));
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

//    public User updateUser(Long id, RegisterRequest request) {
//        User user = getUserById(id);
//        user.setFullName(request.getFullName());
//        user.setPhone(request.getPhone());
//        if (request.getPassword() != null && !request.getPassword().isBlank()) {
//            user.setPassword(request.getPassword());
//        }
//        User updated = userRepository.save(user);
//        bookingLogRepository.save(new BookingLog(id, "Profile updated"));
//        return updated;
//    }

    public User updateUser(Long id, RegisterRequest request) {
        User user = getUserById(id);
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        User updated = userRepository.save(user);
        bookingLogRepository.save(new BookingLog(id, "Profile updated"));
        return updated;
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public BookingLog addLog(Long userId, String description) {
        getUserById(userId);
        return bookingLogRepository.save(new BookingLog(userId, description));
    }

    public List<BookingLog> getHistory(Long userId) {
        getUserById(userId);
        return bookingLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }
}
