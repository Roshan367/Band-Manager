package com.rv.band_manager.Service;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;
import com.rv.band_manager.Model.*;
import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Repository.BandRepository;
import com.rv.band_manager.Repository.UserRepository;
import com.rv.band_manager.Repository.ParentChildRelationshipRepository;
import java.util.*;


/**
 * Service implementation for managing user-related operations, including user registration, role management,
 * band membership management, and parent-child relationships.
 */
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BandRepository bandRepository;
    private final ParentChildRelationshipRepository parentChildRepository;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param userRepository The repository used to interact with the User data.
     * @param passwordEncoder The encoder used to encrypt user passwords.
     * @param bandRepository The repository used to interact with the Band data.
     * @param parentChildRepository The repository used to interact with the ParentChildRelationship data.
     */
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, BandRepository bandRepository,
                           ParentChildRelationshipRepository parentChildRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bandRepository = bandRepository;
        this.parentChildRepository = parentChildRepository;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Registers a new user by encoding their password and assigning them the member role.
     *
     * @param user The user to register.
     * @return The saved user.
     * @throws Exception If the email already exists.
     */
    public User register(User user) throws Exception {
        if(userRepository.findByEmailAndNoParent(user.getEmail()).isPresent()){
            throw new Exception("Email already exists: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.MEMBER);
        return userRepository.save(user);
    }

    /**
     * Retrieves all users from the user repository.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves all users who have parents.
     *
     * @return A list of users with parents.
     */
    public List<User> getAllParents() {
        return userRepository.findUsersWithParents();
    }

    /**
     * Retrieves all users associated with a specific band by the band's name.
     *
     * @param bandName The name of the band.
     * @return A list of users associated with the specified band.
     */
    public List<User> getUsersByBand(String bandName) {
        return userRepository.findByBandName(bandName);
    }

    /**
     * Adds a band to a user by their email address.
     *
     * @param userEmail The email address of the user to whom the band should be added.
     * @param bandId The ID of the band to add.
     * @return The updated user.
     * @throws RuntimeException If the user or band is not found.
     */
    public User addBandToUser(String userEmail, Long bandId) {
        Optional<User> userOpt = userRepository.findByEmailAndNoParent(userEmail);
        Optional<Band> bandOpt = bandRepository.findById(bandId);

        if(userOpt.isPresent() && bandOpt.isPresent()){
            User user = userOpt.get();
            Band band = bandOpt.get();

            user.getBands().add(band);
            return userRepository.save(user);
        }
        else{
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Adds a band to a user by the user's full name.
     *
     * @param fullName The full name of the user.
     * @param BandId The ID of the band to add.
     * @return The updated user.
     * @throws RuntimeException If the user or band is not found.
     */
    public User addBandToUserByFullName(String fullName, Long BandId) {
        Optional<User> userOptional = userRepository.findByFullName(fullName);
        Optional<Band> bandOptional = bandRepository.findById(BandId);

        if(userOptional.isPresent() && bandOptional.isPresent()){
            User user = userOptional.get();
            Band band = bandOptional.get();
            user.getBands().add(band);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Removes a user from a specific band.
     *
     * @param userId The ID of the user to remove.
     * @param bandId The ID of the band from which to remove the user.
     * @throws RuntimeException If an error occurs during removal.
     */
    public void deleteBandMember(Long userId, Long bandId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Band band = bandRepository.findById(bandId)
                    .orElseThrow(() -> new IllegalArgumentException("Band not found"));
            if (user.getBands() != null && user.getBands().contains(band)) {
                Set<Band> userBands = user.getBands();
                userBands.remove(band);
                user.setBands(userBands);
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error removing user from band: " + e.getMessage());
        }
    }

    /**
     * Retrieves all users with the committee member role.
     *
     * @return A list of committee members.
     */
    public List<User> getCommitteeMembers() {
        return userRepository.findByRole(Role.COMMITTEE_MEMBER);
    }

    /**
     * Promotes a user to the committee member role by their email.
     *
     * @param email The email of the user to promote.
     * @return The updated user.
     * @throws RuntimeException If the user is not found.
     */
    public User promoteUserToCommitteeMember(String email) {
        Optional<User> userOptional = userRepository.findByEmailAndNoParent(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.getRoles().add(Role.COMMITTEE_MEMBER);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Demotes a user from the committee member role by their ID.
     *
     * @param id The ID of the user to demote.
     * @throws RuntimeException If the user is not found.
     */
    public void demoteUserFromCommitteeMember(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.getRoles().remove(Role.COMMITTEE_MEMBER);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmailAndNoParent(email);
    }

    /**
     * Adds a parent to a child and establishes the parent-child relationship.
     *
     * @param child The child user.
     * @param parent The parent user.
     * @throws IllegalArgumentException If either the parent or child is null.
     * @throws IllegalStateException If the child already has a parent assigned.
     */
    public void addParentToChild(User child, User parent) {
        // Validate input
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Parent or child cannot be null.");
        }

        parent.getRoles().add(Role.PARENT);
        // Ensure that the parent is persisted (saved)
        parent = userRepository.save(parent); // Save the parent if it's not already saved
        child.getRoles().add(Role.CHILD);
        // Ensure that the child is persisted (saved)
        child = userRepository.save(child); // Save the child if it's not already saved


        // Check if the child already has a parent assigned
        if (child.getParentRelationship() != null) {
            throw new IllegalStateException("This child already has a parent assigned.");
        }

        // Create the ParentChildRelationship entity and set the parent-child relationship
        ParentChildRelationship relationship = new ParentChildRelationship();
        relationship.setParent(parent);
        relationship.setChild(child);

        // Add the relationship to both parent and child
        parent.getChildrenRelationships().add(relationship);
        child.setParentRelationship(relationship);

        // Save the relationship
        parentChildRepository.save(relationship);
    }
    /**
     * Retrieves all children of a given parent.
     *
     * @param parent The parent user whose children are being retrieved.
     * @return A list of children associated with the given parent.
     */
    public List<User> getParentsChildren(User parent){
        return userRepository.findChildrenByParentId(parent.getId());
    }

    /**
     * Retrieves all users with parent relationships.
     *
     * @return A list of all children and parents in the system.
     */
    public List<User> getAllChildren(){
        return userRepository.findUsersWithParents();
    }

    /**
     * Updates a user's account details.
     *
     * @param id The ID of the user to update.
     * @param updatedUser The updated user information.
     * @return The updated user.
     * @throws RuntimeException If the user is not found.
     */
    public User updateAccount(Long id, User updatedUser){
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }else{
            throw new RuntimeException("User not found");
        }

    }


}
