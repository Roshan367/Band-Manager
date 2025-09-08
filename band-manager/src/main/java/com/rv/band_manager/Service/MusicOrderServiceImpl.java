package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.*;
import com.rv.band_manager.Repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the MusicOrderService interface.
 * Provides logic for managing music orders, including CRUD operations,
 * status management, and association with users and music parts.
 */
@Service
public class MusicOrderServiceImpl implements MusicOrderService{
    LocalDate localDate = LocalDate.now();
    private final MusicOrderRepository musicOrderRepository;
    private final MusicPartRepository musicPartRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of MusicOrderServiceImpl with the specified repositories.
     *
     * @param musicOrderRepository the repository used for music order data access
     * @param musicPartRepository the repository used for music part data access
     * @param userRepository the repository used for user data access
     */
    public MusicOrderServiceImpl(MusicOrderRepository musicOrderRepository,
                                 MusicPartRepository musicPartRepository, UserRepository userRepository) {
        this.musicOrderRepository = musicOrderRepository;
        this.musicPartRepository = musicPartRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all MusicOrder records from the database.
     *
     * @return a list of all music orders
     */
    public List<MusicOrder> getAllMusicOrders() {
        return musicOrderRepository.findAll();
    }

    /**
     * Retrieves a specific MusicOrder by its ID.
     *
     * @param id the unique ID of the music order
     * @return an Optional containing the music order if found, or empty if not
     */
    public Optional<MusicOrder> getMusicOrderById(Long id) {
        return musicOrderRepository.findById(id);
    }

    /**
     * Creates a new MusicOrder for a specific user by their ID.
     *
     * @param userId the unique ID of the user
     * @return the created music order
     * @throws RuntimeException if the user is not found
     */
    public MusicOrder createMusicOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        MusicOrder musicOrder = new MusicOrder();
        musicOrder.setOwner(user);
        musicOrder.setStatus("NOT_READY");
        musicOrder.setDate(localDate);
        return musicOrderRepository.save(musicOrder);
    }

    /**
     * Creates a new MusicOrder for a child associated with a specific user.
     *
     * @param userId  the unique ID of the parent user
     * @param childId the unique ID of the child user
     * @return the created music order
     * @throws RuntimeException if the user or child is not found
     */
    public MusicOrder createChildMusicOrder(Long userId, Long childId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + childId));
        MusicOrder musicOrder = new MusicOrder();
        musicOrder.setOwner(user);
        musicOrder.setChild(child);
        musicOrder.setStatus("NOT_READY");
        musicOrder.setDate(localDate);
        return musicOrderRepository.save(musicOrder);
    }

    /**
     * Deletes a MusicOrder.
     *
     * @param id the unique ID of the music order to delete
     * @throws RuntimeException if the music order is not found
     */
    public void deleteMusicOrder(Long id) {
        Optional<MusicOrder> musicOrderOptional = musicOrderRepository.findById(id);
        if (musicOrderOptional.isPresent()) {
            musicOrderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Music Order not found");
        }
    }

    /**
     * Retrieves all MusicOrders for a specific user.
     *
     * @param userId the unique ID of the user
     * @return a list of music orders associated with the user
     */
    public List<MusicOrder> getOrdersByUserId(Long userId) {
        return musicOrderRepository.findByOwnerId(userId);
    }

    /**
     * Retrieves MusicOrders for a user by their ID, and with the status not ready.
     *
     * @param userId the unique ID of the user
     * @return a list of music orders with the status "NOT_READY"
     */
    public List<MusicOrder> getUserMusicOrderNotReady(Long userId) {
        return musicOrderRepository.findByStatusAndOwnerId("NOT_READY", userId);
    }

    /**
     * Retrieves MusicOrders for a user by their ID, and with the status ready.
     *
     * @param userId the unique ID of the user
     * @return a list of music orders with the status "READY"
     */
    public List<MusicOrder> getUserMusicOrderReady(Long userId) {
        return musicOrderRepository.findByStatusAndOwnerId("READY", userId);
    }

    /**
     * Retrieves MusicOrders for a user by their ID, and with the status fulfilled.
     *
     * @param userId the unique ID of the user
     * @return a list of music orders with the status "FULFILLED"
     */
    public List<MusicOrder> getUserMusicOrderFulfilled(Long userId) {
        return musicOrderRepository.findByStatusAndOwnerId("FULFILLED", userId);
    }

    /**
     * Retrieves MusicOrders for a child by their ID, and with the status not ready.
     *
     * @param childId the unique ID of the child
     * @return a list of music orders with the status "NOT_READY"
     */
    public List<MusicOrder> getChildMusicOrderNotReady(Long childId) {
        return musicOrderRepository.findByStatusAndChildId("NOT_READY", childId);
    }

    /**
     * Retrieves MusicOrders for a child by their ID, and with the status ready.
     *
     * @param childId the unique ID of the child
     * @return a list of music orders with the status "READY"
     */
    public List<MusicOrder> getChildMusicOrderReady(Long childId) {
        return musicOrderRepository.findByStatusAndChildId("READY", childId);
    }

    /**
     * Retrieves MusicOrders for a child by their ID, and with the status fulfilled.
     *
     * @param childId the unique ID of the child
     * @return a list of music orders with the status "FULFILLED"
     */
    public List<MusicOrder> getChildMusicOrderFulfilled(Long childId) {
        return musicOrderRepository.findByStatusAndChildId("FULFILLED", childId);
    }

    /**
     * Adds a MusicPart to a specific MusicOrder by its ID and saves the new order.
     *
     * @param MusicOrderId the unique ID of the music order
     * @param musicPart    the music part to add to the order
     * @return the updated music order with the added music part
     * @throws RuntimeException if the music order is not found
     */
    public MusicOrder addMusicPartToMusicOrder(Long MusicOrderId, MusicPart musicPart) {
        Optional<MusicOrder> musicOrderOpt = musicOrderRepository.findById(MusicOrderId);

        if (musicOrderOpt.isPresent()) {
            MusicOrder musicOrder = musicOrderOpt.get();
            musicOrder.getMusicParts().add(musicPart);
            return musicOrderRepository.save(musicOrder);
        }
        else{
            throw new RuntimeException("Music part not found");
        }
    }

    /**
     * Retrieves all MusicParts associated with a specific MusicOrder.
     *
     * @param musicOrderId the unique ID of the music order
     * @return a list of music parts associated with the music order
     * @throws IllegalArgumentException if the music order is not found
     */
    public List<MusicPart> getPartsByMusicOrderId(Long musicOrderId) {
        return musicOrderRepository.findById(musicOrderId)
                .map(musicOrder -> new ArrayList<>(musicOrder.getMusicParts())) // Convert Set to List
                .orElseThrow(() -> new IllegalArgumentException("MusicOrder not found for ID: " + musicOrderId));
    }

    /**
     * Retrieves all MusicOrders with the status ready.
     *
     * @return a list of music orders with the status "READY"
     */
    public List<MusicOrder> getAllMusicOrderReady() {
        return musicOrderRepository.findByStatus("READY");
    }

    /**
     * Retrieves all MusicOrders with the status fulfilled.
     *
     * @return a list of music orders with the status "FULFILLED"
     */
    public List<MusicOrder> getAllMusicOrderFulfilled() {
        return musicOrderRepository.findByStatus("FULFILLED");
    }

    /**
    * Updates the status of a MusicOrder to ready.
    *
    * @param musicOrder the music order to update
    * @return the updated music order with the status "READY"
    */
    public MusicOrder readyMusicOrder(MusicOrder musicOrder) {
        musicOrder.setStatus("READY");
        return musicOrderRepository.save(musicOrder);
    }

    /**
     * Updates the status of a MusicOrder to fulfilled.
     *
     * @param musicOrder the music order to update
     * @return the updated music order with the status "FULFILLED"
     */
    public MusicOrder fulfillMusicOrder(MusicOrder musicOrder) {
        musicOrder.setStatus("FULFILLED");
        return musicOrderRepository.save(musicOrder);
    }


}
